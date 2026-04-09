import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, Routes } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { AppComponent } from './app/app.component';

const routes: Routes = [
  { path: '', redirectTo: '/products', pathMatch: 'full' as const },
  { path: 'products', loadComponent: () => import('./app/components/product-list/product-list.component').then(m => m.ProductListComponent) },
  { path: 'cart', loadComponent: () => import('./app/components/cart/cart.component').then(m => m.CartComponent) },
  { path: 'checkout', loadComponent: () => import('./app/components/checkout/checkout.component').then(m => m.CheckoutComponent) },
  { path: 'orders', loadComponent: () => import('./app/components/order-list/order-list.component').then(m => m.OrderListComponent) },
  { path: 'profile', loadComponent: () => import('./app/components/user-profile/user-profile.component').then(m => m.UserProfileComponent) }
];

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient()
  ]
}).catch(err => console.error(err));
