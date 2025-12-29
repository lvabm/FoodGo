# 🔧 Fix Railway Deployment Failed

## ❌ Lỗi: "Deployment failed during the build process"

Từ hình ảnh, deployment đã fail. Đây là cách fix:

---

## ✅ Giải pháp nhanh

### Bước 1: Cấu hình Root Directory trong Railway

1. Vào Railway Dashboard
2. Click vào service **"foodgo-app"**
3. Vào **Settings** (gear icon)
4. Tìm **"Root Directory"**
5. Set: `backend`
6. Click **Save**

### Bước 2: Kiểm tra Build Command

Trong Railway Settings:
- **Build Command:** `mvn clean package -DskipTests`
- **Start Command:** `java -jar target/backend-0.0.1-SNAPSHOT.jar`

### Bước 3: Set Environment Variables

Thêm các biến môi trường:
```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
```

### Bước 4: Redeploy

Click **"Redeploy"** hoặc push code mới lên GitHub.

---

## 🔍 Xem Build Logs để biết lỗi cụ thể

1. Vào Railway Dashboard
2. Click service **"foodgo-app"**
3. Click tab **"Build Logs"**
4. Scroll xuống xem lỗi cụ thể

**Các lỗi thường gặp:**

### ❌ "Cannot find pom.xml"
**Fix:** Set Root Directory = `backend`

### ❌ "Maven not found" hoặc "Java not found"
**Fix:** Railway tự detect, nhưng có thể cần thêm thời gian. Đảm bảo Root Directory = `backend`

### ❌ "Port already in use"
**Fix:** Set environment variable `PORT=8080`

### ❌ "JAR file not found"
**Fix:** 
- Kiểm tra Build Command: `mvn clean package -DskipTests`
- Đảm bảo Root Directory = `backend`

### ❌ "Build timeout"
**Fix:** 
- Maven build có thể lâu, đợi thêm
- Hoặc optimize build (skip tests)

---

## 📁 Files đã tạo để hỗ trợ

Tôi đã tạo các files:
1. `railway.json` - Railway config
2. `nixpacks.toml` - Build config
3. `backend/railway.json` - Backend-specific config
4. `Procfile` - Start command

---

## ⚙️ Cấu hình đúng trong Railway UI

### Service Settings:

**Root Directory:**
```
backend
```

**Build Command:**
```
mvn clean package -DskipTests
```

**Start Command:**
```
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
SPRING_DATASOURCE_URL=<từ PostgreSQL service nếu có>
SPRING_DATASOURCE_USERNAME=<từ PostgreSQL service>
SPRING_DATASOURCE_PASSWORD=<từ PostgreSQL service>
JWT_SECRET=MTAwUGVyY2VudFNlY3JldEtleUZvckZvb2RHb0JhY2tlbmRNYXlCRTEyMzQ1Njc4OQ==
JWT_EXPIRATION=86400000
```

---

## 🚀 Các bước fix chi tiết

### 1. Xem Build Logs
- Vào Railway > Service > **Build Logs** tab
- Copy toàn bộ log (đặc biệt phần lỗi ở cuối)

### 2. Fix theo lỗi

**Nếu lỗi về pom.xml:**
```
Root Directory = backend
```

**Nếu lỗi về Maven/Java:**
- Railway tự detect Java 17 và Maven
- Đảm bảo Root Directory = `backend`
- Có thể cần đợi thêm thời gian build

**Nếu lỗi về port:**
```
Environment Variable: PORT=8080
```

**Nếu lỗi về JAR:**
- Kiểm tra Build Command
- Đảm bảo build thành công trước khi start

### 3. Redeploy
- Click **"Redeploy"** button
- Hoặc push commit mới lên GitHub

---

## 📝 Checklist

- [ ] Đã set **Root Directory** = `backend` trong Railway Settings
- [ ] Đã set **Build Command** = `mvn clean package -DskipTests`
- [ ] Đã set **Start Command** = `java -jar target/backend-0.0.1-SNAPSHOT.jar`
- [ ] Đã set **PORT=8080** environment variable
- [ ] Đã xem **Build Logs** để biết lỗi cụ thể
- [ ] Đã commit và push các file config mới (`railway.json`, `nixpacks.toml`)

---

## 🆘 Nếu vẫn lỗi

**Gửi cho tôi:**
1. **Build Logs** (copy toàn bộ, đặc biệt phần lỗi)
2. **Cấu hình hiện tại** (Root Directory, Build Command, Start Command)
3. **Environment Variables** (ẩn sensitive data)

Tôi sẽ giúp fix cụ thể!

---

## 💡 Tip

Railway thường tự detect Spring Boot, nhưng đôi khi cần:
- Set Root Directory rõ ràng
- Đảm bảo `pom.xml` ở đúng vị trí
- Build command phải chạy được local trước

**Test local trước:**
```bash
cd backend
mvn clean package -DskipTests
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

Nếu chạy được local → Railway sẽ chạy được!
