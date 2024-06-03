package com.casestudy.trainlivelocationservice.controller;

import com.casestudy.trainlivelocationservice.model.LocationDto;
import com.casestudy.trainlivelocationservice.model.LocationResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;


@RestController
@RequestMapping("/location-stream")
public class LocationStreamController {

    @GetMapping(path = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<LocationResponse> retrieve() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<LocationResponse> locationStream = Flux.fromStream(Stream.generate(
                () -> LocationResponse.builder()
                        .id(new Random().nextInt(100))
                        .location(LocationDto.builder()
                                .lat(new Random().nextInt(90))
                                .lng(new Random().nextInt(180))
                                .build())
                        .build()));
        return Flux.zip(locationStream, interval, (key, value) -> key);
    }

}