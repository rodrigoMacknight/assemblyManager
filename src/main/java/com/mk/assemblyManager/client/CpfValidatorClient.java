package com.mk.assemblyManager.client;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CpfValidatorClient {

    private final RestTemplate restTemplate;

    public CpfValidatorClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON(String cpf) {
        String url = "http://user-info.herokuapp.com/users/" + cpf;
        return this.restTemplate.getForObject(url, String.class);
    }

    public boolean clientCanVote(String cpf) {
        if (getPostsPlainJSON(cpf).contains("UNABLE"))
            return false;
        return true;
    }
}
