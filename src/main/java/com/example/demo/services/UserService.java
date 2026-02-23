package com.example.demo.services;

import java.util.List;

import com.example.demo.models.User;

public class UserService {
     final private UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public List<User> all() { return repo.findAll(); }
    public User findById(String id) { return repo.findById(id).orElse(null); }
    public User findByName(String name) { return repo.findByname(name); }
    public User save(User user) { return repo.save(user); }
    public void delete(User user) { repo.delete(user); }
    public void deleteById(String id) { repo.deleteById(id); }
    public void deleteByName(String name) { repo.delete(repo.findByname(name)); }
    public void deleteAll() { repo.deleteAll(); }
    public void update(User user) { repo.save(user); }
    public void updateAll(User user) { repo.deleteAll(); repo.save(user); }
    public boolean existsById(String id) { return repo.existsById(id); }
}

