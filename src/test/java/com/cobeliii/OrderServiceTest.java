package com.cobeliii;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PaymentProcessor paymentProcessor;
    @Mock
    private OrderRepository orderRepository;

    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;

    @InjectMocks
    private OrderService underTest;


    @Test
    void itShouldChargeWithAssertArg() {
        //given
        BigDecimal amount = BigDecimal.TEN;
        User user = new User(1, "James");
        when(paymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        //when
        boolean actual = underTest.processOrder(user ,amount);

        //Assert
        verify(paymentProcessor).charge(amount);
        verify(orderRepository).save(assertArg(order -> {
            assertThat(order.amount()).isEqualTo(amount);
            assertThat(order.user()).isEqualTo(user);
            assertThat(order.id()).isNotNull();
            assertThat(order.orderDate()).isBefore(ZonedDateTime.now()).isNotNull();
        }));
        assertThat(actual).isTrue();
    }

    @Test
    void itShouldChargeWithArgCaptor() {
        //given
        BigDecimal amount = BigDecimal.TEN;
        User user = new User(1, "James");
        when(paymentProcessor.charge(amount)).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(1);
        //when
        boolean actual = underTest.processOrder(user ,amount);

//        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        //Assert
        verify(paymentProcessor).charge(amount);
        verify(orderRepository).save(orderArgumentCaptor.capture());
        Order orderArgumentCaptorValue = orderArgumentCaptor.getValue();

        assertThat(orderArgumentCaptorValue.user()).isEqualTo(user);
        assertThat(orderArgumentCaptorValue.amount()).isEqualTo(amount);
        assertThat(orderArgumentCaptorValue.orderDate()).isBefore(ZonedDateTime.now()).isNotNull();
        assertThat(orderArgumentCaptorValue.id()).isNotNull();
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

    @Test
    void itShouldThrowWhenChargeFailsWithMockitoBDD() {
        //given
        BigDecimal amount = BigDecimal.TEN;
        given(paymentProcessor.charge(amount)).willReturn(false);
        //when
        assertThatThrownBy(() -> underTest.processOrder(null ,amount))
                .hasMessageContaining("Payment not processed")
                .isInstanceOf(IllegalStateException.class);
        //Assert
        then(paymentProcessor).should().charge(amount);
        then(orderRepository).shouldHaveNoInteractions();

    }
}