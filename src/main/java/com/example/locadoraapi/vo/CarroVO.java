package com.example.locadoraapi.vo;

import lombok.Data;

@Data
public class CarroVO {

    private Long id;
    private String marca;
    private String modelo;
    private boolean alugado;
}
