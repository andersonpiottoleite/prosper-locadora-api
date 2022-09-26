package com.example.locadoraapi.controller;

import com.example.locadoraapi.dto.LocacaoDTO;
import com.example.locadoraapi.service.LocacaoService;
import com.example.locadoraapi.vo.LocacaoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locacao")
public class LocacaoController {

    private LocacaoService locacaoService;

    @Autowired
    public LocacaoController(LocacaoService locacaoService) {
        this.locacaoService = locacaoService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody LocacaoDTO locacaoDTO){
        //locacaoService.save(locacaoDTO);
        locacaoService.send(locacaoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        LocacaoVO locacaoVO = locacaoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(locacaoVO);
    }

    @PatchMapping("/finaliza/{id}")
    public ResponseEntity<?> finalizaLocacao(@PathVariable("id") Long id){
        locacaoService.finalizaLocacao(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
