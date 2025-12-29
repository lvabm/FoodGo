# ⚡ Quick Deploy lên Google Cloud Run

## 🚀 Cách nhanh nhất

### Option 1: Dùng Script (Khuyến nghị)

```powershell
.\deploy-cloud-run.ps1
```

Script sẽ tự động:
- ✅ Kiểm tra Google Cloud SDK
- ✅ Login và set project
- ✅ Enable APIs
- ✅ Build Docker image
- ✅ Push image
- ✅ Deploy lên Cloud Run

### Option 2: Thủ công (5 bước)

#### 1. Login và Setup
```bash
gcloud auth login
gcloud config set project YOUR_PROJECT_ID
gcloud services enable run.googleapis.com cloudbuild.googleapis.com
```

#### 2. Build và Push Image
```bash
cd backend
mvn clean package -DskipTests
docker build -t gcr.io/YOUR_PROJECT_ID/foodgo-backend .
gcloud auth configure-docker
docker push gcr.io/YOUR_PROJECT_ID/foodgo-backend
cd ..
```

#### 3. Deploy
```bash
gcloud run deploy foodgo-backend \
  --image gcr.io/YOUR_PROJECT_ID/foodgo-backend \
  --platform managed \
  --region asia-southeast1 \
  --allow-unauthenticated \
  --port 8080
```

#### 4. Set Environment Variables
```bash
gcloud run services update foodgo-backend \
  --region asia-southeast1 \
  --set-env-vars \
    SPRING_PROFILES_ACTIVE=prod,\
    JWT_SECRET=MTAwUGVyY2VudFNlY3JldEtleUZvckZvb2RHb0JhY2tlbmRNYXlCRTEyMzQ1Njc4OQ==,\
    JWT_EXPIRATION=86400000
```

#### 5. Lấy URL và cập nhật Frontend
```bash
# Lấy URL
gcloud run services describe foodgo-backend \
  --region asia-southeast1 \
  --format="value(status.url)"

# Cập nhật frontend/.env.production
# VITE_API_BASE_URL=<URL>/api/v1
```

---

## 📋 Yêu cầu

- ✅ Google Cloud account (free tier có $300 credit)
- ✅ Google Cloud SDK đã cài
- ✅ Docker Desktop đã cài và chạy
- ✅ Maven đã cài (hoặc dùng mvnw)

---

## 🎯 Sau khi deploy

Bạn sẽ có:
- ✅ Backend URL: `https://foodgo-backend-xxxxx-xx.a.run.app`
- ✅ Auto-scaling
- ✅ Free tier: 2 triệu requests/tháng
- ✅ Tích hợp tốt với Firebase

**Chúc mừng! Backend đã live trên Cloud Run!** 🎉

