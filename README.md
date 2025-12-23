# FoodGo

> 🍜 Nền tảng khám phá và đặt bàn nhà hàng tại TP.HCM

FoodGo là một ứng dụng web giúp người dùng tìm kiếm, khám phá và đặt bàn tại các nhà hàng, quán ăn ở TP. Hồ Chí Minh. Ứng dụng được xây dựng bằng **Spring Boot** (Backend) và **Vue.js 3** (Frontend).

## ✨ Tính năng chính

### 👤 Người dùng (User)

- 🔐 Đăng ký/Đăng nhập với JWT authentication
- 🔍 Tìm kiếm nhà hàng theo tên, địa điểm, danh mục
- 📍 Xem nhà hàng gần đây (nearby)
- 📖 Xem menu, giờ hoạt động, đánh giá
- 🎫 Đặt bàn online
- ⭐ Đánh giá và review nhà hàng
- 📜 Xem lịch sử đặt bàn

### 🏪 Chủ nhà hàng (Owner)

- 📊 Dashboard quản lý outlet
- ✅ Xác nhận/Từ chối đặt bàn
- 🍽️ Quản lý menu (thêm/sửa/xóa món ăn)
- 🏢 Cập nhật thông tin outlet
- 📸 Upload ảnh nhà hàng, món ăn
- 💬 Phản hồi reviews của khách hàng
- 📈 Thống kê booking, rating, check-in

### 👨‍💼 Quản trị viên (Admin)

- 👥 Quản lý users (tạo/sửa/xóa/phân quyền)
- 🏪 Quản lý outlets (duyệt outlet mới)
- 🏷️ Quản lý categories, outlet types, features
- 🌍 Quản lý địa điểm (countries, provinces, districts)
- 📊 Thống kê tổng quan hệ thống
- 🚨 Xử lý báo cáo vi phạm

## 🛠️ Tech Stack

### Backend

- **Framework**: Spring Boot 3.5.0
- **Java**: 17
- **Database**: PostgreSQL 17.0
- **Security**: Spring Security + JWT
- **ORM**: Spring Data JPA (Hibernate)
- **Build Tool**: Maven

### Frontend

- **Framework**: Vue.js 3.5.18 (Composition API)
- **Build Tool**: Vite 7.1.2
- **State Management**: Pinia 2.2.8
- **Routing**: Vue Router 4.4.5
- **HTTP Client**: Axios 1.7.9
- **UI**: Tailwind CSS 3.4.17
- **Icons**: Material Symbols

## 📦 Cài đặt

### Yêu cầu hệ thống

- Java 17+
- Maven 3.8+
- PostgreSQL 17.0+
- Node.js 18+
- npm 9+

### Backend Setup

```bash
# 1. Clone repository
git clone <repository-url>
cd FoodGo/backend

# 2. Tạo database PostgreSQL
createdb foodgo

# 3. Cấu hình application.properties
# Sửa file backend/src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/foodgo
spring.datasource.username=your_username
spring.datasource.password=your_password

# 4. Build & Run
./mvnw clean install
./mvnw spring-boot:run
```

Backend chạy tại: `http://localhost:8080`

### Frontend Setup

```bash
# 1. Di chuyển vào thư mục frontend
cd ../frontend

# 2. Cài đặt dependencies
npm install

# 3. Chạy development server
npm run dev
```

Frontend chạy tại: `http://localhost:3000`

📚 **Chi tiết cài đặt**: Xem [SETUP_GUIDE.md](./SETUP_GUIDE.md)

## 📁 Cấu trúc thư mục

