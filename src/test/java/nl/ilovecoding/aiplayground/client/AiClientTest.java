package nl.ilovecoding.aiplayground.client;

import lombok.extern.slf4j.Slf4j;
import nl.ilovecoding.aiplayground.config.RestConfig;
import nl.ilovecoding.aiplayground.xmodel.AiRequest;
import nl.ilovecoding.aiplayground.xmodel.AiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest({RestConfig.class, AiClient.class})
@Slf4j
class AiClientTest {


    private MockRestServiceServer server;

    @Autowired
    private AiClient aiClient;

    @Autowired
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    void executeAiRequest() {

        AiRequest aiRequest = new AiRequest();
        aiRequest.setModel("model");
        aiRequest.setMaxTokens(150);

        server.expect(requestTo("https://api.openai.com/v1/completions"))
                .andRespond(withSuccess(successBody(), MediaType.APPLICATION_JSON));

        AiResponse aiResponse = aiClient.executeAiRequest(aiRequest);
        assertThat(aiResponse.getModel()).isEqualTo("text-davinci-003");
    }

    private String successBody() {

        return """
                {
                    "object": "text_completion",
                    "id": "cmpl-6QzF1xjlIobjlehxEYaUAjrEX8agh",
                    "created": 1671889959,
                    "model": "text-davinci-003",
                    "choices": [
                        {
                            "text": "\\n\\n1.One-Pot Chicken with Potatoes and Onion: https://www.thespruceeats.com/one-pot-chicken-with-potatoes-and-onion-3071249\\n\\nImage: https://www.thespruceeats.com/thmb/F9XxipDcu8RgEjygg_oXp5jAKbU=/866x613/filters:no_upscale():max_bytes(150000):strip_icc()/one-pot-chicken-with-potatoes-onions-3071249-5b3ac19fc9e77c003780e3b",
                            "finish_reason": "length"
                        }
                    ]
                }
                """;

    }
}