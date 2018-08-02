package com.dkt.breaking.service;

import com.dkt.breaking.model.MapData;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MapsService {

    private final WebClient webClient;

    public MapsService(@Value("${api.map.url}") String mapApi, @Value("${api.map.appkey}") String appKey) {
        this.webClient = WebClient
            .builder()
            .baseUrl(mapApi)
            .filter(logRequest())
            .defaultHeader("Authorization", appKey)
            .build();
    }

    /* 주소 검색 api
     * param: query, page, size
     * */
    public Mono<MapData> getAddress(String address) {
        String path = "v2/local/search/address.json";
        return requestMapApis(path, address);
    }

    /* 키워드로 장소 검색 api
     * param: query, category_group_code, x, y, radius, rect, page, size, sort
     * */
    public Mono<MapData> getPlaceByKeyword(MultiValueMap<String, String> query) {
        String path = "/v2/local/search/keyword.json";
        return requestMapApisWithQuery(path, query);
    }

    /* 카테고리로 장소 검색 api */
    public Object getPlaceByCategory(String category) {
        String host = " /v2/local/search/category.json";
        return requestMapApis(host, category);
    }

    public Mono<MapData> requestMapApis(String path, String address) {
        return this.webClient
            .get()
            .uri(builder -> builder.path(path).queryParam("query", address).build())
            .retrieve()
            .bodyToMono(MapData.class);
    }

    /**
     * 지도 api
     *
     * @param path  Daum Map Api host
     * @param query 검색을 원하는 질의어
     * @return 검색결과
     * @throws
     * @seehttps://developers.kakao.com/docs/restapi/local
     */

    public Mono<MapData> requestMapApisWithQuery(String path, MultiValueMap<String, String> query) {
        Mono<MapData> result = this.webClient
            .get()
            .uri(builder -> builder.path(path).queryParams(query).build())
            .retrieve()
            .bodyToMono(MapData.class);

        return result;
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }
}
