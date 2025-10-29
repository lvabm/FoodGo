package com.foodgo.backend.module.payment_d.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
  @Override
  public void payWithMoMo() {}

  @Override
  public void payWithVnPay() {}

  @Override
  public void payWithZaloPay() {}

  @Override
  public void getPaymentStatus() {}
}
