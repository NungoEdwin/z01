package com.ecommerce.media.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "media")
public class Media {
    
    @Id
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
