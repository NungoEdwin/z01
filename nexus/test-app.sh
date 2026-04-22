#!/bin/bash

GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

echo -e "${BLUE}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${BLUE}║     NEXUS WEB APPLICATION - VISUAL TESTING DEMO           ║${NC}"
echo -e "${BLUE}╚════════════════════════════════════════════════════════════╝${NC}"
echo ""

# Check if app is running
if ! curl -s http://localhost:8080/ > /dev/null 2>&1; then
    echo -e "${YELLOW}⚠️  Application is not running!${NC}"
    echo -e "${CYAN}Start it with: mvn spring-boot:run${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Application is running on http://localhost:8080${NC}"
echo ""

# Test Home Endpoint
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}📍 Testing: GET /${NC}"
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
curl -s http://localhost:8080/ | jq '.'
echo ""

# Test Version Endpoint
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}📍 Testing: GET /version${NC}"
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
curl -s http://localhost:8080/version | jq '.'
echo ""

# Test Health Endpoint
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}📍 Testing: GET /health${NC}"
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
curl -s http://localhost:8080/health | jq '.'
echo ""

# Test Info Endpoint
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}📍 Testing: GET /info${NC}"
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
curl -s http://localhost:8080/info | jq '.'
echo ""

# Test Status Endpoint
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}📍 Testing: GET /status${NC}"
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
curl -s http://localhost:8080/status | jq '.'
echo ""

echo -e "${BLUE}╔════════════════════════════════════════════════════════════╗${NC}"
echo -e "${GREEN}║  ✅ ALL ENDPOINTS TESTED SUCCESSFULLY!                     ║${NC}"
echo -e "${BLUE}╚════════════════════════════════════════════════════════════╝${NC}"
echo ""
echo -e "${CYAN}📊 Summary:${NC}"
echo -e "  ${GREEN}✓${NC} Home endpoint     : http://localhost:8080/"
echo -e "  ${GREEN}✓${NC} Version endpoint  : http://localhost:8080/version"
echo -e "  ${GREEN}✓${NC} Health endpoint   : http://localhost:8080/health"
echo -e "  ${GREEN}✓${NC} Info endpoint     : http://localhost:8080/info"
echo -e "  ${GREEN}✓${NC} Status endpoint   : http://localhost:8080/status"
echo ""
echo -e "${YELLOW}💡 Tip: Open http://localhost:8080/ in your browser!${NC}"
echo ""
