package com.greenjava.validationexception;

import com.greenjava.validationexception.service.DataService;
import com.greenjava.validationexception.service.HomeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class HomeMockTest {


    @InjectMocks
    private HomeService homeService;
    @Mock
    DataService dataServiceMock;

    @Before
    public void before() {
        homeService.setDataService(dataServiceMock);
    }

    @Test
    public void calculateSum() {

        Mockito.when(dataServiceMock.getData()).thenReturn(new int[]{1, 2, 3});
        int actualResult = homeService.calculateSumUsingDataService();
        assertEquals(6, actualResult);

    }

    @Test
    public void calculateSumEmpty() {

        Mockito.when(dataServiceMock.getData()).thenReturn(new int[]{0});
        int actualResult = homeService.calculateSumUsingDataService();
        assertEquals(0, actualResult);

    }
}
