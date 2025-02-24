package com.example.coffee_shop.service;

import com.example.coffee_shop.model.Inquiry;
import com.example.coffee_shop.repository.InquiryRepository;
import org.springframework.stereotype.Service;

@Service
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public Inquiry sendInquiry(Inquiry inquiry){
        return inquiryRepository.save(inquiry);
    }
}
