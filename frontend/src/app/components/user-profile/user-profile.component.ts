import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  userId: number = 1;
  stats: any = {};

  constructor(private orderService: OrderService) {}

  ngOnInit() {
    this.loadStats();
  }

  loadStats() {
    this.orderService.getBuyerStats(this.userId).subscribe({
      next: (data) => this.stats = data,
      error: (err) => console.error('Error loading stats', err)
    });
  }
}
