import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  searchKeyword: string = '';
  selectedCategory: string = '';
  minPrice: number | undefined;
  maxPrice: number | undefined;
  userId: number = 1;

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.filteredProducts = data;
      },
      error: (err) => console.error('Error loading products', err)
    });
  }

  searchProducts() {
    if (this.searchKeyword) {
      this.productService.searchProducts(this.searchKeyword).subscribe({
        next: (data) => this.filteredProducts = data,
        error: (err) => console.error('Error searching products', err)
      });
    } else {
      this.filteredProducts = this.products;
    }
  }

  filterProducts() {
    this.productService.filterProducts(this.selectedCategory, this.minPrice, this.maxPrice).subscribe({
      next: (data) => this.filteredProducts = data,
      error: (err) => console.error('Error filtering products', err)
    });
  }

  addToCart(productId: number) {
    this.cartService.addToCart(this.userId, productId, 1).subscribe({
      next: () => alert('Product added to cart'),
      error: (err) => console.error('Error adding to cart', err)
    });
  }
}
