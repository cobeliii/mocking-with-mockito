package com.cobeliii;


import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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

    @Test
    void mockitoBdd() {
        //given
        List<String> mockList = mock();
        //when(mockList.get(0)).thenReturn("hello");
        given(mockList.get(0)).willReturn("hello");
        //when
        String actual = mockList.get(0);

        //Assert
        //verify(mockList).get(0);
        then(mockList).should().get(0);
        assertThat(actual).isEqualTo("hello");
    }
}
