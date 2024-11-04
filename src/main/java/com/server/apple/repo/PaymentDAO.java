package com.server.apple.repo;

import com.server.apple.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDAO extends JpaRepository<Payment , Integer> {
}
