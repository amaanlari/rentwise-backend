package com.rentwise.controller;

import com.rentwise.dto.PaymentRecordDto;
import com.rentwise.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentRecordController {
    private final PaymentRecordService service;
    @PostMapping
    public PaymentRecordDto create(@RequestBody PaymentRecordDto dto){ return service.createPayment(dto);}
    @GetMapping("/rent/{rentId}") public List<PaymentRecordDto> getByRent(@PathVariable Long rentId){ return service.getPaymentsByRent(rentId);}
}
