package com.cobeliii;

import java.math.BigDecimal;

public class StripePaymentProcessor implements PaymentProcessor{
    @Override
    public boolean charge(BigDecimal amount) {
        return false;
    }
}
