package com.rentwise.controller;

import com.rentwise.dto.PaymentRecordDto;
import com.rentwise.service.PaymentRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    @PostMapping
    public ResponseEntity<PaymentRecordDto> createPayment(@RequestBody PaymentRecordDto paymentRecordDto) {
        return ResponseEntity.ok(paymentRecordService.createPayment(paymentRecordDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentRecordDto> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentRecordService.getPayment(id));
    }

    @GetMapping
    public ResponseEntity<List<PaymentRecordDto>> getAllPayments() {
        return ResponseEntity.ok(paymentRecordService.getAllPayments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentRecordDto> updatePayment(@PathVariable Long id, @RequestBody PaymentRecordDto paymentRecordDto) {
        return ResponseEntity.ok(paymentRecordService.updatePayment(id, paymentRecordDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentRecordService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
