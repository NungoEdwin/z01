import { Injectable, signal, effect, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export interface Challenge {
  id: number;
  type: 'image' | 'math' | 'text';
  question: string;
  answer: any;
  options?: string[];
}

@Injectable({ providedIn: 'root' })
export class CaptchaService {
  private readonly STORAGE_KEY = 'angul_it_state';
  private platformId = inject(PLATFORM_ID); // Get the platform ID

  // State Signals
  challenges = signal<Challenge[]>([]);
  currentStage = signal<number>(0);
  userAnswers = signal<{ [key: number]: any }>({});
  isCompleted = signal<boolean>(false);

  constructor() {
    // Only run this logic if we are in the browser
    if (isPlatformBrowser(this.platformId)) {
      this.loadInitialState();

      effect(() => {
        const state = {
          challenges: this.challenges(),
          currentStage: this.currentStage(),
          userAnswers: this.userAnswers(),
          isCompleted: this.isCompleted()
        };
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(state));
      });
    } else {
      // Provide a default empty state for the Server-side render
      this.resetSession();
    }
  }

  private loadInitialState() {
    // Standard browser check
    if (!isPlatformBrowser(this.platformId)) return;

    const saved = localStorage.getItem(this.STORAGE_KEY);
    if (saved) {
      const parsed = JSON.parse(saved);
      this.challenges.set(parsed.challenges);
      this.currentStage.set(parsed.currentStage);
      this.userAnswers.set(parsed.userAnswers);
      this.isCompleted.set(parsed.isCompleted);
    } else {
      this.resetSession();
    }
  }

  resetSession() {
  // 1. Create a larger pool of diverse challenges (Bonus requirement)
  const masterPool: Challenge[] = [
    { id: 1, type: 'math', question: 'Solve: 12 + 8', answer: '20' },
    { id: 2, type: 'math', question: 'What is 5 x 6?', answer: '30' },
    { id: 3, type: 'text', question: 'Type "ANGULAR" in lowercase', answer: 'angular' },
    { id: 4, type: 'text', question: 'Type "ROBOT" backwards', answer: 'TOBOR' },
    { id: 5, type: 'image', question: 'Select all Fruits (🍎, 🍌)', answer: [0, 2], options: ['🍎', '🚗', '🍌', '🏠', '🚁', '🏢'] },
    { id: 6, type: 'image', question: 'Select all Vehicles (🚗, 🚁)', answer: [1, 4], options: ['🍎', '🚗', '🍌', '🏠', '🚁', '🏢'] },
    { id: 7, type: 'math', question: 'What is 100 - 55?', answer: '45' },
    { id: 8, type: 'image', question: 'Select all Buildings (🏠, 🏢)', answer: [3, 5], options: ['🍎', '🚗', '🍌', '🏠', '🚁', '🏢'] }
  ];

  // 2. Shuffle the master pool
  const shuffled = masterPool.sort(() => Math.random() - 0.5);

  // 3. Pick only 3 random challenges for THIS session
  // This ensures the user gets a different experience every time
  const selectedChallenges = shuffled.slice(0, 3);

  // 4. Reset all signals
  this.challenges.set(selectedChallenges);
  this.currentStage.set(0);
  this.userAnswers.set({});
  this.isCompleted.set(false);
  
  // 5. Force clear localStorage to ensure refresh doesn't bring back old data
  if (typeof window !== 'undefined') {
    localStorage.removeItem(this.STORAGE_KEY);
  }
}
}