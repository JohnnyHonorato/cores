package br.edu.ufcg.virtus.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import br.edu.ufcg.virtus.common.exception.BusinessException;

public final class HttpUtil {

    private HttpUtil() {
    }

    public static ResponseEntity<String> list(HttpServletRequest originalRequest, String url, String apiKey, String filter)
            throws BusinessException {

        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = getHeaders(originalRequest, apiKey);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        if (filter != null) {
            url += "?filter={filter}";
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class, filter);
        }

        return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    }

    public static ResponseEntity<String> getOne(HttpServletRequest originalRequest, String url, String apiKey) throws BusinessException {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = getHeaders(originalRequest, apiKey);
        final HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (final HttpStatusCodeException e) {
            final JsonElement root = new JsonParser().parse(e.getResponseBodyAsString());
            throw new BusinessException(root.getAsJsonObject().get("message").getAsString(), e.getStatusCode());
        }
    }

    public static ResponseEntity<String> post(HttpServletRequest originalRequest, String url, String body, String apiKey)
            throws BusinessException {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = getHeaders(originalRequest, apiKey);
        final HttpEntity<String> request = new HttpEntity<>(body, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (final HttpStatusCodeException e) {
            final JsonElement root = new JsonParser().parse(e.getResponseBodyAsString());
            throw new BusinessException(root.getAsJsonObject().get("message").getAsString(), e.getStatusCode());
        }
    }

    public static ResponseEntity<String> put(HttpServletRequest originalRequest, String url, String body, String apiKey)
            throws BusinessException {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = getHeaders(originalRequest, apiKey);
        final HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            return restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (final HttpStatusCodeException e) {
            final JsonElement root = new JsonParser().parse(e.getResponseBodyAsString());
            throw new BusinessException(root.getAsJsonObject().get("message").getAsString(), e.getStatusCode());
        }
    }

    public static ResponseEntity<String> delete(HttpServletRequest originalRequest, String url, String apiKey) throws BusinessException {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = getHeaders(originalRequest, apiKey);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (final HttpStatusCodeException e) {
            final JsonElement root = new JsonParser().parse(e.getResponseBodyAsString());
            throw new BusinessException(root.getAsJsonObject().get("message").getAsString(), e.getStatusCode());
        }
    }

    private static HttpHeaders getHeaders(HttpServletRequest request, String apiKey) {

        final String authorization = request.getHeader("Authorization");
        final String lang = (String) request.getAttribute("Accept-Language");

        final HttpHeaders httpHeaders = new HttpHeaders();

        if (StringUtils.trimToNull(authorization) != null) {
            httpHeaders.add("Authorization", authorization);
        }

        if (StringUtils.trimToNull(authorization) != null) {
            httpHeaders.add("Accept-Language", lang);
        }

        httpHeaders.add("apikey", apiKey);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
}
