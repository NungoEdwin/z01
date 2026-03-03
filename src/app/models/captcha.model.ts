export type ChallengeType = 'image' | 'math' | 'text';

export interface Challenge {
  id: number;
  type: ChallengeType;
  question: string;
  answer: any;
  options?: string[]; // For image grid selection
}

export interface AppState {
  currentStage: number;
  completed: boolean;
  challenges: Challenge[];
  userAnswers: { [key: number]: any };
}