package com.ecommerce.media.service;

import com.ecommerce.media.dto.MediaResponse;
import com.ecommerce.media.exception.InvalidFileException;
import com.ecommerce.media.exception.ResourceNotFoundException;
import com.ecommerce.media.exception.UnauthorizedException;
import com.ecommerce.media.kafka.MediaEventProducer;
import com.ecommerce.media.model.Media;
import com.ecommerce.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {

    private final MediaRepository mediaRepository;
    private final MediaEventProducer mediaEventProducer;

    @Value("${media.upload.directory:uploads}")
    private String uploadDirectory;

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    public MediaResponse uploadMedia(MultipartFile file, String productId, String userId) throws IOException {
        log.info("Uploading media for product {} by user {}", productId, userId);

        // Validate file
        validateFile(file);

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : "";
        String filename = UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(filename);

        // Save file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        log.info("File saved: {}", filename);

        // Generate URL - use /view endpoint for inline display
        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/media/")
                .path(filename)
                .path("/view")
                .toUriString();

        // Save metadata to database
        Media media = Media.builder()
                .filename(filename)
                .originalFilename(originalFilename)
                .contentType(file.getContentType())
                .size(file.getSize())
                .url(fileUrl)
                .productId(productId)
                .uploadedBy(userId)
                .uploadedAt(LocalDateTime.now())
                .build();

        media = mediaRepository.save(media);
        log.info("Media metadata saved with id: {}", media.getId());

        // Publish event to Kafka
        mediaEventProducer.publishMediaUploadedEvent(media);

        return mapToResponse(media);
    }

    public MediaResponse getMedia(String mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + mediaId));
        
        return mapToResponse(media);
    }

    public Resource downloadMedia(String filename) throws IOException {
        Path filePath = Paths.get(uploadDirectory).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new ResourceNotFoundException("File not found: " + filename);
        }
    }

    public List<MediaResponse> getProductMedia(String productId) {
        return mediaRepository.findByProductId(productId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<MediaResponse> getUserMedia(String userId) {
        return mediaRepository.findByUploadedBy(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteMedia(String mediaId, String userId) throws IOException {
        log.info("Deleting media {} by user {}", mediaId, userId);

        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Media not found with id: " + mediaId));

        if (!media.getUploadedBy().equals(userId)) {
            throw new UnauthorizedException("You are not authorized to delete this media");
        }

        // Delete file from filesystem
        Path filePath = Paths.get(uploadDirectory).resolve(media.getFilename()).normalize();
        Files.deleteIfExists(filePath);
        log.info("File deleted: {}", media.getFilename());

        // Delete metadata from database
        mediaRepository.delete(media);
        log.info("Media metadata deleted with id: {}", mediaId);

        // Publish event to Kafka
        mediaEventProducer.publishMediaDeletedEvent(mediaId, userId, media.getProductId());
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidFileException("File size exceeds 2MB limit");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new InvalidFileException("Only image files (JPEG, PNG, GIF, WebP) are allowed");
        }
    }

    private MediaResponse mapToResponse(Media media) {
        return MediaResponse.builder()
                .id(media.getId())
                .filename(media.getFilename())
                .originalFilename(media.getOriginalFilename())
                .contentType(media.getContentType())
                .size(media.getSize())
                .url(media.getUrl())
                .productId(media.getProductId())
                .uploadedBy(media.getUploadedBy())
                .uploadedAt(media.getUploadedAt())
                .build();
    }
}
