import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { Cart, CartItem } from '../../models/cart.model';
import { Product } from '../../models/product.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart: Cart | null = null;
  cartProducts: Map<number, Product> = new Map();
  userId: number = 1;
  totalAmount: number = 0;

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadCart();
  }

  loadCart() {
    this.cartService.getCart(this.userId).subscribe({
      next: (cart) => {
        this.cart = cart;
        this.loadProductDetails();
      },
      error: (err) => console.error('Error loading cart', err)
    });
  }

  loadProductDetails() {
    if (!this.cart) return;
    
    this.cart.items.forEach(item => {
      this.productService.getProductById(item.productId).subscribe({
        next: (product) => {
          this.cartProducts.set(item.productId, product);
          this.calculateTotal();
        }
      });
    });
  }

  calculateTotal() {
    this.totalAmount = 0;
    this.cart?.items.forEach(item => {
      const product = this.cartProducts.get(item.productId);
      if (product) {
        this.totalAmount += product.price * item.quantity;
      }
    });
  }

  updateQuantity(productId: number, quantity: number) {
    if (quantity < 1) return;
    this.cartService.updateQuantity(this.userId, productId, quantity).subscribe({
      next: (cart) => {
        this.cart = cart;
        this.calculateTotal();
      },
      error: (err) => console.error('Error updating quantity', err)
    });
  }

  removeItem(productId: number) {
    this.cartService.removeFromCart(this.userId, productId).subscribe({
      next: (cart) => {
        this.cart = cart;
        this.cartProducts.delete(productId);
        this.calculateTotal();
      },
      error: (err) => console.error('Error removing item', err)
    });
  }

  proceedToCheckout() {
    this.router.navigate(['/checkout']);
  }
}
