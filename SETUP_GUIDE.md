# FoodGo - Hướng dẫn chạy dự án

## Yêu cầu hệ thống

- **Backend**: Java 17+, Maven 3.8+, PostgreSQL 17.0
- **Frontend**: Node.js 18+, npm 9+

## Cài đặt và chạy Backend

### 1. Cấu hình PostgreSQL

```sql
-- Tạo database
CREATE DATABASE foodgo;

-- Tạo user (nếu chưa có)
CREATE USER foodgo_user WITH PASSWORD 'your_password';

-- Cấp quyền
GRANT ALL PRIVILEGES ON DATABASE foodgo TO foodgo_user;
```

### 2. Cấu hình application.properties

File: `backend/src/main/resources/application.properties`

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/foodgo
spring.datasource.username=foodgo_user
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=your_secret_key_here_minimum_32_characters
jwt.expiration=86400000

# Server
server.port=8080
```

### 3. Chạy Backend

```bash
cd backend

# Build project
./mvnw clean install

# Run application
./mvnw spring-boot:run
```

Backend sẽ chạy tại: `http://localhost:8080`

## Cài đặt và chạy Frontend

### 1. Cài đặt dependencies

```bash
cd frontend
npm install
```

### 2. Cấu hình environment

File `.env.development` đã được tạo sẵn:

```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

### 3. Chạy Frontend

```bash
npm run dev
```

Frontend sẽ chạy tại: `http://localhost:3000`

## Kiểm tra kết nối

### 1. Test Backend API

```bash
# Health check
curl http://localhost:8080/api/v1/health

# Test register
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "phone": "0123456789",
    "password": "password123"
  }'

# Test login
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "emailOrPhone": "test@example.com",
    "password": "password123"
  }'
```

### 2. Test Frontend

1. Mở browser: `http://localhost:3000`
2. Click "Đăng ký" để tạo tài khoản
3. Sau khi đăng ký thành công, đăng nhập
4. Kiểm tra console browser để xem API calls

## Cấu trúc API

### Authentication

- `POST /api/v1/auth/register` - Đăng ký
- `POST /api/v1/auth/login` - Đăng nhập
- `POST /api/v1/auth/logout` - Đăng xuất
- `POST /api/v1/auth/refresh-token` - Refresh token
- `POST /api/v1/auth/forgot-password` - Quên mật khẩu
- `POST /api/v1/auth/reset-password` - Reset mật khẩu

### Users

- `GET /api/v1/users/my-profile` - Thông tin cá nhân
- `PUT /api/v1/users/my-profile` - Cập nhật profile
- `GET /api/v1/users` - Danh sách users (Admin)
- `GET /api/v1/users/{id}` - Chi tiết user
- `POST /api/v1/users` - Tạo user (Admin)
- `PUT /api/v1/users/{id}` - Cập nhật user (Admin)
- `DELETE /api/v1/users/{id}` - Xóa user (Admin)

### Outlets

- `GET /api/v1/outlets` - Danh sách outlets
- `GET /api/v1/outlets/{id}` - Chi tiết outlet
- `GET /api/v1/outlets/search` - Tìm kiếm
- `GET /api/v1/outlets/nearby` - Outlets gần đây
- `POST /api/v1/outlets` - Tạo outlet (Owner)
- `PUT /api/v1/outlets/{id}` - Cập nhật outlet
- `DELETE /api/v1/outlets/{id}` - Xóa outlet

### Bookings

- `GET /api/v1/bookings/my-bookings` - Lịch sử đặt bàn
- `GET /api/v1/bookings/{id}` - Chi tiết đặt bàn
- `POST /api/v1/bookings` - Tạo đặt bàn
- `PUT /api/v1/bookings/{id}` - Cập nhật
- `DELETE /api/v1/bookings/{id}` - Hủy đặt bàn
- `POST /api/v1/bookings/{id}/confirm` - Xác nhận (Owner)
- `POST /api/v1/bookings/{id}/reject` - Từ chối (Owner)

