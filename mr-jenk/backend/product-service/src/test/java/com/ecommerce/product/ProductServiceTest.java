package com.ecommerce.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllProducts() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/products", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product("Test Product", "Description", 99.99, 10);
        ResponseEntity<Product> response = restTemplate.postForEntity("/api/products", product, Product.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Product", response.getBody().getName());
    }
}
