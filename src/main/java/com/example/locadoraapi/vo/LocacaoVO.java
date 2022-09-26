package com.example.locadoraapi.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LocacaoVO {

    private Long id;
    private Long idCarro;
    private LocalDate inicio;
    private LocalDate fim;
    private boolean emVigor;
}
