package org.example;

import java.util.HashMap;
import java.util.Map;

public class Portfolio<T> {

    private Map<T, Integer> outcome = new HashMap<>();


    public void addAsset(T key, int quantity) {
        outcome.put(key, outcome.getOrDefault(key, 0) + quantity);
    }

    public void deleteAsset(T key , int quantity) {
           int curentQuantity = outcome.getOrDefault(key , 0);
           if(curentQuantity <= quantity){
               outcome.remove(key);
           }else{
               outcome.put(key , curentQuantity - quantity);
           }

    }

    public int getQuantity(T key) {
        return outcome.getOrDefault(key, 0);
    }

    public Map<T, Integer> getOutcome() {
        return outcome;
    }

}