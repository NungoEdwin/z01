package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Product;
import com.example.demo.services.ProductService;
@RestController
@RequestMapping("/auth")
public class ProductController {
	 @Autowired
    ProductService productService;
    @GetMapping("/products")
	public List<Product> GetProducts(){
		return productService.getAll();
	}
	 @PostMapping("/products")
    public void CreateProduct(@RequestBody Product entity){
     productService.createProduct(entity);
    }
    @PutMapping("/products/{id}")
    public Product UpdateProduct(@RequestBody Product entity,@PathVariable String id){
        return productService.update(entity, id);
    }
    @DeleteMapping("/products/{id}") 
    public void DeleteProduct(@PathVariable String id){
         productService.deleteProduct(id);
    }
    @GetMapping("/products/{id}")
    public Product GetProduct(@PathVariable String id){
        return productService.getProduct(id);
    }
	
}
