package com.ecommerce.media.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponse {
    
    private String id;
    
    private String filename;
    
    private String originalFilename;
    
    private String contentType;
    
    private Long size;
    
    private String url;
    
    private String productId;
    
    private String uploadedBy;
    
    private LocalDateTime uploadedAt;
}
