package com.example.locadoraapi.service;

import com.example.locadoraapi.client.CarroClient;
import com.example.locadoraapi.config.LocacaoProducer;
import com.example.locadoraapi.dto.CarroAlugadoDTO;
import com.example.locadoraapi.dto.LocacaoDTO;
import com.example.locadoraapi.exception.LocacaoException;
import com.example.locadoraapi.exception.LocacaoNotFoundException;
import com.example.locadoraapi.model.Locacao;
import com.example.locadoraapi.repository.LocacaoRepository;
import com.example.locadoraapi.vo.CarroVO;
import com.example.locadoraapi.vo.LocacaoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LocacaoService {

    private static Logger LOGGER = LoggerFactory.getLogger(LocacaoService.class);
    private LocacaoRepository locacaoRepository;

    private CarroClient carroClient;

    private LocacaoProducer locacaoProducer;

    @Autowired
    public LocacaoService(LocacaoRepository locacaoRepository, CarroClient carroClient, LocacaoProducer locacaoProducer) {
        this.locacaoRepository = locacaoRepository;
        this.carroClient = carroClient;
        this.locacaoProducer = locacaoProducer;
    }

    public void save(LocacaoDTO locacaoDTO) {
        LOGGER.info("Salvando locação - " + locacaoDTO);
        Long idCarro = locacaoDTO.getIdCarro();
        verificaLocacaoExistente(idCarro);
        Locacao locacao = new Locacao();
        locacao.setEmVigor(true);
        BeanUtils.copyProperties(locacaoDTO, locacao);
        locacaoRepository.save(locacao);
        carroClient.acionaIncrementoQuantidadeAlugueis(idCarro);
        LOGGER.info("Locação salva - " + locacaoDTO);
    }

    private void verificaLocacaoExistente(Long idCarro){
        Locacao locacaoJaExistente = locacaoRepository.findByIdCarroAndEmVigor(idCarro, true);
        if (Objects.nonNull(locacaoJaExistente)){
            throw new LocacaoException("Já existe uma locação em vigor para o carro com o id " + idCarro);
        }
    }

    public void send(LocacaoDTO locacaoDTO) {
        LOGGER.info("Salvando locação - " + locacaoDTO);
        Long idCarro = locacaoDTO.getIdCarro();
        verificaLocacaoExistente(idCarro);
        Locacao locacao = new Locacao();
        locacao.setEmVigor(true);
        BeanUtils.copyProperties(locacaoDTO, locacao);
        locacaoRepository.save(locacao);
        LOGGER.info("Locação salva - " + locacaoDTO);

        CarroAlugadoDTO carroAlugadoDTO = new CarroAlugadoDTO(idCarro);
        locacaoProducer.sendMsgCarroAlugado(carroAlugadoDTO);
    }

    public LocacaoVO findById(Long id) {
        Locacao locacao = locacaoRepository.findById(id)
                .orElseThrow(() -> new LocacaoNotFoundException("Locacao não encontrada com o id " + id));
        LocacaoVO locacaoVO = new LocacaoVO();
        BeanUtils.copyProperties(locacao, locacaoVO);
        return locacaoVO;
    }

    public void finalizaLocacao(Long id) {
        Locacao locacao = locacaoRepository.findById(id)
                .orElseThrow(() -> new LocacaoNotFoundException("Locacao não encontrada com o id " + id));
        locacao.setEmVigor(false);
        locacaoRepository.save(locacao);
    }
}
