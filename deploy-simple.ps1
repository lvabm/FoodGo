# Simple Deploy Script for FoodGo
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  FoodGo - Firebase Deploy" -ForegroundColor Cyan  
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Step 1: Check Firebase
Write-Host "[1/5] Checking Firebase..." -ForegroundColor Yellow
firebase --version
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Firebase CLI not found!" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Step 2: Set Firebase project
Write-Host "[2/5] Setting Firebase project..." -ForegroundColor Yellow
firebase use testfirebase-2e513
if ($LASTEXITCODE -ne 0) {
    Write-Host "WARNING: Could not set project. Make sure you're in the project root." -ForegroundColor Yellow
}
Write-Host ""

# Step 3: Install dependencies
Write-Host "[3/5] Installing frontend dependencies..." -ForegroundColor Yellow
Push-Location frontend
npm install
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: npm install failed!" -ForegroundColor Red
    Pop-Location
    exit 1
}
Write-Host ""

# Step 4: Build
Write-Host "[4/5] Building frontend..." -ForegroundColor Yellow
npm run build
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Build failed!" -ForegroundColor Red
    Pop-Location
    exit 1
}
Pop-Location
Write-Host "Build successful!" -ForegroundColor Green
Write-Host ""

# Step 5: Deploy
Write-Host "[5/5] Deploying to Firebase..." -ForegroundColor Yellow
firebase deploy --only hosting
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Deploy failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Deployment Successful!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

