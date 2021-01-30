package com.reactive.project;

import java.util.*;

/**
 * this methods returns top highest 2 value and lowest two value except the input values.
 * case 1 if six of map is less then 4 then return the same values.
 * case 2
 * Example:
 *
 */
public class TopTwoLowestPriceAndHighestPrice {

    public static void main(String[] args) {

        Map<String, Double> map=new LinkedHashMap<>();
        map.put("RELI",3200D);
        map.put("ICIBK",3100D);
        map.put("PNBBK",3000D);
        map.put("HDBCB",2500D);
        map.put("NOVI",2000D);
        map.put("APPL",1900D);
        map.put("SAMS",2100D);

        Map<String, Double> updated = updated(map,"HDBCB");
        System.out.println(updated);


    }
    private static Map<String, Double> updated(Map<String, Double> doubleMap, String input) {

        if (doubleMap.size() <= 4) {
            doubleMap.remove(input);
            return doubleMap;
        }

        Map<String, Double> updatedMap = new LinkedHashMap<>();
        Set<String> strings = doubleMap.keySet();
        List<String> stringList = new ArrayList<>(strings);
        int size = stringList.size();
        int index = stringList.indexOf(input);

        if (doubleMap.size() >= 5) {

            if (index == 0) {
                for (int i = index; i <= 4; i++) {
                    updatedMap.put(stringList.get(i), doubleMap.get(stringList.get(i)));
                }
            }
            if (index == 1) {
                for (int i = 0; i <= 4; i++) {
                    updatedMap.put(stringList.get(i), doubleMap.get(stringList.get(i)));
                }
            }

            if (!(index == 0 || index == 1) && index <= (size - 3)) {
                for (int i = index - 2; i <= index + 2; i++) {
                    try {
                        updatedMap.put(stringList.get(i), doubleMap.get(stringList.get(i)));
                    } catch (Exception exception) {
                        System.out.println("data not found for ric =" + input + " error = " + exception.getMessage());
                    }
                }
            }

            if (index == (size - 2)) {
                for (int i = index - 3; i <= index + 1; i++) {
                    updatedMap.put(stringList.get(i), doubleMap.get(stringList.get(i)));
                }
            }
            if (index == (size - 1)) {
                for (int i = index - 4; i <= index; i++) {
                    updatedMap.put(stringList.get(i), doubleMap.get(stringList.get(i)));
                }
            }
        }
        updatedMap.remove(input);
        return updatedMap;
    }

}
