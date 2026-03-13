package com.ecommerce.user.dto;

import java.util.List;

public class UserProfileDTO {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Double totalSpent;
    private Double totalEarned;
    private List<Object> bestProducts;
    private List<Object> mostBuyingProducts;
    private List<Object> bestSellingProducts;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(Double totalSpent) { this.totalSpent = totalSpent; }
    public Double getTotalEarned() { return totalEarned; }
    public void setTotalEarned(Double totalEarned) { this.totalEarned = totalEarned; }
    public List<Object> getBestProducts() { return bestProducts; }
    public void setBestProducts(List<Object> bestProducts) { this.bestProducts = bestProducts; }
    public List<Object> getMostBuyingProducts() { return mostBuyingProducts; }
    public void setMostBuyingProducts(List<Object> mostBuyingProducts) { this.mostBuyingProducts = mostBuyingProducts; }
    public List<Object> getBestSellingProducts() { return bestSellingProducts; }
    public void setBestSellingProducts(List<Object> bestSellingProducts) { this.bestSellingProducts = bestSellingProducts; }
}
