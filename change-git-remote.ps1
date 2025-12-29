# Script để thay đổi Git remote URL
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Change Git Remote URL" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if Git is initialized
if (-not (Test-Path ".git")) {
    Write-Host "ERROR: Not a Git repository!" -ForegroundColor Red
    Write-Host "Please run this script in the root of your Git project." -ForegroundColor Yellow
    pause
    exit 1
}

# Show current remote
Write-Host "[1/4] Current remote configuration:" -ForegroundColor Yellow
git remote -v
Write-Host ""

# Check for uncommitted changes
Write-Host "[2/4] Checking for uncommitted changes..." -ForegroundColor Yellow
$status = git status --porcelain
if ($status) {
    Write-Host "WARNING: You have uncommitted changes!" -ForegroundColor Yellow
    Write-Host ""
    $response = Read-Host "Do you want to commit them now? (y/n)"
    if ($response -eq "y" -or $response -eq "Y") {
        Write-Host "Staging all changes..." -ForegroundColor Gray
        git add .
        $commitMsg = Read-Host "Enter commit message (or press Enter for default)"
        if ([string]::IsNullOrWhiteSpace($commitMsg)) {
            $commitMsg = "Save changes before changing remote"
        }
        git commit -m $commitMsg
        Write-Host "Changes committed!" -ForegroundColor Green
    } else {
        Write-Host "Please commit or stash your changes before continuing." -ForegroundColor Yellow
        pause
        exit 1
    }
} else {
    Write-Host "No uncommitted changes. Good!" -ForegroundColor Green
}
Write-Host ""

# Get new remote URL
Write-Host "[3/4] Enter new repository URL:" -ForegroundColor Yellow
Write-Host "Examples:" -ForegroundColor Gray
Write-Host "  HTTPS: https://github.com/username/repo-name.git" -ForegroundColor Gray
Write-Host "  SSH:   git@github.com:username/repo-name.git" -ForegroundColor Gray
Write-Host ""
$newUrl = Read-Host "New remote URL"

if ([string]::IsNullOrWhiteSpace($newUrl)) {
    Write-Host "ERROR: URL cannot be empty!" -ForegroundColor Red
    pause
    exit 1
}

# Confirm
Write-Host ""
Write-Host "You are about to change remote to:" -ForegroundColor Yellow
Write-Host "  $newUrl" -ForegroundColor Cyan
Write-Host ""
$confirm = Read-Host "Continue? (y/n)"

if ($confirm -ne "y" -and $confirm -ne "Y") {
    Write-Host "Cancelled." -ForegroundColor Yellow
    pause
    exit 0
}

# Change remote
Write-Host ""
Write-Host "[4/4] Changing remote URL..." -ForegroundColor Yellow

# Check if origin exists
$originExists = git remote | Select-String -Pattern "^origin$"
if ($originExists) {
    git remote set-url origin $newUrl
    Write-Host "Updated existing 'origin' remote." -ForegroundColor Green
} else {
    git remote add origin $newUrl
    Write-Host "Added new 'origin' remote." -ForegroundColor Green
}

# Verify
Write-Host ""
Write-Host "New remote configuration:" -ForegroundColor Yellow
git remote -v
Write-Host ""

# Ask if want to push
Write-Host "Do you want to push code to the new repository now?" -ForegroundColor Yellow
$push = Read-Host "Push now? (y/n)"

if ($push -eq "y" -or $push -eq "Y") {
    Write-Host ""
    Write-Host "Pushing code..." -ForegroundColor Yellow
    
    # Get current branch
    $currentBranch = git branch --show-current
    Write-Host "Current branch: $currentBranch" -ForegroundColor Gray
    
    # Push
    git push -u origin $currentBranch
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "========================================" -ForegroundColor Green
        Write-Host "  Successfully pushed to new remote!" -ForegroundColor Green
        Write-Host "========================================" -ForegroundColor Green
    } else {
        Write-Host ""
        Write-Host "ERROR: Push failed!" -ForegroundColor Red
        Write-Host "Please check:" -ForegroundColor Yellow
        Write-Host "  1. Repository URL is correct" -ForegroundColor Yellow
        Write-Host "  2. You have access to the repository" -ForegroundColor Yellow
        Write-Host "  3. Authentication is set up (SSH key or token)" -ForegroundColor Yellow
    }
} else {
    Write-Host ""
    Write-Host "Remote URL changed successfully!" -ForegroundColor Green
    Write-Host "You can push later with: git push -u origin <branch-name>" -ForegroundColor Gray
}

Write-Host ""
pause

