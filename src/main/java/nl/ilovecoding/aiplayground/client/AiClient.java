package nl.ilovecoding.aiplayground.client;

import lombok.RequiredArgsConstructor;
import nl.ilovecoding.aiplayground.xmodel.AiRequest;
import nl.ilovecoding.aiplayground.xmodel.AiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AiClient {

    @Value("${ai.api.token}")
    private String aiApiToken;


    private final RestTemplate restTemplate;

    public AiResponse executeAiRequest(AiRequest aiRequest) {


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "*/*");
        headers.add("Authorization", getBearerString());
        HttpEntity<AiRequest> entity = new HttpEntity<>(aiRequest, headers);

        try {
            ResponseEntity<AiResponse> aiResponse = restTemplate.exchange("https://api.openai.com/v1/completions",
                    HttpMethod.POST, entity, AiResponse.class);
            return aiResponse.getBody();
        } catch (HttpClientErrorException e) {

            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                throw new AiAuthenticationException(e);
            }
            throw e;

        }

    }

    private String getBearerString() {
        return "Bearer " + aiApiToken;
    }

}
