package com.ecommerce.media.controller;

import com.ecommerce.media.dto.MediaResponse;
import com.ecommerce.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaUserController {

    private final MediaService mediaService;

    @GetMapping("/my-uploads")
    public ResponseEntity<List<MediaResponse>> getMyUploads(@RequestAttribute("userId") String userId) {
        return ResponseEntity.ok(mediaService.getUserMedia(userId));
    }
}