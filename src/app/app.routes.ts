import { Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { CaptchaComponent } from './component/captcha/captcha.component';
import { ResultComponent } from './component/result/result.component';
import { resultGuard } from './guards/result.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'captcha', component: CaptchaComponent },
  { 
    path: 'result', 
    component: ResultComponent, 
    canActivate: [resultGuard] 
  },
  { path: '**', redirectTo: '' }
];