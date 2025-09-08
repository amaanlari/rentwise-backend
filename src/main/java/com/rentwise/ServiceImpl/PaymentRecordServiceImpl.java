package com.rentwise.ServiceImpl;

import com.rentwise.dto.PaymentRecordDto;
import com.rentwise.mapper.PaymentRecordMapper;
import com.rentwise.model.PaymentMode;
import com.rentwise.model.PaymentRecord;
import com.rentwise.model.Rent;
import com.rentwise.repository.PaymentRecordRepository;
import com.rentwise.repository.RentRepository;
import com.rentwise.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentRecordServiceImpl implements PaymentRecordService {

    private final PaymentRecordRepository paymentRecordRepository;
    private final RentRepository rentRepository;

    @Override
    public PaymentRecordDto createPayment(PaymentRecordDto dto) {
        Rent rent = rentRepository.findById(dto.getRentId())
                .orElseThrow(() -> new RuntimeException("Rent not found with id: " + dto.getRentId()));
        PaymentRecord record = PaymentRecordMapper.toEntity(dto, rent);
        return PaymentRecordMapper.toDto(paymentRecordRepository.save(record));
    }

    @Override
    public PaymentRecordDto getPayment(Long id) {
        return paymentRecordRepository.findById(id)
                .map(PaymentRecordMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    @Override
    public List<PaymentRecordDto> getAllPayments() {
        return paymentRecordRepository.findAll().stream()
                .map(PaymentRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentRecordDto updatePayment(Long id, PaymentRecordDto dto) {
        PaymentRecord existing = paymentRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));

        existing.setAmount(dto.getAmount());
        existing.setPaymentDate(dto.getPaymentDate());

        // Convert String -> Enum
        existing.setPaymentMode(PaymentMode.valueOf(dto.getPaymentMode().toUpperCase()));

        existing.setReferenceNumber(dto.getReferenceNumber());

        return PaymentRecordMapper.toDto(paymentRecordRepository.save(existing));
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRecordRepository.existsById(id)) {
            throw new RuntimeException("Payment not found with id: " + id);
        }
        paymentRecordRepository.deleteById(id);
    }
}
