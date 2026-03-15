# CI/CD Pipeline Setup Guide

## GitHub Actions Setup

### 1. Add Secrets to GitHub Repository

Navigate to: Repository → Settings → Secrets and variables → Actions

Add secrets:
- `NEXUS_USERNAME`: Your Nexus username (e.g., admin)
- `NEXUS_PASSWORD`: Your Nexus password

### 2. Pipeline Triggers

The pipeline automatically runs on:
- Push to main/master/develop branches
- Pull requests to main/master

### 3. Pipeline Stages

1. Checkout code
2. Setup JDK 11
3. Build with Maven
4. Run tests
5. Publish artifacts to Nexus
6. Build Docker image
7. Push Docker image to Nexus

## Jenkins Setup

### 1. Install Required Plugins

- Maven Integration Plugin
- Docker Pipeline Plugin
- Credentials Binding Plugin

### 2. Configure Tools

Navigate to: Manage Jenkins → Global Tool Configuration

Add:
- JDK 11 (name: `JDK-11`)
- Maven 3.8+ (name: `Maven-3.8`)

### 3. Add Credentials

Navigate to: Manage Jenkins → Manage Credentials

Add:
- ID: `nexus-credentials`
- Type: Username with password
- Username: admin
- Password: your-nexus-password

### 4. Create Pipeline Job

1. New Item → Pipeline
2. Configure:
   - Pipeline script from SCM
   - SCM: Git
   - Repository URL: your-repo-url
   - Script Path: Jenkinsfile

## Verify Pipeline

After pushing code, verify:
1. Build completes successfully
2. Tests pass
3. Artifacts appear in Nexus
4. Docker image is pushed
