package com.rentwise.ServiceImpl;

import com.rentwise.dto.PaymentRecordDto;
import com.rentwise.service.PaymentRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {
    @Override
    public PaymentRecordDto createPayment(PaymentRecordDto dto){ System.out.println("Creating payment: "+dto); return dto;}
    @Override
    public List<PaymentRecordDto> getPaymentsByRent(Long rentId){ System.out.println("Fetching payments for rent: "+rentId); return new ArrayList<>(); }
}
