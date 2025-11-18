package com.foodgo.backend.module.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

  @Operation(summary = "Thanh toán đặt bàn qua MoMo")
  @PostMapping("/momo")
  private void MoMoPayment() {}

  @Operation(summary = "Thanh toán đặt bàn qua VnPay")
  @PostMapping("/vnpay")
  private void vnPayPayment() {}

  @Operation(summary = "Thanh toán đặt bàn qua ZaloPay")
  @PostMapping("/zalopay")
  private void zaloPayPayment() {}

  @Operation(summary = "Xem trạng thái thanh toán")
  @GetMapping("/payment-status/bookings/{bookingId}")
  private void getPaymentStatus(@PathVariable Integer bookingId) {}
}
