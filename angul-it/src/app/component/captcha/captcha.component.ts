import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CaptchaService } from '../../services/captcha.service';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './captcha.component.html',
  styleUrls: ['./captcha.component.scss']
})
export class CaptchaComponent {
  service = inject(CaptchaService);
  router = inject(Router);

  // Local UI state
  textInput = '';
  selectedImages = signal<number[]>([]);

  get currentChallenge() {
    return this.service.challenges()[this.service.currentStage()];
  }

  toggleImage(index: number) {
    const current = this.selectedImages();
    if (current.includes(index)) {
      this.selectedImages.set(current.filter(i => i !== index));
    } else {
      this.selectedImages.set([...current, index]);
    }
  }

  isValid() {
    if (this.currentChallenge.type === 'image') return this.selectedImages().length > 0;
    return this.textInput.trim().length > 0;
  }

  next() {
    const userAnswer = this.currentChallenge.type === 'image' ? this.selectedImages().sort() : this.textInput.trim();
    const correctAnswer = this.currentChallenge.answer;

    if (JSON.stringify(userAnswer) === JSON.stringify(correctAnswer)) {
      if (this.service.currentStage() === this.service.challenges().length - 1) {
        this.service.isCompleted.set(true);
        this.router.navigate(['/result']);
      } else {
        this.service.currentStage.update(s => s + 1);
        this.textInput = '';
        this.selectedImages.set([]);
      }
    } else {
      alert("Incorrect answer. Please try again.");
    }
  }

  back() {
    if (this.service.currentStage() > 0) {
      this.service.currentStage.update(s => s - 1);
    }
  }
}