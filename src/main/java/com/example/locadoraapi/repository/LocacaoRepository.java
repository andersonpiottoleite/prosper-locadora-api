package com.example.locadoraapi.repository;

import com.example.locadoraapi.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

    Locacao findByIdCarroAndEmVigor(Long idCarro, Boolean emVigor);
}
