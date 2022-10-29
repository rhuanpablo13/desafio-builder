package br.com.builder.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.builder.api.response.Response;
import br.com.builder.api.responses.TokenResponse;
import br.com.builder.api.services.TokenServices;

@RestController
@RequestMapping("/token")
@CrossOrigin(origins = "*")
public class TokenController {
    
    @Autowired
    private TokenServices tokenServices;


    /**
     * Recupera um token válido da api https://vagas.builders/api/builders/auth/tokens 
     * @return
     */
    @GetMapping
    public ResponseEntity<Response<TokenResponse>> getToken() {
        Response<TokenResponse> response = new Response<>();

        TokenResponse tokenResponse = tokenServices.getToken();
        if (tokenResponse != null) {
            response.setData(tokenResponse);
            return ResponseEntity.ok().body(response);
        }
        response.addError("Falha ao recuperar token");
        return ResponseEntity.badRequest().body(response);
    }

}
