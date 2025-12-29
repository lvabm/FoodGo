# 🎨 Deploy Backend lên Render (Dễ nhất)

## ✅ Tại sao Render?

- ✅ **Free tier tốt** - Không cần credit card
- ✅ **Dễ setup** - Chỉ cần connect GitHub
- ✅ **Auto-deploy** - Tự động deploy khi push code
- ✅ **PostgreSQL** - Có thể add database service
- ⚠️ **Sleep** sau 15 phút không dùng (free tier) - Nhưng wake up nhanh

---

## 🚀 Các bước Deploy

### Bước 1: Tạo Render account

1. Vào https://render.com
2. Click **"Get Started for Free"**
3. Đăng nhập bằng **GitHub**
4. Authorize Render access

### Bước 2: Tạo Web Service

1. Click **"New +"** (góc trên bên phải)
2. Chọn **"Web Service"**
3. Connect GitHub repository: **FoodGo**
4. Chọn branch: **main** (hoặc branch bạn muốn)

### Bước 3: Cấu hình Service

**Basic Settings:**
```
Name: foodgo-backend
Region: Singapore (gần Việt Nam nhất)
Branch: main
```

**Build & Deploy:**
```
Root Directory: backend
Environment: Docker (hoặc Java)
Build Command: mvn clean package -DskipTests
Start Command: java -jar target/backend-0.0.1-SNAPSHOT.jar
```

**Nếu chọn Docker:**
- Render sẽ dùng `backend/Dockerfile` (đã có)
- Build Command: (để trống, Docker tự build)
- Start Command: (để trống, Docker tự start)

**Nếu chọn Java:**
- Build Command: `mvn clean package -DskipTests`
- Start Command: `java -jar target/backend-0.0.1-SNAPSHOT.jar`

### Bước 4: Environment Variables

Click **"Advanced"** > **"Add Environment Variable"**, thêm:

```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
```

**Lưu ý:** PORT sẽ được Render tự động set, nhưng có thể set thủ công.

### Bước 5: Thêm PostgreSQL (nếu cần database)

1. Click **"New +"** > **"PostgreSQL"**
2. Đặt tên: `foodgo-db`
3. Region: Singapore
4. Plan: Free (hoặc Starter nếu cần)
5. Click **"Create Database"**
6. Render tự tạo và inject connection string vào Web Service

**Connection string sẽ tự động có:**
```
SPRING_DATASOURCE_URL=jdbc:postgresql://...
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
```

### Bước 6: Thêm Environment Variables khác

Trong Web Service Settings > Environment:
```
JWT_SECRET=MTAwUGVyY2VudFNlY3JldEtleUZvckZvb2RHb0JhY2tlbmRNYXlCRTEyMzQ1Njc4OQ==
JWT_EXPIRATION=86400000
```

### Bước 7: Deploy

1. Click **"Create Web Service"**
2. Render tự động:
   - Clone code từ GitHub
   - Build project
   - Deploy
3. Đợi build xong (5-10 phút)
4. Lấy URL từ dashboard (ví dụ: `https://foodgo-backend.onrender.com`)

---

## 🔧 Cấu hình nâng cao

### Auto-Deploy
- Mặc định: Auto-deploy khi push code
- Có thể tắt trong Settings

### Health Check
Render tự động check health endpoint:
- `/actuator/health` (nếu có)
- Hoặc root `/`

### Custom Domain
1. Vào Settings > Custom Domains
2. Add domain của bạn
3. Update DNS records

---

## 📝 Sau khi deploy

### 1. Lấy Backend URL
Từ Render dashboard, copy URL (ví dụ: `https://foodgo-backend.onrender.com`)

### 2. Test API
```bash
curl https://foodgo-backend.onrender.com/api/v1/statistics/public
```

### 3. Cập nhật Frontend

Tạo file `frontend/.env.production`:
```env
VITE_API_BASE_URL=https://foodgo-backend.onrender.com/api/v1
```

### 4. Rebuild và Deploy Frontend
```bash
cd frontend
npm run build
cd ..
firebase deploy --only hosting
```

---

## ⚠️ Lưu ý

### Free Tier Limitations:
- **Sleep** sau 15 phút không có traffic
- **Wake up** mất ~30 giây khi có request
- **512MB RAM**
- **100GB bandwidth/tháng**

### Nếu cần Always On:
- Upgrade lên Starter plan ($7/tháng)
- Service sẽ không sleep

---

## 🔍 Troubleshooting

### Lỗi "Build failed"
- Kiểm tra Build Logs trong Render dashboard
- Đảm bảo Root Directory = `backend`
- Kiểm tra Build Command đúng

### Lỗi "Port already in use"
- Set PORT=8080 trong environment variables
- Hoặc để Render tự động set

### Lỗi "Cannot connect to database"
- Kiểm tra PostgreSQL service đã tạo chưa
- Kiểm tra connection string trong environment variables
- Đảm bảo database service và web service cùng region

### Service bị sleep
- Normal với free tier
- Request đầu tiên sẽ mất ~30 giây để wake up
- Upgrade lên paid plan để tránh sleep

---

## ✅ Checklist

- [ ] Đã tạo Render account
- [ ] Đã tạo Web Service
- [ ] Đã set Root Directory = `backend`
- [ ] Đã set Build Command và Start Command
- [ ] Đã thêm environment variables
- [ ] Đã tạo PostgreSQL service (nếu cần)
- [ ] Đã deploy thành công
- [ ] Đã test API endpoint
- [ ] Đã cập nhật frontend API URL
- [ ] Đã rebuild và redeploy frontend

---

## 🎉 Kết quả

Sau khi deploy thành công:
- ✅ Backend URL: `https://foodgo-backend.onrender.com`
- ✅ Auto-deploy khi push code
- ✅ Free tier (có sleep)
- ✅ Có thể upgrade khi cần

**Render là lựa chọn tốt nhất nếu Railway không được!** 🚀

