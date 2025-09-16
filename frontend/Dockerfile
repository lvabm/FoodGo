# GIAI ĐOẠN 1: BUILD (Sử dụng Node.js để biên dịch code Vue)
FROM node:lts-alpine AS builder

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Copy package.json và package-lock.json để cài đặt dependencies
COPY package*.json ./

# Cài đặt dependencies (chỉ cần chạy một lần)
RUN npm install

# Copy toàn bộ code source
COPY . .

# Biên dịch ứng dụng Vue.js (Giả định lệnh build là 'npm run build' và output vào thư mục 'dist')
RUN npm run build

# GIAI ĐOẠN 2: RUN (Sử dụng Nginx để phục vụ file tĩnh và Proxy API)
FROM nginx:alpine

# Xóa cấu hình Nginx mặc định
RUN rm /etc/nginx/conf.d/default.conf

# Copy cấu hình Nginx tùy chỉnh (Tạo ở Bước 2)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy các file tĩnh đã build từ Giai đoạn 1 vào thư mục phục vụ của Nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# Mở cổng 80 (cổng Nginx)
EXPOSE 80

# Chạy Nginx
CMD ["nginx", "-g", "daemon off;"]