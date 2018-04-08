package com.spring.webflux.sample;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.spring.webflux.sample.client.model.Weather;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

    // Mono
    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(BodyInserters.fromObject("Hello, Spring!"));
    }
    
    // Flux (Cold Stream)
    public Mono<ServerResponse> weather(ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_STREAM_JSON)
            .body(Generator.getWeatherWeek(),Weather.class);
    }

    // Flux (Hot Stream)
    public Mono<ServerResponse> seconds(ServerRequest request) {
        Flux<String> publisher = Flux.<String>create(fluxSink -> {
            int index = 0;
            while( index < 100 ) {
              fluxSink.next(""+index);
              index++;
            }
        }).share();    
        //}).publish().autoConnect();
        //}).delayElements(Duration.ofMillis(1000)).share();    
        
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(publisher,String.class);
    }
    
    
    public static class Generator {
        public static Flux<Weather> getWeatherWeek() {
            List<Weather> list = new ArrayList<Weather>();
            Weather w;
            w = new Weather();
            w.setDayOfWeek("MONDAY");
            randomizeWeather(w);
            list.add(w);
            w = new Weather();
            w.setDayOfWeek("TUESDAY");
            randomizeWeather(w);
            list.add(w);
            w = new Weather();
            w.setDayOfWeek("WEDNESDAY");
            randomizeWeather(w);
            list.add(w);
            w = new Weather();
            w.setDayOfWeek("THURSDAY");
            randomizeWeather(w);
            list.add(w);
            w = new Weather();
            w.setDayOfWeek("FRIDAY");
            randomizeWeather(w);
            list.add(w);
            w = new Weather();
            w.setDayOfWeek("SATURDAY");
            randomizeWeather(w);
            list.add(w);
            w = new Weather();
            w.setDayOfWeek("SUNDAY");
            randomizeWeather(w);
            list.add(w);
            
            return Flux.fromIterable(list).share();
        }

        private static void randomizeWeather(Weather w) {
            w.setTemperature(ThreadLocalRandom.current().nextInt(48) + "°");
            w.setRainny(ThreadLocalRandom.current().nextInt(100) + "%");
            w.setSunny(ThreadLocalRandom.current().nextInt(100) + "%");
        }
    }

}
