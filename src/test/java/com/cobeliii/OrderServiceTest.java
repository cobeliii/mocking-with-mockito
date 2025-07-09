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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService underTest;


    @Test
    void itShouldCharge() {
        //given
        BigDecimal amount = BigDecimal.TEN;
        when(paymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        //when
        boolean actual = underTest.processOrder(null ,amount);

        //Assert
        verify(paymentProcessor).charge(amount);
        assertThat(actual).isTrue();
    }

    @Test
    void itShouldThrowWhenChargeFails() {
        //given
        BigDecimal amount = BigDecimal.TEN;
        when(paymentProcessor.charge(amount)).thenReturn(false);
        //when
        assertThatThrownBy(() -> underTest.processOrder(null ,amount))
                .hasMessageContaining("Payment not processed")
                .isInstanceOf(IllegalStateException.class);
        //Assert
        verify(paymentProcessor).charge(amount);
        verifyNoInteractions(orderRepository);
    }
}