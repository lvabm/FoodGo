# GIAI ĐOẠN 1: BUILD JAR FILE
FROM eclipse-temurin:21-jdk-alpine AS build

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy Maven/Gradle project files
# Nếu dùng Maven
COPY pom.xml mvnw .
COPY .mvn .mvn

# Nếu dùng Gradle (thay thế 3 dòng trên bằng các dòng sau)
# COPY build.gradle settings.gradle gradlew .
# COPY gradle gradle

# Copy toàn bộ mã nguồn
COPY src src

# Build dự án và tạo JAR file
# Nếu dùng Maven
RUN ./mvnw clean package -DskipTests

# Nếu dùng Gradle (thay thế dòng trên bằng dòng sau)
# RUN ./gradlew bootJar

# GIAI ĐOẠN 2: CHẠY ỨNG DỤNG
# Sử dụng base image JRE (nhẹ hơn JDK) để chạy ứng dụng
FROM eclipse-temurin:21-jre-alpine

# Thiết lập đối số (arg) để trỏ đến JAR file được tạo ra từ giai đoạn build
ARG JAR_FILE=target/*.jar

# Copy JAR file từ giai đoạn build
COPY --from=build /app/${JAR_FILE} app.jar

# ENTRYPOINT để chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]