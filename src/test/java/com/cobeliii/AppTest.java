package com.cobeliii;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AppTest {
    private final List<String> myMock = mock();
    private final List<String> names = new ArrayList<>();

    @Test
    void myFirstTestWithMock() {
        myMock.add("Hello");
        when(myMock.get(0)).thenReturn("Hello");
        verify(myMock).add("Hello");

        String actual = myMock.get(0);
        assertThat(actual).isEqualTo("Hello");
    }

    @Test
    void myFirstTestWithOutMock() {
        names.add("Hello");
        assertThat(names).hasSize(1);
    }

    @Test
    void itShouldVerifyNoInteractions() {
        //given
        List<String> listMock = mock();
        //when

        //Assert
        verifyNoInteractions(listMock);
    }

    @Test
    void itShouldVerifyNoMoreInteractions() {
        //given
        List<String> listMock = mock();
        //when
        listMock.add("Hello");
        listMock.clear();
        //Assert
        verify(listMock).add("Hello");
        verify(listMock).clear();
        verifyNoMoreInteractions(listMock);
    }


    @Test
    void itShouldVerifyInteractionsMode() {
        //given
        List<String> listMock = mock();
        //when
        listMock.add("Hello");
        listMock.add("Hello");
        //Assert
        verify(listMock, times(2)).add("Hello");
        verifyNoMoreInteractions(listMock);
    }
}
