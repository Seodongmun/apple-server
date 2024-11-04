package com.server.apple.controller;

import com.server.apple.server.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins =  {"*"}, maxAge = 6000)
public class PaymentController {

    @Autowired
    private PaymentService service;






}
