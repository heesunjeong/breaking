package com.dkt.breaking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class MapsService {
    RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${api.map.url}")
    private String MAP_API;

    @Value(value = "${api.map.appkey}")
    private String APP_KEY;

    public Object requestMapApis(String host, String address) {
        HttpHeaders headers = new HttpHeaders();
        URI uri = UriComponentsBuilder.fromHttpUrl(MAP_API).path(host).queryParam("query", address).build().encode().toUri();
        headers.add("Authorization", APP_KEY);

        ResponseEntity response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity(headers), Object.class);

        return response.getBody();
    }
    /**
     * 지도 api
     *
     * @param     host Daum Map Api host
     * @param     query 검색을 원하는 질의어
     * @return    검색결과
     * @exception
     *
     * @see https://developers.kakao.com/docs/restapi/local
     */

    public Object requestMapApisWithQuery(String host, Map<String, String> query) {
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MAP_API).path(host);
        headers.add("Authorization", APP_KEY);

        for (Map.Entry<String, String> entry : query.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        URI uri = builder.build().encode().toUri();

        ResponseEntity response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity(headers), Object.class);

        return response.getBody();
    }

    /* 주소 검색 api
     * param query, page, size
     * */
    public Object getAddress(String address) {
        String host = "v2/local/search/address.json";
        return requestMapApis(host, address);
    }

    /* 키워드로 장소 검색 api
     * param query, category_group_code, x, y, radius, rect, page, size, sort
     * */
    public Object getPlaceByKeyword(Map<String, String> query) {
        String host = "/v2/local/search/keyword.json";
        return requestMapApisWithQuery(host, query);
    }

    /* 카테고리로 장소 검색 api */
    public Object getPlaceByCategory(String category) {
        String host = " /v2/local/search/category.json";
        return requestMapApis(host, category);
    }
}
