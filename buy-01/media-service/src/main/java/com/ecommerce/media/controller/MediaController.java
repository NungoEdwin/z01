package com.ecommerce.media.controller;

import com.ecommerce.media.dto.MediaResponse;
import com.ecommerce.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Slf4j
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") String productId,
            @RequestAttribute("userId") String userId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "File must not be empty"));
        }
        try {
            log.info("Upload request received for product {} by user {}", productId, userId);
            MediaResponse response = mediaService.uploadMedia(file, productId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error uploading file: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaResponse> getMedia(@PathVariable String id) {
        MediaResponse response = mediaService.getMedia(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<MediaResponse>> getProductMedia(@PathVariable String productId) {
        List<MediaResponse> mediaList = mediaService.getProductMedia(productId);
        return ResponseEntity.ok(mediaList);
    }

    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(
            @PathVariable String id,
            @RequestAttribute("userId") String userId) {
        try {
            mediaService.deleteMedia(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting media {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error deleting media: " + e.getMessage());
        }
    }

    /**
     * Download endpoint - serves files with attachment disposition (forces download)
     */
    @GetMapping("/{filename}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Resource resource = mediaService.downloadMedia(filename);
            
            String contentType = "application/octet-stream";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            log.error("Error downloading file {}: {}", filename, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * View endpoint - serves images inline for display in browsers
     */
    @GetMapping("/{filename}/view")
    public ResponseEntity<Resource> viewFile(@PathVariable String filename) {
        try {
            Resource resource = mediaService.downloadMedia(filename);
            
            // Determine content type from filename extension
            String contentType = determineContentType(filename);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=31536000")
                    .body(resource);
        } catch (Exception e) {
            log.error("Error viewing file {}: {}", filename, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    private String determineContentType(String filename) {
        if (filename == null) {
            return "application/octet-stream";
        }
        
        String lowerFilename = filename.toLowerCase();
        if (lowerFilename.endsWith(".jpg") || lowerFilename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerFilename.endsWith(".png")) {
            return "image/png";
        } else if (lowerFilename.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerFilename.endsWith(".webp")) {
            return "image/webp";
        } else if (lowerFilename.endsWith(".svg")) {
            return "image/svg+xml";
        }
        
        return "application/octet-stream";
    }
}
