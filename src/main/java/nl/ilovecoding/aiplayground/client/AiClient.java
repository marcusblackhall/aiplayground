package nl.ilovecoding.aiplayground.client;

import nl.ilovecoding.aiplayground.xmodel.AiRequest;
import nl.ilovecoding.aiplayground.xmodel.AiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class AiClient {

    @Value("${ai.api.token}")
    private String aiApiToken;

    public AiResponse executeAiRequest(AiRequest aiRequest) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getBearerString());
        HttpEntity<AiRequest> entity = new HttpEntity<>(aiRequest, headers);
        ResponseEntity<AiResponse> aiResponse  = restTemplate.exchange("https://api.openai.com/v1/completions",
                HttpMethod.POST,
                entity, AiResponse.class);

        return aiResponse.getBody();

    }

    private String getBearerString() {
        return "Bearer " + aiApiToken;
    }
}
