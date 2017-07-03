package com.szyciov.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class TemplateHelper4OrgApi {
	public static RestTemplate template;
	
	static {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(90000);
        requestFactory.setConnectTimeout(90000);
        template = new RestTemplate(requestFactory);
        template.setErrorHandler(new DefaultResponseErrorHandler());
	}

	public <T> T dealRequest(String url, HttpMethod method, HttpServletRequest request, Object entity,
			Class<T> responseType, Object... uriVariables) throws RestClientException {

		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return dealRequestWithToken(url, method, userToken, entity, responseType, uriVariables);
	}

	public <T> T dealRequestWithToken(String url, HttpMethod method, String userToken, Object entity,
			Class<T> responseType, Object... uriVariables) throws RestClientException {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);

		ResponseEntity<T> responseEntity = template.exchange(SystemConfig.getSystemProperty("orgApi") + url, method,
				requestEntity,
				responseType, uriVariables);
		return responseEntity.getBody();
	}

	public <T> T dealRequest(String url, HttpMethod method, HttpServletRequest request, Object entity,
			Class<T> responseType) throws RestClientException {

		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return dealRequestWithToken(url, method, userToken, entity, responseType);
	}

	public <T> T dealRequestWithToken(String url, HttpMethod method, String userToken, Object entity,
			Class<T> responseType) throws RestClientException {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);

		ResponseEntity<T> responseEntity = template.exchange(SystemConfig.getSystemProperty("orgApi") + url, method,
				requestEntity,
				responseType);
		return responseEntity.getBody();
	}

	public <T> T dealRequest(String url, HttpMethod method, HttpServletRequest request, Object entity,
			Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {

		String userToken = (String) request.getAttribute(Constants.REQUEST_USER_TOKEN);
		return dealRequestWithToken(url, method, userToken, entity, responseType, uriVariables);
	}

	public <T> T dealRequestWithToken(String url, HttpMethod method, String userToken, Object entity,
			Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);

		ResponseEntity<T> responseEntity = template.exchange(SystemConfig.getSystemProperty("orgApi") + url, method,
				requestEntity,
				responseType, uriVariables);
		return responseEntity.getBody();
	}
}
