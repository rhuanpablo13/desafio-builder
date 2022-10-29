package br.com.builder.api.responses;

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
public class TokenResponse {
    
    private String token;
    private String expires_in;


    public static TokenResponse jsonToObject(String json) {
        ObjectMapper mapper = new ObjectMapper();
        if (json == null) return null;
        
        try {
            return mapper.readValue(json, TokenResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
