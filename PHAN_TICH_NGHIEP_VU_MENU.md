# PHÂN TÍCH NGHIỆP VỤ MENU VÀ WORKFLOW - HỆ THỐNG FOODGO

## 📋 MỤC LỤC
1. [Tổng quan hệ thống Menu](#1-tổng-quan-hệ-thống-menu)
2. [Cấu trúc dữ liệu Menu](#2-cấu-trúc-dữ-liệu-menu)
3. [Workflow nghiệp vụ Menu](#3-workflow-nghiệp-vụ-menu)
4. [Phân quyền và vai trò](#4-phân-quyền-và-vai-trò)
5. [Các tính năng chính](#5-các-tính-năng-chính)
6. [Luồng xử lý chi tiết](#6-luồng-xử-lý-chi-tiết)

---

## 1. TỔNG QUAN HỆ THỐNG MENU

### 1.1. Khái niệm
Hệ thống Menu của FoodGo được thiết kế theo mô hình **2 tầng**:
- **Menu Item (Món ăn gốc)**: Do Admin quản lý, là danh mục món ăn chung cho toàn hệ thống
- **Outlet Menu Item (Món ăn tại cửa hàng)**: Do Owner quản lý, là phiên bản tùy chỉnh của Menu Item cho từng cửa hàng cụ thể

### 1.2. Mục đích
- **Chuẩn hóa dữ liệu**: Menu Item gốc đảm bảo tính nhất quán về tên và phân loại
- **Linh hoạt**: Mỗi cửa hàng có thể tùy chỉnh giá, mô tả, hình ảnh riêng
- **Quản lý tập trung**: Admin quản lý danh mục chung, Owner quản lý menu của cửa hàng mình

---

## 2. CẤU TRÚC DỮ LIỆU MENU

### 2.1. Cấu trúc phân cấp

```
MenuItemType (Loại món ăn)
    └── MenuItemCategory (Danh mục món ăn)
            └── MenuItemSubCategory (Danh mục con)
                    └── MenuItem (Món ăn gốc)
                            └── OutletMenuItem (Món ăn tại cửa hàng)
```

### 2.2. Các Entity chính

#### 2.2.1. MenuItemType (Loại món ăn)
- **Mục đích**: Phân loại cấp cao nhất (VD: Đồ ăn, Đồ uống)
- **Thuộc tính**:
  - `id`: Integer (Primary Key)
  - `name`: String (50) - Tên loại (unique)
  - `description`: String (255) - Mô tả
- **Quan hệ**: One-to-Many với MenuItemCategory

#### 2.2.2. MenuItemCategory (Danh mục món ăn)
- **Mục đích**: Phân loại cấp 2 (VD: Món chính, Món phụ, Tráng miệng)
- **Thuộc tính**:
  - `id`: Integer (Primary Key)
  - `name`: String (50) - Tên danh mục (unique)
  - `description`: String (255)
  - `type`: MenuItemType (FK)
- **Quan hệ**: 
  - Many-to-One với MenuItemType
  - One-to-Many với MenuItemSubCategory

#### 2.2.3. MenuItemSubCategory (Danh mục con)
- **Mục đích**: Phân loại chi tiết (VD: Phở, Bún, Cơm)
- **Thuộc tính**:
  - `id`: Integer (Primary Key)
  - `name`: String (50) - Tên danh mục con (unique)
  - `description`: String (255)
  - `category`: MenuItemCategory (FK)
- **Quan hệ**:
  - Many-to-One với MenuItemCategory
  - One-to-Many với MenuItem

#### 2.2.4. MenuItem (Món ăn gốc) ⭐
- **Mục đích**: Món ăn chuẩn do Admin quản lý
- **Thuộc tính**:
  - `id`: UUID (Primary Key)
  - `name`: String (255) - Tên món ăn
  - `description`: TEXT - Mô tả
  - `isPopular`: Boolean - Đánh dấu món phổ biến (default: false)
  - `subCategory`: MenuItemSubCategory (FK) - Bắt buộc
  - `province`: Province (FK) - Bắt buộc (theo địa phương)
- **Quan hệ**:
  - Many-to-One với MenuItemSubCategory
  - Many-to-One với Province
  - One-to-Many với OutletMenuItem

#### 2.2.5. OutletMenuItem (Món ăn tại cửa hàng) ⭐⭐
- **Mục đích**: Phiên bản tùy chỉnh của MenuItem cho từng cửa hàng
- **Thuộc tính**:
  - `id`: Integer (Primary Key)
  - `name`: String (255) - Tên món tại cửa hàng (có thể khác MenuItem)
  - `description`: TEXT - Mô tả riêng
  - `price`: BigDecimal (10,2) - Giá bán (bắt buộc)
  - `imageUrl`: String (255) - URL hình ảnh
  - `isAvailable`: Boolean - Trạng thái có sẵn (default: true)
  - `outlet`: Outlet (FK) - Bắt buộc
  - `menuItem`: MenuItem (FK) - Bắt buộc (liên kết với món gốc)
- **Ràng buộc**: Unique constraint trên (outlet_id, menu_item_id)
- **Quan hệ**:
  - Many-to-One với Outlet
  - Many-to-One với MenuItem

#### 2.2.6. MenuItemFeature (Tính năng món ăn)
- **Mục đích**: Định nghĩa các tính năng đặc biệt (VD: Nóng, Lạnh, Không cay)
- **Thuộc tính**:
  - `id`: Integer (Primary Key)
  - `name`: String (100) - Tên tính năng (unique)
  - `featureType`: String (20) - Loại tính năng
  - `valueType`: String (20) - Kiểu giá trị
  - `possibleValues`: TEXT - Các giá trị có thể
  - `description`: String (255)

---

## 3. WORKFLOW NGHIỆP VỤ MENU

### 3.1. Workflow tổng quan

```
┌─────────────────────────────────────────────────────────────┐
│                    WORKFLOW MENU                            │
└─────────────────────────────────────────────────────────────┘

1. ADMIN SETUP (Một lần)
   ├── Tạo MenuItemType
   ├── Tạo MenuItemCategory
   ├── Tạo MenuItemSubCategory
   └── Tạo MenuItem (Món ăn gốc)

2. OWNER QUẢN LÝ MENU CỬA HÀNG
   ├── Chọn Outlet
   ├── Tìm kiếm MenuItem gốc
   ├── Tạo OutletMenuItem (tùy chỉnh giá, mô tả, hình ảnh)
   ├── Cập nhật OutletMenuItem
   ├── Bật/Tắt trạng thái Available
   └── Xóa OutletMenuItem

3. USER XEM MENU
   ├── Xem danh sách OutletMenuItem của cửa hàng
   ├── Lọc theo Available
   └── Xem chi tiết món ăn
```

### 3.2. Workflow chi tiết theo vai trò

#### 3.2.1. ADMIN - Quản lý Menu Item gốc

**Bước 1: Tạo MenuItemType** (nếu chưa có)
```
Admin → Menu Management → Tạo Type mới
```

**Bước 2: Tạo MenuItemCategory**
```
Admin → Menu Management → Tạo Category
- Chọn Type
- Nhập tên, mô tả
```

**Bước 3: Tạo MenuItemSubCategory**
```
Admin → Menu Management → Tạo SubCategory
- Chọn Category
- Nhập tên, mô tả
```

**Bước 4: Tạo MenuItem**
```
Admin → Menu Management → Thêm món ăn
- Nhập tên món
- Chọn SubCategory (bắt buộc)
- Chọn Province (bắt buộc)
- Nhập mô tả (tùy chọn)
- Đánh dấu isPopular (tùy chọn)
→ Lưu MenuItem
```

**Validation:**
- SubCategory phải tồn tại
- Province phải tồn tại
- Tên món không được trống

#### 3.2.2. OWNER - Quản lý Menu cửa hàng

**Bước 1: Chọn Outlet**
```
Owner → Menu Management → Chọn Outlet từ dropdown
```

**Bước 2: Tạo OutletMenuItem**
```
Owner → Menu Management → Tạo món
├── Tìm kiếm MenuItem gốc
│   └── Nhập tên → Hiển thị danh sách MenuItem
├── Chọn MenuItem gốc (bắt buộc)
├── Nhập thông tin tùy chỉnh:
│   ├── Tên (có thể khác MenuItem gốc)
│   ├── Giá (bắt buộc)
│   ├── Mô tả (tùy chọn)
│   ├── URL hình ảnh (tùy chọn)
│   └── Danh mục, Loại (tùy chọn)
└── Lưu OutletMenuItem
```

**Validation:**
- Outlet phải thuộc sở hữu của Owner
- MenuItem phải tồn tại
- Không được trùng lặp (outlet_id + menu_item_id)
- Giá phải > 0

**Bước 3: Cập nhật OutletMenuItem**
```
Owner → Menu Management → Sửa món
├── Cập nhật tên, giá, mô tả, hình ảnh
├── Có thể thay đổi MenuItem gốc (nếu cần)
└── Lưu thay đổi
```

**Bước 4: Quản lý trạng thái Available**
```
Owner → Menu Management → Bật/Tắt Available
├── Click toggle → Chuyển đổi isAvailable
└── Món không Available sẽ bị ẩn/mờ khi User xem
```

**Bước 5: Xóa OutletMenuItem**
```
Owner → Menu Management → Xóa món
├── Xác nhận xóa
└── Soft Delete (không xóa vật lý)
```

#### 3.2.3. USER - Xem Menu

**Bước 1: Xem danh sách Menu**
```
User → Outlet Detail → Tab "Thực đơn"
├── Hiển thị danh sách OutletMenuItem
├── Lọc tự động: chỉ hiển thị isAvailable = true
└── Hiển thị: Tên, Giá, Hình ảnh, Mô tả
```

**Bước 2: Xem chi tiết món**
```
User → Click vào món ăn
├── Hiển thị đầy đủ thông tin
├── Giá, mô tả, hình ảnh
└── Thông tin cửa hàng
```

---

## 4. PHÂN QUYỀN VÀ VAI TRÒ

### 4.1. Bảng phân quyền

| Thao tác | Admin | Owner | User |
|----------|-------|-------|------|
| **MenuItem (Món gốc)** |
| Tạo MenuItem | ✅ | ❌ | ❌ |
| Sửa MenuItem | ✅ | ❌ | ❌ |
| Xóa MenuItem | ✅ | ❌ | ❌ |
| Xem MenuItem | ✅ | ✅ | ❌ |
| **OutletMenuItem (Món tại cửa hàng)** |
| Tạo OutletMenuItem | ✅ | ✅ (chỉ Outlet của mình) | ❌ |
| Sửa OutletMenuItem | ✅ | ✅ (chỉ Outlet của mình) | ❌ |
| Xóa OutletMenuItem | ✅ | ✅ (chỉ Outlet của mình) | ❌ |
| Toggle Available | ✅ | ✅ (chỉ Outlet của mình) | ❌ |
| Xem OutletMenuItem | ✅ | ✅ | ✅ |

### 4.2. Kiểm tra quyền trong Backend

#### 4.2.1. MenuItem Service
```java
// Chỉ Admin mới được tạo/sửa/xóa MenuItem
ensurePermission(null); // Kiểm tra Admin
```

#### 4.2.2. OutletMenuItem Service
```java
// Kiểm tra Owner của Outlet
UUID ownerId = SecurityContext.getCurrentUserId();
Outlet outlet = outletRepository.findById(request.outletId());

if (!outlet.getOwner().getId().equals(ownerId)) {
    throw new AccessDeniedException("Bạn không có quyền...");
}
```

---

## 5. CÁC TÍNH NĂNG CHÍNH

### 5.1. Tính năng Admin

#### 5.1.1. Quản lý MenuItem gốc
- ✅ Tạo/sửa/xóa MenuItem
- ✅ Đánh dấu món phổ biến (isPopular)
- ✅ Phân loại theo SubCategory và Province
- ✅ Tìm kiếm và lọc MenuItem
- ✅ Phân trang danh sách

#### 5.1.2. Quản lý cấu trúc phân loại
- ✅ Quản lý MenuItemType
- ✅ Quản lý MenuItemCategory
- ✅ Quản lý MenuItemSubCategory

### 5.2. Tính năng Owner

#### 5.2.1. Quản lý Menu cửa hàng
- ✅ Chọn Outlet để quản lý
- ✅ Tìm kiếm MenuItem gốc từ danh sách chung
- ✅ Tạo OutletMenuItem với thông tin tùy chỉnh
- ✅ Cập nhật giá, mô tả, hình ảnh
- ✅ Bật/Tắt trạng thái Available (toggle)
- ✅ Xóa món khỏi menu cửa hàng
- ✅ Tìm kiếm món trong menu cửa hàng

### 5.3. Tính năng User

#### 5.3.1. Xem Menu
- ✅ Xem danh sách món ăn của cửa hàng
- ✅ Tự động lọc chỉ hiển thị món Available
- ✅ Xem chi tiết món ăn (giá, mô tả, hình ảnh)
- ✅ Hiển thị món không Available với trạng thái mờ

---

## 6. LUỒNG XỬ LÝ CHI TIẾT

### 6.1. Luồng tạo OutletMenuItem

```
┌─────────────┐
│   Owner     │
│  Click      │
│ "Tạo món"   │
└──────┬──────┘
       │
       ▼
┌─────────────────────────┐
│ Frontend:                │
│ - Load danh sách Outlet  │
│ - Hiển thị Modal        │
│ - Load Master MenuItems  │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Owner nhập thông tin:   │
│ - Chọn MenuItem gốc     │
│ - Nhập tên, giá, mô tả  │
│ - Upload hình ảnh       │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Frontend: Validate      │
│ - MenuItemId bắt buộc    │
│ - Giá > 0               │
│ - OutletId phải có      │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ API: POST               │
│ /outlets/{outletId}/    │
│ menu-items              │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Backend:                │
│ 1. Kiểm tra quyền Owner │
│ 2. Validate Outlet      │
│ 3. Validate MenuItem    │
│ 4. Kiểm tra trùng lặp   │
│ 5. Tạo OutletMenuItem  │
│ 6. Set isAvailable=true │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Response:               │
│ OutletMenuItemResponse  │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Frontend:               │
│ - Hiển thị thông báo    │
│ - Reload danh sách      │
│ - Đóng Modal            │
└─────────────────────────┘
```

### 6.2. Luồng xem Menu (User)

```
┌─────────────┐
│   User      │
│ Xem Outlet  │
│ Detail      │
└──────┬──────┘
       │
       ▼
┌─────────────────────────┐
│ Frontend:               │
│ - Click Tab "Thực đơn"  │
│ - Set isLoadingMenu=true│
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ API: GET                │
│ /outlets/{outletId}/    │
│ menu-items?             │
│ isAvailable=true        │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Backend:                │
│ 1. Lọc theo outletId    │
│ 2. Lọc isAvailable=true │
│ 3. Phân trang           │
│ 4. Trả về danh sách     │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Frontend:               │
│ - Hiển thị danh sách    │
│ - Grid layout           │
│ - Hiển thị hình ảnh,    │
│   tên, giá, mô tả       │
│ - Mờ món không Available│
└─────────────────────────┘
```

### 6.3. Luồng Toggle Available

```
┌─────────────┐
│   Owner     │
│ Click Toggle│
│ Available   │
└──────┬──────┘
       │
       ▼
┌─────────────────────────┐
│ API: PATCH              │
│ /outlets/{outletId}/    │
│ menu-items/{itemId}/    │
│ availability            │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Backend:                │
│ 1. Tìm OutletMenuItem   │
│ 2. Kiểm tra quyền Owner │
│ 3. Đảo ngược isAvailable│
│    (true ↔ false)       │
│ 4. Lưu thay đổi         │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Response:               │
│ OutletMenuItemResponse  │
│ (với isAvailable mới)   │
└──────┬──────────────────┘
       │
       ▼
┌─────────────────────────┐
│ Frontend:               │
│ - Cập nhật UI           │
│ - Reload danh sách      │
└─────────────────────────┘
```

---

## 7. API ENDPOINTS

### 7.1. MenuItem (Admin)

| Method | Endpoint | Mô tả | Quyền |
|--------|----------|-------|-------|
| GET | `/api/v1/menu-items` | Danh sách MenuItem | Admin, Owner |
| GET | `/api/v1/menu-items/{id}` | Chi tiết MenuItem | Admin, Owner |
| POST | `/api/v1/menu-items` | Tạo MenuItem | Admin |
| PATCH | `/api/v1/menu-items/{id}` | Sửa MenuItem | Admin |
| DELETE | `/api/v1/menu-items/{id}` | Xóa MenuItem | Admin |

### 7.2. OutletMenuItem (Owner/Admin)

| Method | Endpoint | Mô tả | Quyền |
|--------|----------|-------|-------|
| GET | `/api/v1/outlets/{outletId}/menu-items` | Danh sách món của Outlet | Tất cả |
| GET | `/api/v1/outlets/{outletId}/menu-items/{itemId}` | Chi tiết món | Tất cả |
| GET | `/api/v1/outlets/{outletId}/menu-items/search` | Tìm kiếm món | Tất cả |
| POST | `/api/v1/outlets/{outletId}/menu-items` | Tạo món cho Outlet | Owner, Admin |
| PATCH | `/api/v1/outlets/{outletId}/menu-items/{itemId}` | Sửa món | Owner, Admin |
| PATCH | `/api/v1/outlets/{outletId}/menu-items/{itemId}/availability` | Toggle Available | Owner, Admin |
| DELETE | `/api/v1/outlets/{outletId}/menu-items/{itemId}` | Xóa món | Owner, Admin |

---

## 8. ĐIỂM NỔI BẬT CỦA HỆ THỐNG

### 8.1. Kiến trúc 2 tầng
- **MenuItem gốc**: Chuẩn hóa dữ liệu, quản lý tập trung
- **OutletMenuItem**: Linh hoạt, tùy chỉnh theo cửa hàng

### 8.2. Phân quyền rõ ràng
- Admin: Quản lý toàn hệ thống
- Owner: Chỉ quản lý menu cửa hàng của mình
- User: Chỉ xem menu

### 8.3. Validation chặt chẽ
- Kiểm tra quyền sở hữu Outlet
- Kiểm tra trùng lặp (outlet + menuItem)
- Validate dữ liệu đầu vào

### 8.4. Trạng thái Available
- Owner có thể nhanh chóng bật/tắt món
- User chỉ thấy món Available
- Món không Available vẫn tồn tại trong DB (soft delete)

### 8.5. Tìm kiếm và lọc
- Tìm kiếm MenuItem gốc khi Owner tạo món
- Lọc món theo Outlet, Available status
- Phân trang hiệu quả

---

## 9. KẾT LUẬN

Hệ thống Menu của FoodGo được thiết kế với:
- ✅ **Kiến trúc rõ ràng**: 2 tầng MenuItem và OutletMenuItem
- ✅ **Phân quyền chặt chẽ**: Admin, Owner, User có quyền hạn riêng
- ✅ **Workflow logic**: Từ Admin tạo món gốc → Owner tùy chỉnh → User xem
- ✅ **Tính năng đầy đủ**: CRUD, Toggle Available, Tìm kiếm, Lọc
- ✅ **Validation tốt**: Đảm bảo tính toàn vẹn dữ liệu
- ✅ **UX tốt**: Giao diện thân thiện, dễ sử dụng

---

**Tài liệu này phục vụ cho mục đích thuyết trình và phân tích nghiệp vụ hệ thống Menu FoodGo.**

