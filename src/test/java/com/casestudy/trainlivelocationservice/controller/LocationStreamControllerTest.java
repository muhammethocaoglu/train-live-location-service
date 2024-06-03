package com.casestudy.trainlivelocationservice.controller;

import com.casestudy.trainlivelocationservice.model.LocationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@WebFluxTest(controllers = LocationStreamController.class)
public class LocationStreamControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    void test_should_return_location_stream_when_retrieve() {
        Flux<LocationResponse> result = webClient
                .get()
                .uri("/location-stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(LocationResponse.class)
                .getResponseBody();


        StepVerifier.create(result)
                .expectNextCount(1)
                .thenAwait(Duration.ofSeconds(1))
                .expectNextCount(1)
                .thenCancel()
                .verify();

    }
}