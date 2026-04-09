import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <nav class="navbar">
      <div class="nav-brand">E-Commerce</div>
      <div class="nav-links">
        <a routerLink="/products">Products</a>
        <a routerLink="/cart">Cart</a>
        <a routerLink="/orders">Orders</a>
        <a routerLink="/profile">Profile</a>
      </div>
    </nav>
    <main>
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [`
    .navbar {
      background: #2c3e50;
      color: white;
      padding: 1rem 2rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .nav-brand {
      font-size: 1.5rem;
      font-weight: bold;
    }
    .nav-links a {
      color: white;
      text-decoration: none;
      margin-left: 1.5rem;
      transition: color 0.3s;
    }
    .nav-links a:hover {
      color: #3498db;
    }
    main {
      min-height: calc(100vh - 60px);
    }
  `]
})
export class AppComponent {
  title = 'E-Commerce Platform';
}
