import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { CaptchaService } from '../services/captcha.service';
import { isPlatformBrowser } from '@angular/common';
import { PLATFORM_ID } from '@angular/core';

export const resultGuard: CanActivateFn = (route, state) => {
  const service = inject(CaptchaService);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);

  // If we are on the server, allow navigation to prevent hydration errors
  if (!isPlatformBrowser(platformId)) {
    return true;
  }

  // If completed, allow access. Otherwise, redirect to captcha.
  if (service.isCompleted()) {
    return true;
  }

  return router.createUrlTree(['/captcha']);
};