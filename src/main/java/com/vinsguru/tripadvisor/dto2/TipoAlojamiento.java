package com.vinsguru.tripadvisor.dto2;

import lombok.val;

import java.util.Random;

public enum TipoAlojamiento {

    HOTEL,

    CASA_DE_PLAYA,

    PH;

    public static TipoAlojamiento getAleatorio() {
        Random random = new Random();
        val valores = TipoAlojamiento.values();
        return valores[random.nextInt(valores.length)];
    }
}
