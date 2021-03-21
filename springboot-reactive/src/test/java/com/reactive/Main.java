package com.reactive;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {

        AtomicReference<LocalDate> localDate = new AtomicReference<>();



        System.out.println(null == localDate.getPlain());
        localDate.set(LocalDate.now());
        System.out.println(null == localDate.getPlain());
        localDate.setPlain(null);
        System.out.println(null == localDate.getPlain());

    }
}
