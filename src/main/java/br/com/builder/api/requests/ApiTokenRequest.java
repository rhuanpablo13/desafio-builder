package br.com.builder.api.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiTokenRequest {
    
    private String client_id;
    private String client_secret;


    /**
     * Retorna um request token válido
     * @return
     */
    public static String getToken() {

        ApiTokenRequest token = ApiTokenRequest.builder()
        .client_id("bd753592-cf9b-4d1a-96b9-cb8b2c01bd12")
        .client_secret("4e8229fe-1131-439c-9846-799895a8be5b")
        .build();

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
