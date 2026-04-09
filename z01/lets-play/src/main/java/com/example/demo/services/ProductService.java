package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepo;

@Service
public class ProductService {
   @Autowired
    private ProductRepo repository;
    public List<Product> getAll(){
        return repository.findAll();
    }
    public Product getProduct(String id){
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
    public void deleteProduct(String id){
       Optional<Product> pd=repository.findById(id);// Product pd = repository.findById(id)
            //.orElseThrow(() -> new RuntimeException("Product not found"));
       pd.ifPresent(repository::delete); 
        //Alternalively
       //repository.deleteById(id);
    }
    public void createProduct(Product entity){
        repository.save(entity);

    }
    public Product update(Product updatedProduct,String id){

        Product product = repository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
        if (product!=null){
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            return repository.save(product);
        }
        return null;
    }

}
