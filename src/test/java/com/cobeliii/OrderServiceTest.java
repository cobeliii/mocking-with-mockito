package com.cobeliii;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;

    @InjectMocks
    private OrderService underTest;


    @Test
    void itShouldCharge() {
        //given
        BigDecimal amount = BigDecimal.TEN;
        when(paymentProcessor.charge(amount)).thenReturn(true);
        //when
        boolean actual = underTest.processOrder(amount);

        //Assert
        verify(paymentProcessor).charge(amount);
        assertThat(actual).isTrue();
    }


}