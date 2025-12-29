package com.foodgo.backend.security.config;

import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
// @Profile("dev")
@RequiredArgsConstructor
public class PasswordUpdateRunner implements ApplicationRunner {

  private final UserAccountRepository userAccountRepository;
  private final PasswordEncoder passwordEncoder;

  // 🔑 Chuỗi dùng để nhận diện mật khẩu chưa mã hóa (Plain Text)
  private static final String PLAINTEXT_MARKER = "PLAINTEXT_123";
  private static final String ACTUAL_PASSWORD = "123";

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {
    log.info("⏳ Kiểm tra và mã hóa mật khẩu từ SQL Seeding...");

    // 1. Tìm tất cả UserAccount có mật khẩu khớp với chuỗi đánh dấu
    // 💡 Giả định UserRepository của bạn có phương thức findByPasswordHash
    List<UserAccount> unhashedUsers = userAccountRepository.findByPasswordHash(PLAINTEXT_MARKER);

    if (unhashedUsers.isEmpty()) {
      log.info("✅ Không tìm thấy mật khẩu cần mã hóa. Bỏ qua.");
      return;
    }

    String encodedPassword = passwordEncoder.encode(ACTUAL_PASSWORD);

    // 2. Lặp qua và cập nhật từng User
    for (UserAccount user : unhashedUsers) {
      user.setPasswordHash(encodedPassword); // 🔑 SET MẬT KHẨU ĐÃ MÃ HÓA
      userAccountRepository.save(user);
      log.info("   - Mã hóa thành công mật khẩu cho user: {}", user.getUsername());
    }

    log.info("✅ Hoàn tất quá trình mã hóa mật khẩu sau SQL Seeding.");
  }
}
