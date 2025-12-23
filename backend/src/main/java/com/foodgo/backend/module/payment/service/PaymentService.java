package com.foodgo.backend.module.payment.service;

public interface PaymentService {
  void payWithMoMo();

  void payWithVnPay();

  void payWithZaloPay();

  void getPaymentStatus();
}
