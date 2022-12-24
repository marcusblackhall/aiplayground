package nl.ilovecoding.aiplayground.client;

import org.springframework.web.client.HttpClientErrorException;

public class AiAuthenticationException extends RuntimeException {

	public AiAuthenticationException(HttpClientErrorException e) {
		super(e);
	}

}
