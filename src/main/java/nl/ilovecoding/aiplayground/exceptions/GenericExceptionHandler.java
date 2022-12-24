package nl.ilovecoding.aiplayground.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nl.ilovecoding.aiplayground.client.AiAuthenticationException;
import nl.ilovecoding.aiplayground.xmodel.AiError;
import nl.ilovecoding.aiplayground.xmodel.ExecuteRequest401Response;
import nl.ilovecoding.aiplayground.xmodel.ExecuteRequest404Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler {

	@ExceptionHandler(HttpClientErrorException.NotFound.class)
	public ResponseEntity<ExecuteRequest404Response> handleException(Exception ex, WebRequest webRequest)
			throws JsonProcessingException {

		log.info("error found {}", ex.getMessage());
		HttpClientErrorException clientException = (HttpClientErrorException) ex;

		String responseBodyAsString = clientException.getResponseBodyAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		ExecuteRequest404Response aiError = objectMapper.readValue(responseBodyAsString,
				ExecuteRequest404Response.class);
		return new ResponseEntity<>(aiError, new HttpHeaders(), HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(HttpClientErrorException.Unauthorized.class)
	public ResponseEntity<ExecuteRequest401Response> handleException(AiAuthenticationException ex,
																	 WebRequest webRequest) throws JsonProcessingException {

		log.info("error found {}", ex.getMessage());

		HttpClientErrorException cause = (HttpClientErrorException) ex.getCause();
		String responseBodyAsString = cause.getResponseBodyAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		ExecuteRequest401Response ai401 = objectMapper.readValue(responseBodyAsString,
				ExecuteRequest401Response.class);

		return new ResponseEntity<>(ai401, new HttpHeaders(), HttpStatus.UNAUTHORIZED);

	}

}
