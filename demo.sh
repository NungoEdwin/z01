#!/bin/bash
# AUDIT DEMONSTRATION SCRIPT
# Run this to show everything works!

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     NEXUS PROJECT - AUDIT DEMONSTRATION                   ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

echo "📋 DEMONSTRATION MENU:"
echo ""
echo "1. Show Working Application"
echo "2. Show Build & Tests"
echo "3. Show Docker Integration"
echo "4. Show Nexus Configuration"
echo "5. Show Documentation"
echo "6. Run ALL Tests"
echo ""
read -p "Choose (1-6): " choice

case $choice in
    1)
        echo "🚀 Starting Spring Boot Application..."
        echo "Run in another terminal: ./test-app.sh"
        mvn spring-boot:run
        ;;
    2)
        echo "🔨 Building Project..."
        mvn clean package
        echo ""
        echo "🧪 Running Tests..."
        mvn test
        ;;
    3)
        echo "🐳 Building Docker Image..."
        docker build -t nexus-web-app:demo .
        echo ""
        echo "✅ Docker image built successfully!"
        ;;
    4)
        echo "⚙️  NEXUS CONFIGURATION:"
        echo ""
        echo "--- Setup Script (non-root user) ---"
        cat scripts/setup-nexus.sh | grep -A 3 "nexus user"
        echo ""
        echo "--- Maven Distribution Management ---"
        cat pom.xml | grep -A 15 "distributionManagement"
        echo ""
        echo "--- Maven Settings (Authentication) ---"
        cat maven-settings.xml | grep -A 10 "servers"
        ;;
    5)
        echo "📚 DOCUMENTATION:"
        echo ""
        ls -lh *.md docs/*.md
        echo ""
        echo "Total documentation lines:"
        wc -l README.md docs/*.md | tail -1
        ;;
    6)
        echo "🧪 Running ALL Tests..."
        ./run-tests.sh
        ;;
    *)
        echo "Invalid choice"
        ;;
esac
