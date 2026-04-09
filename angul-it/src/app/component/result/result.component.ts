import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CaptchaService } from '../../services/captcha.service';

@Component({
  standalone: true,
  template: `
    <div style="text-align: center; margin-top: 50px;">
      <h1>🎉 Success!</h1>
      <p>You have proven you are not a bot.</p>
      <button (click)="newChallenge()">Start New Challenge</button>
    </div>
  `,
  styles: [`button { background: green; color: white; padding: 10px 20px; border: none; cursor: pointer; }`]
})
export class ResultComponent {
  service = inject(CaptchaService);
  router = inject(Router);

 newChallenge() {
  this.service.resetSession(); // This generates the new random set
  this.router.navigate(['/captcha']); // Navigate directly to start the new set
}
}