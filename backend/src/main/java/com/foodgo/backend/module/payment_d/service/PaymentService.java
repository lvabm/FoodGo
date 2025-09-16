package com.foodgo.backend.module.payment_d.service;

public interface PaymentService {
  void payWithMoMo();

  void payWithVnPay();

  void payWithZaloPay();

  void getPaymentStatus();
}
