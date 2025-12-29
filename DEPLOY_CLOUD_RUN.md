# ☁️ Deploy Backend lên Google Cloud Run

## ✅ Tại sao Cloud Run?

- ✅ **Serverless** - Pay per use, auto-scaling
- ✅ **Free tier tốt** - 2 triệu requests/tháng
- ✅ **Tích hợp tốt** với Firebase
- ✅ **Nhanh** - Global CDN
- ✅ **Dễ scale** - Tự động scale theo traffic

---

## 🚀 Các bước Deploy

### Bước 1: Setup Google Cloud

#### 1.1. Tạo Google Cloud Project

1. Vào https://console.cloud.google.com
2. Click **"Select a project"** > **"New Project"**
3. Đặt tên: `foodgo-app` (hoặc tên bạn muốn)
4. Click **"Create"**
5. Chọn project vừa tạo

#### 1.2. Enable APIs

1. Vào **APIs & Services** > **Library**
2. Tìm và enable:
   - **Cloud Run API**
   - **Cloud Build API**
   - **Container Registry API** (hoặc Artifact Registry)

#### 1.3. Install Google Cloud SDK

**Windows (PowerShell):**
```powershell
# Download và cài đặt từ:
# https://cloud.google.com/sdk/docs/install

# Hoặc dùng Chocolatey:
choco install gcloudsdk
```

**Mac:**
```bash
brew install --cask google-cloud-sdk
```

**Linux:**
```bash
curl https://sdk.cloud.google.com | bash
exec -l $SHELL
```

### Bước 2: Login và Setup

```bash
# Login
gcloud auth login

# Set project
gcloud config set project YOUR_PROJECT_ID

# Enable APIs
gcloud services enable run.googleapis.com
gcloud services enable cloudbuild.googleapis.com
gcloud services enable containerregistry.googleapis.com
```

### Bước 3: Build Docker Image

```bash
# Vào thư mục backend
cd backend

# Build JAR (nếu chưa build)
mvn clean package -DskipTests

# Build Docker image
docker build -t gcr.io/YOUR_PROJECT_ID/foodgo-backend:latest .

# Nếu chưa có Docker, cài Docker Desktop:
# https://www.docker.com/products/docker-desktop
```

### Bước 4: Push Image lên Container Registry

```bash
# Configure Docker để push lên GCR
gcloud auth configure-docker

# Push image
docker push gcr.io/YOUR_PROJECT_ID/foodgo-backend:latest
```

### Bước 5: Deploy lên Cloud Run

```bash
gcloud run deploy foodgo-backend \
  --image gcr.io/YOUR_PROJECT_ID/foodgo-backend:latest \
  --platform managed \
  --region asia-southeast1 \
  --allow-unauthenticated \
  --port 8080 \
  --memory 512Mi \
  --cpu 1 \
  --min-instances 0 \
  --max-instances 10 \
  --set-env-vars SPRING_PROFILES_ACTIVE=prod
```

### Bước 6: Set Environment Variables

```bash
gcloud run services update foodgo-backend \
  --region asia-southeast1 \
  --set-env-vars \
    SPRING_PROFILES_ACTIVE=prod,\
    PORT=8080,\
    JWT_SECRET=MTAwUGVyY2VudFNlY3JldEtleUZvckZvb2RHb0JhY2tlbmRNYXlCRTEyMzQ1Njc4OQ==,\
    JWT_EXPIRATION=86400000
```

**Hoặc set qua Console:**
1. Vào Cloud Run > foodgo-backend > Edit & Deploy New Revision
2. Variables & Secrets > Add Variable
3. Thêm từng variable

### Bước 7: Setup Database (nếu cần)

#### Option A: Cloud SQL (Production)

```bash
# Tạo Cloud SQL instance
gcloud sql instances create foodgo-db \
  --database-version=POSTGRES_15 \
  --tier=db-f1-micro \
  --region=asia-southeast1

# Tạo database
gcloud sql databases create foodgo_db --instance=foodgo-db

# Tạo user
gcloud sql users create foodgo_user \
  --instance=foodgo-db \
  --password=YOUR_PASSWORD

# Lấy connection name
gcloud sql instances describe foodgo-db --format="value(connectionName)"
# Output: PROJECT_ID:asia-southeast1:foodgo-db
```

**Connect từ Cloud Run:**
```bash
gcloud run services update foodgo-backend \
  --region asia-southeast1 \
  --add-cloudsql-instances PROJECT_ID:asia-southeast1:foodgo-db \
  --set-env-vars \
    SPRING_DATASOURCE_URL=jdbc:postgresql:///foodgo_db?cloudSqlInstance=PROJECT_ID:asia-southeast1:foodgo-db&socketFactory=com.google.cloud.sql.postgres.SocketFactory,\
    SPRING_DATASOURCE_USERNAME=foodgo_user,\
    SPRING_DATASOURCE_PASSWORD=YOUR_PASSWORD
```

#### Option B: External Database (Supabase, Railway PostgreSQL, etc.)

