package com.szyciov.util;

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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TemplateHelper {
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
		ResponseEntity<T> responseEntity = template.exchange(SystemConfig.getSystemProperty("webApiUrl") + url, method,
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

		ResponseEntity<T> responseEntity = template.exchange(SystemConfig.getSystemProperty("webApiUrl") + url, method,
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

		ResponseEntity<T> responseEntity = template.exchange(SystemConfig.getSystemProperty("webApiUrl") + url, method,
				requestEntity,
				responseType, uriVariables);
		return responseEntity.getBody();
	}
	
	public <T> T dealRequestWithFullUrl(String url, HttpMethod method, Object entity,
			Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);
		ResponseEntity<T> responseEntity = template.exchange(url, method,
				requestEntity,
				responseType, uriVariables);
		return responseEntity.getBody();
	}
	
	public <T> T dealRequestWithFullUrlToken(String url, HttpMethod method, String userToken, Object entity,
			Class<T> responseType) throws RestClientException {
		/*
		HttpHeaders headers = new HttpHeaders();
		headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);
		*/

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);
		
		ResponseEntity<T> responseEntity = template.exchange(url, method,
				requestEntity,
				responseType);
		return responseEntity.getBody();
	}
	
	public <T> T dealRequestWithFullUrlToken(String url, HttpMethod method, String userToken, Object entity,
			Class<T> responseType, Object... uriVariables) throws RestClientException {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);

		ResponseEntity<T> responseEntity = template.exchange(url, method,
				requestEntity,
				responseType, uriVariables);
		return responseEntity.getBody();
	}
	
	public <T> T dealRequestWithTokenCarserviceApiUrl(String url, HttpMethod method, String userToken, Object entity,
			Class<T> responseType) throws RestClientException {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(Constants.REQUEST_USER_TOKEN, userToken);
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);

		ResponseEntity<T> responseEntity = template.exchange(SystemConfig.getSystemProperty("carserviceApi") + url, method,
				requestEntity,
				responseType);
		return responseEntity.getBody();
	}

    public <T> T dealRequestWithFullUrlTokenHeader(String url, HttpMethod method, String userToken, Object entity,
                                Map<String, String> headerMap, Class<T> responseType, Object... uriVariables) throws RestClientException {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add(Constants.REQUEST_USER_TOKEN, userToken);
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        if(null != headerMap && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(entity, headers);

        ResponseEntity<T> responseEntity = template.exchange(url, method,
                requestEntity,
                responseType, uriVariables);
        return responseEntity.getBody();
    }
}
