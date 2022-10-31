package br.com.builder.api.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.builder.api.requests.ApiTokenRequest;
import br.com.builder.api.responses.TokenResponse;

@Service
public class TokenServices {
    
    private String uri = "https://vagas.builders/api/builders/auth/tokens";


    /**
     * Recupera um token válido da api https://vagas.builders/api/builders/auth/tokens
     * @return
     */
    public TokenResponse getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(ApiTokenRequest.getToken(), headers);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);
        if (response == null || response.getBody() == null || response.getBody().isBlank()) {
            return null;
        }

        return TokenResponse.jsonToObject(response.getBody());
    }
}
