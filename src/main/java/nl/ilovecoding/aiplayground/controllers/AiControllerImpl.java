package nl.ilovecoding.aiplayground.controllers;

import lombok.extern.slf4j.Slf4j;
import nl.ilovecoding.aiplayground.client.AiClient;
import nl.ilovecoding.aiplayground.gen.api.CompletionsApi;
import nl.ilovecoding.aiplayground.xmodel.AiRequest;
import nl.ilovecoding.aiplayground.xmodel.AiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AiControllerImpl implements CompletionsApi {

	@Autowired
	private AiClient aiClient;

	// @PostMapping

	@Override
	@CrossOrigin
	public ResponseEntity<AiResponse> executeRequest(@RequestBody AiRequest aiRequest) {
		AiResponse aiResponse = executeAiRequest(aiRequest);
		return ResponseEntity.ok(aiResponse);
	}

	private AiResponse executeAiRequest(AiRequest aiRequest) {

		return aiClient.executeAiRequest(aiRequest);
	}

	// public ResponseEntity<nl.ilovecoding.aiplayground.xmodel.AiResponse>
	// executeRequest(nl.ilovecoding.aiplayground.xmodel.AiRequest aiRequest) {
	// return AiApi.super.executeRequest(aiRequest);
	// }

}