Chỉ cần set environment variables:
```bash
gcloud run services update foodgo-backend \
  --region asia-southeast1 \
  --set-env-vars \
    SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/foodgo_db,\
    SPRING_DATASOURCE_USERNAME=your_user,\
    SPRING_DATASOURCE_PASSWORD=your_password
```

---

## 🔄 Auto-Deploy từ GitHub (CI/CD)

### Bước 1: Tạo Cloud Build Trigger

1. Vào **Cloud Build** > **Triggers**
2. Click **"Create Trigger"**
3. Connect GitHub repository
4. Cấu hình:
   ```
   Name: foodgo-backend-deploy
   Event: Push to a branch
   Branch: ^main$
   Configuration: Cloud Build configuration file
   Location: backend/cloudbuild.yaml
   ```

### Bước 2: File cloudbuild.yaml đã có

File `backend/cloudbuild.yaml` đã được tạo sẵn!

### Bước 3: Push code

```bash
git add .
git commit -m "Setup Cloud Run deployment"
git push origin main
```

Cloud Build tự động build và deploy!

---

## 📝 Sau khi deploy

### 1. Lấy Backend URL

Sau khi deploy, bạn sẽ nhận được URL:
```
https://foodgo-backend-xxxxx-xx.a.run.app
```

Hoặc xem trong Console:
```bash
gcloud run services describe foodgo-backend \
  --region asia-southeast1 \
  --format="value(status.url)"
```

### 2. Test API

```bash
curl https://foodgo-backend-xxxxx-xx.a.run.app/api/v1/statistics/public
```

### 3. Cập nhật Frontend

Tạo file `frontend/.env.production`:
```env
VITE_API_BASE_URL=https://foodgo-backend-xxxxx-xx.a.run.app/api/v1
```

### 4. Rebuild và Deploy Frontend

```bash
cd frontend
npm run build
cd ..
firebase deploy --only hosting
```

---

## 🔧 Cấu hình nâng cao

### Custom Domain

1. Vào Cloud Run > foodgo-backend > Manage Custom Domains
2. Add domain
3. Update DNS records
4. Wait for SSL certificate (tự động)

### Auto-scaling

```bash
gcloud run services update foodgo-backend \
  --region asia-southeast1 \
  --min-instances 1 \
  --max-instances 100 \
  --cpu-throttling
```

### Memory và CPU

```bash
gcloud run services update foodgo-backend \
  --region asia-southeast1 \
  --memory 1Gi \
  --cpu 2
```

---

## 💰 Pricing

### Free Tier:
- **2 triệu requests/tháng**
- **400,000 GB-seconds memory**
- **200,000 vCPU-seconds**
- **1 GB egress/tháng**

### Pay per use:
- **$0.40 per million requests** (sau free tier)
- **$0.0000025 per GB-second memory**
- **$0.00002400 per vCPU-second**

**Ước tính:** Với 10,000 requests/tháng → **FREE** ✅

---

## 🔒 Security

### IAM Roles

```bash
# Cho phép unauthenticated access (public API)
gcloud run services add-iam-policy-binding foodgo-backend \
  --region asia-southeast1 \
  --member="allUsers" \
  --role="roles/run.invoker"

# Hoặc chỉ cho phép authenticated users
gcloud run services remove-iam-policy-binding foodgo-backend \
  --region asia-southeast1 \
  --member="allUsers" \
  --role="roles/run.invoker"
```

### CORS Configuration

Backend cần cho phép requests từ Firebase Hosting:
```java
.allowedOrigins(
    "https://testfirebase-2e513.web.app",
    "https://testfirebase-2e513.firebaseapp.com"
)
```

---

## ✅ Checklist

- [ ] Đã tạo Google Cloud project
- [ ] Đã enable Cloud Run API
- [ ] Đã install Google Cloud SDK
- [ ] Đã login: `gcloud auth login`
- [ ] Đã set project: `gcloud config set project`
- [ ] Đã build Docker image
- [ ] Đã push image lên Container Registry
- [ ] Đã deploy lên Cloud Run
- [ ] Đã set environment variables
- [ ] Đã setup database (nếu cần)
- [ ] Đã test API endpoint
- [ ] Đã cập nhật frontend API URL
- [ ] Đã rebuild và redeploy frontend

---

## 🆘 Troubleshooting

### Lỗi "Permission denied"
```bash
gcloud auth login
gcloud auth application-default login
```

### Lỗi "API not enabled"
```bash
gcloud services enable run.googleapis.com
gcloud services enable cloudbuild.googleapis.com
```

### Lỗi "Image not found"
- Kiểm tra image đã push chưa: `gcloud container images list`
- Kiểm tra project ID có đúng không

### Lỗi "Port already in use"
- Cloud Run tự động set PORT environment variable
- Backend cần đọc từ `System.getenv("PORT")` hoặc `server.port=${PORT:8080}`

---

## 🎉 Kết quả

Sau khi deploy thành công:
- ✅ Backend URL: `https://foodgo-backend-xxxxx-xx.a.run.app`
- ✅ Auto-scaling
- ✅ Global CDN
- ✅ Free tier: 2 triệu requests/tháng
- ✅ Tích hợp tốt với Firebase

**Cloud Run là lựa chọn tốt nhất cho production!** 🚀

