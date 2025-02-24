package com.example.coffee_shop.controller;

import com.example.coffee_shop.model.Inquiry;
import com.example.coffee_shop.service.InquiryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inquiry")
@CrossOrigin(origins = "http://localhost:3000")
public class InquiryController {
    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<Inquiry> newInquiry(@Valid @RequestBody Inquiry inquiry){
        return ResponseEntity.ok(inquiryService.sendInquiry(inquiry));
    }
}
