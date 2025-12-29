# Script để deploy lên Google Cloud Run
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Deploy to Google Cloud Run" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if gcloud is installed
$gcloudCheck = Get-Command gcloud -ErrorAction SilentlyContinue
if (-not $gcloudCheck) {
    Write-Host "ERROR: Google Cloud SDK not found!" -ForegroundColor Red
    Write-Host "Please install from: https://cloud.google.com/sdk/docs/install" -ForegroundColor Yellow
    pause
    exit 1
}

Write-Host "[1/6] Checking Google Cloud SDK..." -ForegroundColor Yellow
gcloud --version
Write-Host ""

# Get project ID
Write-Host "[2/6] Getting Google Cloud project..." -ForegroundColor Yellow
$projectId = gcloud config get-value project 2>&1
if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($projectId)) {
    Write-Host "No project set. Please set project:" -ForegroundColor Yellow
    Write-Host "  gcloud config set project YOUR_PROJECT_ID" -ForegroundColor Gray
    Write-Host ""
    $projectId = Read-Host "Enter your Google Cloud Project ID"
    if ([string]::IsNullOrWhiteSpace($projectId)) {
        Write-Host "ERROR: Project ID cannot be empty!" -ForegroundColor Red
        pause
        exit 1
    }
    gcloud config set project $projectId
} else {
    Write-Host "Current project: $projectId" -ForegroundColor Green
    $confirm = Read-Host "Use this project? (y/n)"
    if ($confirm -ne "y" -and $confirm -ne "Y") {
        $projectId = Read-Host "Enter your Google Cloud Project ID"
        gcloud config set project $projectId
    }
}
Write-Host ""

# Check if logged in
Write-Host "[3/6] Checking authentication..." -ForegroundColor Yellow
gcloud auth list --filter=status:ACTIVE --format="value(account)" | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "Not logged in. Logging in..." -ForegroundColor Yellow
    gcloud auth login
}
Write-Host ""

# Enable APIs
Write-Host "[4/6] Enabling required APIs..." -ForegroundColor Yellow
gcloud services enable run.googleapis.com --quiet
gcloud services enable cloudbuild.googleapis.com --quiet
gcloud services enable containerregistry.googleapis.com --quiet
Write-Host "APIs enabled!" -ForegroundColor Green
Write-Host ""

# Build and push Docker image
Write-Host "[5/6] Building and pushing Docker image..." -ForegroundColor Yellow
Write-Host "This may take a few minutes..." -ForegroundColor Gray

# Check if Docker is running
$dockerCheck = Get-Command docker -ErrorAction SilentlyContinue
if (-not $dockerCheck) {
    Write-Host "ERROR: Docker not found!" -ForegroundColor Red
    Write-Host "Please install Docker Desktop: https://www.docker.com/products/docker-desktop" -ForegroundColor Yellow
    pause
    exit 1
}

# Configure Docker for GCR
Write-Host "Configuring Docker for Google Container Registry..." -ForegroundColor Gray
gcloud auth configure-docker --quiet

# Build JAR
Write-Host "Building JAR file..." -ForegroundColor Gray
Set-Location backend
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Maven build failed!" -ForegroundColor Red
    Set-Location ..
    pause
    exit 1
}

# Build Docker image
Write-Host "Building Docker image..." -ForegroundColor Gray
$imageTag = "gcr.io/$projectId/foodgo-backend:latest"
docker build -t $imageTag .
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Docker build failed!" -ForegroundColor Red
    Set-Location ..
    pause
    exit 1
}

# Push image
Write-Host "Pushing image to Container Registry..." -ForegroundColor Gray
docker push $imageTag
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Docker push failed!" -ForegroundColor Red
    Set-Location ..
    pause
    exit 1
}

Set-Location ..
Write-Host "Image pushed successfully!" -ForegroundColor Green
Write-Host ""

# Deploy to Cloud Run
Write-Host "[6/6] Deploying to Cloud Run..." -ForegroundColor Yellow
Write-Host "This may take a few minutes..." -ForegroundColor Gray

$region = Read-Host "Enter region (default: asia-southeast1)"
if ([string]::IsNullOrWhiteSpace($region)) {
    $region = "asia-southeast1"
}

gcloud run deploy foodgo-backend `
  --image $imageTag `
  --platform managed `
  --region $region `
  --allow-unauthenticated `
  --port 8080 `
  --memory 512Mi `
  --cpu 1 `
  --min-instances 0 `
  --max-instances 10 `
  --set-env-vars SPRING_PROFILES_ACTIVE=prod

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Deploy failed!" -ForegroundColor Red
    pause
    exit 1
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Deployment Successful!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

# Get service URL
Write-Host "Getting service URL..." -ForegroundColor Yellow
$serviceUrl = gcloud run services describe foodgo-backend --region $region --format="value(status.url)"
Write-Host ""
Write-Host "Backend URL: $serviceUrl" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Update frontend/.env.production:" -ForegroundColor Gray
Write-Host "   VITE_API_BASE_URL=$serviceUrl/api/v1" -ForegroundColor Gray
Write-Host ""
Write-Host "2. Set environment variables in Cloud Run:" -ForegroundColor Gray
Write-Host "   gcloud run services update foodgo-backend --region $region --set-env-vars JWT_SECRET=..." -ForegroundColor Gray
Write-Host ""
Write-Host "3. Test API:" -ForegroundColor Gray
Write-Host "   curl $serviceUrl/api/v1/statistics/public" -ForegroundColor Gray
Write-Host ""

pause

