package com.greenjava.validationexception;

import com.greenjava.validationexception.service.DataService;
import com.greenjava.validationexception.service.HomeService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HomeStubTest {

    @Test
    public void calculateSumUsingStub() {
        HomeService homeService = new HomeService();
        homeService.setDataService(new DataServiceImpl());
        int actualResult = homeService.calculateSumUsingDataService();
        int expectedResult = 6;
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void calculateSumEmptyUsingStub() {
        HomeService homeService = new HomeService();
        int actualResult = homeService.calculateSum(new int[]{});
        int expectedResult = 0;
        assertEquals(expectedResult, actualResult);

    }

}
class DataServiceImpl implements DataService {

    @Override
    public int[] getData() {
        return new int[]{1,2,3};
    }
}
