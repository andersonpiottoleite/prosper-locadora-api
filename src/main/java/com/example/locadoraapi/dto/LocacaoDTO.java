package com.example.locadoraapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LocacaoDTO {

    private Long idCarro;
    private LocalDate inicio;
    private LocalDate fim;
}
