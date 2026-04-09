#!/bin/bash

# Git Smart Commit - Auto-generates commit messages

echo "🚀 Git Smart Commit"
echo "=================="
echo ""

# Check if git is initialized
if [ ! -d .git ]; then
    echo "Initializing git repository..."
    git init
fi

# Get git status
echo "Analyzing changes..."
git add .

# Auto-generate commit message based on changes
if [ -z "$1" ]; then
    # Count changes
    added=$(git diff --cached --numstat | wc -l)
    modified=$(git diff --cached --name-status | grep -c '^M')
    deleted=$(git diff --cached --name-status | grep -c '^D')
    
    # Get changed files
    files=$(git diff --cached --name-only | head -3 | tr '\n' ', ' | sed 's/,$//')
    
    # Generate smart commit message
    if [ $added -gt 10 ]; then
        commit_message="feat: major updates to project structure"
    elif [ $modified -gt 5 ]; then
        commit_message="refactor: update multiple files ($files)"
    elif [ $deleted -gt 0 ]; then
        commit_message="chore: cleanup and remove unused files"
    elif [ $added -gt 0 ]; then
        commit_message="feat: add new features and improvements"
    else
        commit_message="chore: update project files"
    fi
    
    echo "📝 Auto-generated message: $commit_message"
else
    commit_message="$1"
    echo "📝 Using custom message: $commit_message"
fi

# Commit changes
echo "Committing changes..."
git commit -m "$commit_message"

# Push to remote (if configured)
if git remote | grep -q origin; then
    echo "Pushing to remote..."
    git push origin main 2>/dev/null || git push origin master 2>/dev/null || echo "Push failed - check remote configuration"
else
    echo "⚠️  No remote configured. Skipping push."
    echo "To add remote: git remote add origin <url>"
fi

echo ""
echo "✅ Done!"
