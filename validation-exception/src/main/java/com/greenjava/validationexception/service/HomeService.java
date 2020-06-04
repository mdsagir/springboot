package com.greenjava.validationexception.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HomeService {

    public String homeService() {
        System.out.println("Home Service is calling");
        return "service";
    }

    private DataService dataService;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    public int calculateSum(int[] data) {
        int sum = 0;
        for (int value : data) {
            sum += value;
        }
        return sum;
    }

    public int calculateSumUsingDataService() {
        int sum = 0;
        int[] data = dataService.getData();
        for (int value : data) {
            sum += value;
        }
        return sum;
    }

    public void scopeTest() {

    }
}
