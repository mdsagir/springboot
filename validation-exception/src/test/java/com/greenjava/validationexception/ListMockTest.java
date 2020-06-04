package com.greenjava.validationexception;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class ListMockTest {

    List<String> mock = Mockito.mock(List.class);

    @Test
    public void size_basic() {

        Mockito.when(mock.size()).thenReturn(5);
        Assert.assertEquals(5, mock.size());
    }

    @Test
    public void returnDifferentValue() {

        Mockito.when(mock.size()).thenReturn(5).thenReturn(10);
        Assert.assertEquals(5, mock.size());
        Assert.assertEquals(10, mock.size());
    }

    @Test
    public void returnWithParameter() {

        Mockito.when(mock.get(0)).thenReturn("java");
        Assert.assertEquals("java", mock.get(0));
        Assert.assertEquals(null, mock.get(1));
    }

    @Test
    public void returnWithGenericParameter() {

        Mockito.when(mock.get(Mockito.anyInt())).thenReturn("java");
        Assert.assertEquals("java", mock.get(0));
        Assert.assertEquals("java", mock.get(1));
    }

    @Test
    public void verificationBasic() {

        String value1 = mock.get(0);
        String value2 = mock.get(1);

        Mockito.verify(mock).get(0);
        Mockito.verify(mock, Mockito.times(2)).get(Mockito.anyInt());
        Mockito.verify(mock, Mockito.times(1)).get(Mockito.anyInt());
    }

    @Test
    public void argumentCapture() {

        mock.add("String1");
        mock.add("String2");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(mock,Mockito.times(2)).add(captor.capture());

        List<String> value = captor.getAllValues();
        Assert.assertEquals("String1", value.get(0));
        Assert.assertEquals("String2", value.get(1));
    }
}
