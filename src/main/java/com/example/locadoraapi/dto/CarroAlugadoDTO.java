package com.example.locadoraapi.dto;

import lombok.Data;

@Data
public class CarroAlugadoDTO {

    public CarroAlugadoDTO(Long id) {
        this.id = id;
    }

    private Long id;
}
