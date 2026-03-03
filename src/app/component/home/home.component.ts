import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CaptchaService } from '../../services/captcha.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  template: `
    <div class="container">
      <h1>Welcome to Angul-It</h1>
      <p>Please complete the security challenges to proceed.</p>
      <button (click)="start()">Start Verification</button>
    </div>
  `,
  styles: [`.container { text-align: center; margin-top: 50px; font-family: sans-serif; } 
            button { padding: 10px 20px; font-size: 1.1rem; cursor: pointer; background: #007bff; color: white; border: none; border-radius: 5px; }`]
})
export class HomeComponent {
  service = inject(CaptchaService);
  router = inject(Router);

  start() {
    this.service.resetSession();
    this.router.navigate(['/captcha']);
  }
}