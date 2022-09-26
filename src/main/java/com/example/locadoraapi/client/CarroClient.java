package com.example.locadoraapi.client;

import com.example.locadoraapi.dto.CarroAlugadoDTO;
import com.example.locadoraapi.vo.CarroVO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

@Component
public class CarroClient {

    private RestTemplate restTemplate;

    @Value("${patch.uri.api.carro}")
    private String patchUriApiCarro;

    @Autowired
    public CarroClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void acionaIncrementoQuantidadeAlugueis(Long idCarro){
        CarroAlugadoDTO carroAlugadoDTO = new CarroAlugadoDTO(idCarro);
        configuraRestTemplateParaPatch();

        try {
           URI uri = new URI(patchUriApiCarro);
           restTemplate.exchange(uri, HttpMethod.PATCH, getEntityToRequest(carroAlugadoDTO), Void.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void configuraRestTemplateParaPatch() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.setRequestFactory(new
                HttpComponentsClientHttpRequestFactory(httpClient));
    }

    private HttpEntity<CarroAlugadoDTO> getEntityToRequest(CarroAlugadoDTO carroAlugadoDTO) {
        HttpEntity<CarroAlugadoDTO> requestEntity = new HttpEntity<>(carroAlugadoDTO);
        return requestEntity;
    }
}
