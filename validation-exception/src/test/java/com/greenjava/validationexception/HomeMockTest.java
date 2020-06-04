package com.greenjava.validationexception;

import com.greenjava.validationexception.service.HomeService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class HomeTest {

    @Test
    public void calculateSum() {
        HomeService homeService = new HomeService();
        int actualResult = homeService.calculateSum(new int[]{1, 2, 3});
        int expectedResult = 6;
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void calculateSumEmpty() {
        HomeService homeService = new HomeService();
        int actualResult = homeService.calculateSum(new int[]{});
        int expectedResult = 0;
        assertEquals(expectedResult, actualResult);

    }
}