```
FoodGo/
├── backend/                    # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/foodgo/
│   │   │   │       ├── controller/     # REST Controllers
│   │   │   │       ├── service/        # Business Logic
│   │   │   │       ├── repository/     # Data Access
│   │   │   │       ├── model/          # Entities
│   │   │   │       ├── dto/            # Data Transfer Objects
│   │   │   │       ├── config/         # Configurations
│   │   │   │       └── security/       # Security & JWT
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
│
├── frontend/                   # Vue.js Frontend
│   ├── src/
│   │   ├── api/                # API services
│   │   │   ├── axios.js        # Axios config
│   │   │   ├── auth.js
│   │   │   ├── outlet.js
│   │   │   ├── booking.js
│   │   │   └── ...
│   │   ├── stores/             # Pinia stores
│   │   │   ├── auth.js
│   │   │   ├── outlet.js
│   │   │   └── ...
│   │   ├── views/              # Page components
│   │   │   ├── user/           # User pages
│   │   │   ├── admin/          # Admin pages
│   │   │   └── owner/          # Owner pages
│   │   ├── layouts/            # Layout components
│   │   ├── components/         # Reusable components
│   │   ├── router/             # Vue Router config
│   │   └── main.js
│   ├── package.json
│   └── vite.config.js
│
├── Admin_SideMap/              # Admin HTML templates
├── Owner_SideMap/              # Owner HTML templates
├── User_SideMap/               # User HTML templates
└── docker-compose.yaml
```

## 🌐 API Endpoints

### Authentication

```
POST   /api/v1/auth/register          # Đăng ký
POST   /api/v1/auth/login             # Đăng nhập
POST   /api/v1/auth/logout            # Đăng xuất
POST   /api/v1/auth/refresh-token     # Refresh token
POST   /api/v1/auth/forgot-password   # Quên mật khẩu
POST   /api/v1/auth/reset-password    # Reset mật khẩu
```

### Outlets

```
GET    /api/v1/outlets                # Danh sách outlets
GET    /api/v1/outlets/{id}           # Chi tiết outlet
GET    /api/v1/outlets/search         # Tìm kiếm
GET    /api/v1/outlets/nearby         # Outlets gần đây
POST   /api/v1/outlets                # Tạo outlet (Owner)
PUT    /api/v1/outlets/{id}           # Cập nhật outlet
DELETE /api/v1/outlets/{id}           # Xóa outlet
```

### Bookings

```
GET    /api/v1/bookings/my-bookings   # Lịch sử đặt bàn
POST   /api/v1/bookings               # Tạo booking
PUT    /api/v1/bookings/{id}          # Cập nhật booking
DELETE /api/v1/bookings/{id}          # Hủy booking
POST   /api/v1/bookings/{id}/confirm  # Xác nhận (Owner)
POST   /api/v1/bookings/{id}/reject   # Từ chối (Owner)
```

📚 **Full API docs**: Xem [frontend/README_INTEGRATION.md](./frontend/README_INTEGRATION.md)

## 🚀 Deployment

### Docker

```bash
# Build và chạy tất cả services
docker-compose up -d

# Xem logs
docker-compose logs -f

# Stop
docker-compose down
```

### Production Build

**Backend:**

```bash
cd backend
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

**Frontend:**

```bash
cd frontend
npm run build
# Deploy thư mục dist/ lên hosting
```

## 📝 Development

### Backend

```bash
# Run with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Run tests
./mvnw test

# Generate coverage report
./mvnw jacoco:report
```

### Frontend

```bash
# Development
npm run dev

# Build
npm run build

# Preview production build
npm run preview

# Lint
npm run lint
```

## 🔐 Authentication Flow

1. User đăng nhập → Nhận `accessToken` và `refreshToken`
2. Token được lưu vào `localStorage`
3. Mọi API request tự động thêm `Authorization: Bearer {token}` header
4. Khi token hết hạn → Tự động logout và redirect về login
5. (Optional) Implement auto-refresh token

## 📸 Screenshots

_(Thêm screenshots của ứng dụng)_

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- Your Name - Initial work

## 🙏 Acknowledgments

- Spring Boot Documentation
- Vue.js Documentation
- Tailwind CSS
- Material Symbols Icons
