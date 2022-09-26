package com.example.locadoraapi.config;

import com.example.locadoraapi.dto.CarroAlugadoDTO;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocacaoProducer {

    private static Logger LOGGER = LoggerFactory.getLogger(LocacaoProducer.class);

    private final JmsTemplate jmsTemplate;

    @Value("${activemq.name}")
    private String destinationQueue;

    public void sendMsgCarroAlugado(CarroAlugadoDTO carroAlugadoDTO){
        Gson gson = new Gson();
        String jsonCarroAlugado = gson.toJson(carroAlugadoDTO);
        LOGGER.info("Enviando mensagem " + jsonCarroAlugado + " para a fila " + destinationQueue);
        jmsTemplate.convertAndSend(destinationQueue, jsonCarroAlugado);
        LOGGER.info("Mensagem" + jsonCarroAlugado + "enviada para a fila" + destinationQueue);
    }

}
