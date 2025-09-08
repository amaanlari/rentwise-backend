package com.rentwise.service;

import com.rentwise.dto.PaymentRecordDto;

import java.util.List;

public interface PaymentRecordService {
    PaymentRecordDto createPayment(PaymentRecordDto dto);
    PaymentRecordDto getPayment(Long id);
    List<PaymentRecordDto> getAllPayments();
    PaymentRecordDto updatePayment(Long id, PaymentRecordDto dto);
    void deletePayment(Long id);
}