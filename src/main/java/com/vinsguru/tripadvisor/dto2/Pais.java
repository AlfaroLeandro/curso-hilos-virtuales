package com.vinsguru.tripadvisor.dto2;

import lombok.val;

import java.util.Random;

public enum Pais {
    ARGENTINA,
    BRASIL,
    CHILE,
    COLOMBIA,
    MEXICO,
    URUGUAY;


    public static Pais getAleatorio() {
        Random random = new Random();
        val valores = Pais.values();
        return valores[random.nextInt(valores.length)];
    }
}
