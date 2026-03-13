import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Cart } from '../models/cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8084/api/cart';
  private cartSubject = new BehaviorSubject<Cart | null>(null);
  public cart$ = this.cartSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadCartFromStorage();
  }

  private loadCartFromStorage() {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      this.cartSubject.next(JSON.parse(savedCart));
    }
  }

  private saveCartToStorage(cart: Cart) {
    localStorage.setItem('cart', JSON.stringify(cart));
    this.cartSubject.next(cart);
  }

  getCart(userId: number): Observable<Cart> {
    return this.http.get<Cart>(`${this.apiUrl}/${userId}`);
  }

  addToCart(userId: number, productId: number, quantity: number): Observable<Cart> {
    return new Observable(observer => {
      this.http.post<Cart>(`${this.apiUrl}/${userId}/items`, { productId, quantity })
        .subscribe({
          next: (cart) => {
            this.saveCartToStorage(cart);
            observer.next(cart);
            observer.complete();
          },
          error: (err) => observer.error(err)
        });
    });
  }

  updateQuantity(userId: number, productId: number, quantity: number): Observable<Cart> {
    return new Observable(observer => {
      this.http.put<Cart>(`${this.apiUrl}/${userId}/items/${productId}?quantity=${quantity}`, {})
        .subscribe({
          next: (cart) => {
            this.saveCartToStorage(cart);
            observer.next(cart);
            observer.complete();
          },
          error: (err) => observer.error(err)
        });
    });
  }

  removeFromCart(userId: number, productId: number): Observable<Cart> {
    return new Observable(observer => {
      this.http.delete<Cart>(`${this.apiUrl}/${userId}/items/${productId}`)
        .subscribe({
          next: (cart) => {
            this.saveCartToStorage(cart);
            observer.next(cart);
            observer.complete();
          },
          error: (err) => observer.error(err)
        });
    });
  }

  clearCart(userId: number): Observable<void> {
    return new Observable(observer => {
      this.http.delete<void>(`${this.apiUrl}/${userId}`)
        .subscribe({
          next: () => {
            localStorage.removeItem('cart');
            this.cartSubject.next(null);
            observer.next();
            observer.complete();
          },
          error: (err) => observer.error(err)
        });
    });
  }
}
