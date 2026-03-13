import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = 'http://localhost:8083/api/orders';

  constructor(private http: HttpClient) {}

  createOrder(orderData: any): Observable<Order> {
    return this.http.post<Order>(this.apiUrl, orderData);
  }

  getUserOrders(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/user/${userId}`);
  }

  getSellerOrders(sellerId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/seller/${sellerId}`);
  }

  searchOrders(userId?: number, status?: string): Observable<Order[]> {
    let url = `${this.apiUrl}/search?`;
    if (userId) url += `userId=${userId}&`;
    if (status) url += `status=${status}`;
    return this.http.get<Order[]>(url);
  }

  updateOrderStatus(orderId: number, status: string): Observable<Order> {
    return this.http.put<Order>(`${this.apiUrl}/${orderId}/status?status=${status}`, {});
  }

  cancelOrder(orderId: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${orderId}/cancel`, {});
  }

  deleteOrder(orderId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${orderId}`);
  }

  getBuyerStats(userId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/buyer-stats/${userId}`);
  }

  getSellerStats(sellerId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/seller-stats/${sellerId}`);
  }
}
