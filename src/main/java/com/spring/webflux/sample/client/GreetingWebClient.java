package com.spring.webflux.sample.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.spring.webflux.sample.client.model.Weather;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GreetingWebClient {

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    private List<Weather> weatherReport = new ArrayList<Weather>();

    // Mono sample
    public String hello() {
        //@formatter:off
        return webClient.get()
                .uri("/hello")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .flatMap(res -> res.bodyToMono(String.class))
                .block();
        //@formatter:on
    }
    
    // Flux (Cold Stream)
    public void weather() {
        weatherReport.clear();
        //@formatter:off
        webClient.get()
                .uri("/weather")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .flatMapMany(res -> res.bodyToFlux(Weather.class))
                .subscribe(w -> {
                    System.out.println(w);
                    weatherReport.add(w);
                  },
                  err -> System.out.println("Error on Weather Stream: " + err),
                  () -> System.out.println("Weather Report Finished!"));
        //@formatter:on  
    }

    // Flux (Hot Stream)
    public void seconds() {
      //@formatter:off
        webClient.get()
            .uri("/seconds")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange()
            .flatMapMany(response -> response.bodyToFlux(String.class))
            .subscribe(s -> {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + s);
                },
                err -> System.out.println("Error on Weather Stream: " + err),
                () -> System.out.println("Weather Report Finished!"));
      //@formatter:on
    }

    public static void main(String[] args) {
        GreetingWebClient gwc = new GreetingWebClient();
        //gwc.weather();
        gwc.seconds();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
