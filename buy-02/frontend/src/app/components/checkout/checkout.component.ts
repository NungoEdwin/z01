import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  userId: number = 1;
  shippingAddress: string = '';
  paymentMethod: string = 'PAY_ON_DELIVERY';
  cart: any;
  cartProducts: Map<number, any> = new Map();
  totalAmount: number = 0;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
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
      }
    });
  }

  loadProductDetails() {
    this.cart.items.forEach((item: any) => {
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
    this.cart.items.forEach((item: any) => {
      const product = this.cartProducts.get(item.productId);
      if (product) {
        this.totalAmount += product.price * item.quantity;
      }
    });
  }

  placeOrder() {
    if (!this.shippingAddress) {
      alert('Please enter shipping address');
      return;
    }

    const orderItems = this.cart.items.map((item: any) => {
      const product = this.cartProducts.get(item.productId);
      return {
        productId: item.productId,
        sellerId: product.sellerId,
        quantity: item.quantity,
        price: product.price
      };
    });

    const orderData = {
      userId: this.userId,
      shippingAddress: this.shippingAddress,
      paymentMethod: this.paymentMethod,
      items: orderItems
    };

    this.orderService.createOrder(orderData).subscribe({
      next: () => {
        this.cartService.clearCart(this.userId).subscribe();
        alert('Order placed successfully!');
        this.router.navigate(['/orders']);
      },
      error: (err) => {
        console.error('Error placing order', err);
        alert('Failed to place order');
      }
    });
  }
}
