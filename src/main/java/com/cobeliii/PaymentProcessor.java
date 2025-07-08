package com.cobeliii;

import java.math.BigDecimal;

public interface PaymentProcessor {
    boolean charge(BigDecimal amount);
}
