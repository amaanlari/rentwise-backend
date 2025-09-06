package com.rentwise.mapper;

import com.rentwise.dto.PaymentRecordDto;
import com.rentwise.model.PaymentMode;
import com.rentwise.model.PaymentRecord;
import com.rentwise.model.Rent;

public class PaymentRecordMapper {
    public static PaymentRecordDto toDto(PaymentRecord record) {
        return PaymentRecordDto.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .paymentDate(record.getPaymentDate())
                .paymentMode(record.getPaymentMode() != null ? record.getPaymentMode().name() : null)
                .referenceNumber(record.getReferenceNumber())
                .rentId(record.getRent() != null ? record.getRent().getRentId() : null)
                .build();
    }

    public static PaymentRecord toEntity(PaymentRecordDto dto, Rent rent) {
        return PaymentRecord.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .paymentDate(dto.getPaymentDate())
                .paymentMode(dto.getPaymentMode() != null ? PaymentMode.valueOf(dto.getPaymentMode()) : null)
                .referenceNumber(dto.getReferenceNumber())
                .rent(rent)
                .build();
    }
}