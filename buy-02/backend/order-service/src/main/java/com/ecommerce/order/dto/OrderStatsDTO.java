package com.ecommerce.order.dto;

import java.util.List;
import java.util.Map;

public class OrderStatsDTO {
    private Double totalSpent;
    private Double totalEarned;
    private List<Map<String, Object>> bestProducts;
    private List<Map<String, Object>> mostBuyingProducts;
    private List<Map<String, Object>> bestSellingProducts;

    public Double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(Double totalSpent) { this.totalSpent = totalSpent; }
    public Double getTotalEarned() { return totalEarned; }
    public void setTotalEarned(Double totalEarned) { this.totalEarned = totalEarned; }
    public List<Map<String, Object>> getBestProducts() { return bestProducts; }
    public void setBestProducts(List<Map<String, Object>> bestProducts) { this.bestProducts = bestProducts; }
    public List<Map<String, Object>> getMostBuyingProducts() { return mostBuyingProducts; }
    public void setMostBuyingProducts(List<Map<String, Object>> mostBuyingProducts) { this.mostBuyingProducts = mostBuyingProducts; }
    public List<Map<String, Object>> getBestSellingProducts() { return bestSellingProducts; }
    public void setBestSellingProducts(List<Map<String, Object>> bestSellingProducts) { this.bestSellingProducts = bestSellingProducts; }
}
