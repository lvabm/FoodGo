# Script don gian de deploy
Write-Host "Bat dau deploy..." -ForegroundColor Green

# Set project
Write-Host "Setting project..." -ForegroundColor Yellow
gcloud config set project foodgo-app-482706

# Enable APIs
Write-Host "Enabling APIs..." -ForegroundColor Yellow
gcloud services enable run.googleapis.com --quiet
gcloud services enable cloudbuild.googleapis.com --quiet
gcloud services enable containerregistry.googleapis.com --quiet

# Configure Docker
Write-Host "Configuring Docker..." -ForegroundColor Yellow
gcloud auth configure-docker --quiet

# Build
Write-Host "Building..." -ForegroundColor Yellow
cd backend
mvn clean package -DskipTests

# Build Docker
Write-Host "Building Docker image..." -ForegroundColor Yellow
docker build -t gcr.io/foodgo-app-482706/foodgo-backend:latest .

# Push
Write-Host "Pushing image..." -ForegroundColor Yellow
docker push gcr.io/foodgo-app-482706/foodgo-backend:latest

# Deploy
Write-Host "Deploying..." -ForegroundColor Yellow
gcloud run deploy foodgo-backend --image gcr.io/foodgo-app-482706/foodgo-backend:latest --platform managed --region asia-southeast1 --allow-unauthenticated --port 8080 --memory 512Mi --set-env-vars SPRING_PROFILES_ACTIVE=prod

cd ..

# Get URL
Write-Host "Getting URL..." -ForegroundColor Yellow
$url = gcloud run services describe foodgo-backend --region asia-southeast1 --format="value(status.url)"
Write-Host "Backend URL: $url" -ForegroundColor Green

pause