### Reviews

- `GET /api/v1/reviews/outlet/{outletId}` - Reviews của outlet
- `POST /api/v1/reviews` - Tạo review
- `PUT /api/v1/reviews/{id}` - Cập nhật review
- `DELETE /api/v1/reviews/{id}` - Xóa review
- `POST /api/v1/reviews/{id}/reply` - Reply review (Owner)

### Menu

- `GET /api/v1/menu/outlet/{outletId}` - Menu của outlet
- `GET /api/v1/menu/{id}` - Chi tiết món
- `POST /api/v1/menu` - Tạo món (Owner)
- `PUT /api/v1/menu/{id}` - Cập nhật món
- `DELETE /api/v1/menu/{id}` - Xóa món

### Locations

- `GET /api/v1/locations/countries` - Danh sách quốc gia
- `GET /api/v1/locations/provinces` - Danh sách tỉnh/thành
- `GET /api/v1/locations/districts` - Danh sách quận/huyện

## Tính năng đã tích hợp

### User Side

✅ Đăng ký tài khoản với validation
✅ Đăng nhập với JWT authentication
✅ Tự động lưu token vào localStorage
✅ Auto-login khi refresh page
✅ Trang chủ hiển thị outlets từ API
✅ Tìm kiếm outlets
✅ Click vào outlet để xem chi tiết

### Admin Side

⏳ Dashboard với thống kê thực tế
⏳ Quản lý users
⏳ Quản lý outlets
⏳ Quản lý categories, locations

### Owner Side

⏳ Dashboard outlet
⏳ Quản lý bookings (xác nhận/từ chối)
⏳ Quản lý menu
⏳ Quản lý reviews

## Troubleshooting

### Lỗi CORS

Nếu gặp lỗi CORS, thêm config vào backend:

```java
// backend/src/main/java/com/foodgo/config/WebConfig.java
@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
```

### Token expired

- Axios interceptor tự động xử lý 401 error
- User sẽ được redirect về trang login
- Cần implement refresh token nếu muốn tự động refresh

### Database connection error

```bash
# Kiểm tra PostgreSQL đang chạy
psql -U postgres -c "SELECT version();"

# Kiểm tra database đã tạo chưa
psql -U postgres -c "\l" | grep foodgo

# Test connection
psql -U foodgo_user -d foodgo
```

### Backend không start

```bash
# Kiểm tra port 8080 có bị chiếm không
netstat -ano | findstr :8080

# Kill process đang chiếm port (Windows)
taskkill /PID <PID> /F

# Xem logs
./mvnw spring-boot:run -X
```

### Frontend không load data

1. Mở DevTools → Network tab
2. Kiểm tra API calls có gửi đến backend không
3. Kiểm tra response status và data
4. Xem Console log để debug

## Các bước tiếp theo

1. ✅ Tích hợp Login & Register
2. ✅ Tích hợp Home page với outlets
3. ⏳ Tích hợp Outlet Detail page
4. ⏳ Tích hợp Booking flow
5. ⏳ Tích hợp Admin Dashboard
6. ⏳ Tích hợp Owner Dashboard
7. ⏳ Add loading states & error handling
8. ⏳ Add form validation
9. ⏳ Add pagination
10. ⏳ Add image upload

## Scripts hữu ích

### Backend

```bash
# Build without tests
./mvnw clean install -DskipTests

# Run specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Generate JAR
./mvnw package
```

### Frontend

```bash
# Development
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint
npm run lint
```

## Docker (Optional)

```bash
# Build và chạy bằng docker-compose
docker-compose up -d

# Xem logs
docker-compose logs -f

# Stop services
docker-compose down
```

## Support

Nếu gặp vấn đề:

1. Kiểm tra logs backend và frontend
2. Kiểm tra Network tab trong DevTools
3. Đảm bảo database đã setup đúng
4. Đảm bảo CORS được config
5. Kiểm tra `.env` file
