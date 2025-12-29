# 🚀 Các Options Deploy Backend (Ngoài Railway)

## 🎯 Top 3 Khuyến nghị

### 1. **Render** ⭐ (Dễ nhất, Free tier tốt)
- ✅ Free tier có sẵn
- ✅ Dễ setup
- ✅ Auto-deploy từ GitHub
- ⚠️ Sleep sau 15 phút không dùng (free tier)

### 2. **Google Cloud Run** ⭐⭐ (Tích hợp tốt với Firebase)
- ✅ Serverless, pay per use
- ✅ Free tier: 2 triệu requests/tháng
- ✅ Tích hợp tốt với Firebase
- ⚠️ Cần setup Docker

### 3. **Fly.io** ⭐ (Free tier tốt, nhanh)
- ✅ Free tier: 3 VMs
- ✅ Global edge network
- ✅ Dễ setup
- ✅ Nhanh

---

## 🎨 Option 1: Render (Khuyến nghị - Dễ nhất)

### Bước 1: Tạo account
1. Vào https://render.com
2. Đăng nhập bằng GitHub
3. Click "New +" > "Web Service"

### Bước 2: Connect GitHub
1. Chọn repository FoodGo
2. Chọn branch (thường là `main`)

### Bước 3: Cấu hình
```
Name: foodgo-backend
Environment: Docker
Region: Singapore (gần Việt Nam nhất)

Root Directory: backend
Build Command: mvn clean package -DskipTests
Start Command: java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Bước 4: Environment Variables
```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
SPRING_DATASOURCE_URL=<từ PostgreSQL service>
SPRING_DATASOURCE_USERNAME=<từ PostgreSQL>
SPRING_DATASOURCE_PASSWORD=<từ PostgreSQL>
JWT_SECRET=MTAwUGVyY2VudFNlY3JldEtleUZvckZvb2RHb0JhY2tlbmRNYXlCRTEyMzQ1Njc4OQ==
JWT_EXPIRATION=86400000
```

### Bước 5: Thêm PostgreSQL (nếu cần)
1. Click "New +" > "PostgreSQL"
2. Render tự tạo và inject connection string

### Bước 6: Deploy
- Click "Create Web Service"
- Render tự động build và deploy
- Lấy URL từ dashboard

**Thời gian:** ~10 phút

---

## ☁️ Option 2: Google Cloud Run (Tích hợp tốt với Firebase)

### Bước 1: Setup Google Cloud
1. Vào https://console.cloud.google.com
2. Tạo project mới hoặc dùng project hiện có
3. Enable Cloud Run API

### Bước 2: Build và Deploy
```bash
# Install Google Cloud SDK (nếu chưa có)
# https://cloud.google.com/sdk/docs/install

# Login
gcloud auth login

# Set project
gcloud config set project YOUR_PROJECT_ID

# Build Docker image
cd backend
mvn clean package -DskipTests
docker build -t gcr.io/YOUR_PROJECT_ID/foodgo-backend .

# Push image
gcloud auth configure-docker
docker push gcr.io/YOUR_PROJECT_ID/foodgo-backend

# Deploy
gcloud run deploy foodgo-backend \
  --image gcr.io/YOUR_PROJECT_ID/foodgo-backend \
  --platform managed \
  --region asia-southeast1 \
  --allow-unauthenticated \
  --port 8080 \
  --memory 512Mi \
  --set-env-vars SPRING_PROFILES_ACTIVE=prod
```

**Thời gian:** ~15 phút

---

## 🚀 Option 3: Fly.io (Free tier tốt)

### Bước 1: Install Fly CLI
```bash
# Windows (PowerShell)
iwr https://fly.io/install.ps1 -useb | iex

# Mac/Linux
curl -L https://fly.io/install.sh | sh
```

### Bước 2: Login
```bash
fly auth login
```

### Bước 3: Tạo app
```bash
cd backend
fly launch
```

Chọn:
- App name: foodgo-backend
- Region: sin (Singapore)
- PostgreSQL: Yes (nếu cần)

### Bước 4: Deploy
```bash
fly deploy
```

**Thời gian:** ~10 phút

---

## 🌐 Option 4: DigitalOcean App Platform

### Bước 1: Tạo account
1. Vào https://www.digitalocean.com
2. Đăng ký (có $200 credit free)

### Bước 2: Tạo App
1. App Platform > Create App
2. Connect GitHub repo
3. Chọn "Docker" hoặc "Java"

### Bước 3: Cấu hình
- Root Directory: `backend`
- Build Command: `mvn clean package -DskipTests`
- Run Command: `java -jar target/backend-0.0.1-SNAPSHOT.jar`

**Thời gian:** ~15 phút

---

## 📊 So sánh nhanh

| Platform | Free Tier | Dễ Setup | Tốc độ | Tích hợp Firebase |
|----------|-----------|----------|--------|-------------------|
| **Render** | ✅ Tốt | ⭐⭐⭐ | ⭐⭐ | ⭐⭐ |
| **Cloud Run** | ✅ Tốt | ⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐ |
| **Fly.io** | ✅ Tốt | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| **DigitalOcean** | ✅ $200 credit | ⭐⭐ | ⭐⭐ | ⭐⭐ |

---

## 🎯 Khuyến nghị

**Cho nhanh và dễ:** **Render** ⭐
- Setup trong 10 phút
- Free tier tốt
- Auto-deploy từ GitHub

**Cho production:** **Google Cloud Run** ⭐⭐
- Tích hợp tốt với Firebase
- Serverless, scale tự động
- Free tier: 2 triệu requests/tháng

---

## 📝 Sau khi deploy

1. **Lấy Backend URL**
2. **Cập nhật Frontend:**
   ```env
   VITE_API_BASE_URL=https://your-backend-url/api/v1
   ```
3. **Rebuild và deploy frontend:**
   ```bash
   cd frontend
   npm run build
   cd ..
   firebase deploy --only hosting
   ```

