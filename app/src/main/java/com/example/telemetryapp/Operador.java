package com.example.telemetryapp;

import java.io.Serializable;

public class Operador implements Serializable {
    public String email;
    public String dispositivo;

    public Operador(){}

    public Operador(String email, String dispositivo){
        this.email = email;
        this.dispositivo = dispositivo;
    }
}
