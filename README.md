## Angul-It: Multi-Stage Captcha Application

Angul-It is a robust, multiple-stage captcha web application built using the latest version of Angular. It is designed to challenge users with varied tasks—including image identification, mathematical problem solving, and text input—to verify they are human.

## 🚀 Features

    Multi-Stage Challenges: A linear progression through diverse captcha types.

    State Management & Persistence: Powered by Angular Signals and localStorage. Progress remains intact even after a page refresh.

    Randomized Challenge Sets: Challenges are randomly selected and shuffled from a master pool for every session.

    ## Navigation Integrity:

        Users can revisit previous stages using a "Back" button.

        A Result Guard prevents direct access to the success page unless all challenges are completed.

    Responsive Design: Optimized for seamless use on desktop, tablet, and mobile devices.

    Form Validation: Robust validation ensures users cannot proceed without providing the correct answer.

   ## 🛠️ Installation & Setup
Prerequisites

    - Node.js (Latest LTS version recommended)

    - Angular CLI installed globally (npm install -g @angular/cli)

Steps to Run

    Clone the repository:
    code Bash

    git clone https://github.com/enungo/anguil-it.git

    Navigate into the project directory:
    code Bash

    cd anguil-it

    Install dependencies:
    code Bash

    npm install

    Start the development server:
    code Bash

    ng serve

    Open the app:
    Navigate to http://localhost:4200/ in your browser.

## 📁 Project Structure

    src/app/services/captcha.service.ts: The core "brain" of the app. Manages state, handles randomization, and syncs data with localStorage.

    src/app/guards/result.guard.ts: Ensures the security of the results page.

    src/app/components/:

        HomeComponent: Entry point for the user.

        CaptchaComponent: Handles the logic for image grids, math, and text challenges.

        ResultComponent: Final destination proving the user is not a bot.

## ✅ Audit Checklist Verification

This project has been built to satisfy the following requirements:
Functional Requirements

    Component Creation: Separate components for Home, Captcha, and Result.

    Captcha Challenges: Implements image selection, math problems, and text input.

    Form Validation: "Next" button is disabled until valid input is detected; incorrect answers trigger alerts.

    State Management: Uses Angular Signals and localStorage to survive page refreshes.

    Results Page: Accessible only after completing all stages; provides "Not a bot" feedback.

## Bonus Requirements

    Multiple Challenge Sets: Challenges are randomly assigned and shuffled using a master pool logic.

    Responsiveness: CSS Grid and Flexbox ensure the application works on all screen sizes.

    Navigation: Includes a "Back" button to revisit previous challenges without losing progress.

## 🧪 Testing the Application

    Start a Session: Click "Start Verification" on the Home page.

    Verify Persistence: Complete Stage 1, then Refresh the browser. You should remain on Stage 2.

    Verify Security: Try to manually navigate to /result via the address bar. You should be redirected back to the captcha.

    Verify Randomization: Finish the captcha, reach the result page, and click "Try New Challenge." You will receive a different set/order of challenges.

## 🛠️ Technologies Used

    - Angular (Latest Version)

    - Angular Signals (State Management)

    - TypeScript

    - SCSS (Responsive Styling)

    - LocalStorage API