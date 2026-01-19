package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepo;

@Service
public class ProductService {
   @Autowired
    private ProductRepo repository;
    public List<Product> getAll(){
        return repository.findAll();
    }
    public Optional<Product> getProduct(String id){
        return repository.findById(id);
    }
    public void deleteProduct(String id){
       Optional<Product> pd=repository.findById(id);
       pd.ifPresent(repository::delete); 
        //Alternalively
       //repository.deleteById(id);
    }
    public void createProduct(Product entity){
        repository.save(entity);

    }

}
