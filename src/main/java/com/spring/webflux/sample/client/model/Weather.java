package com.spring.webflux.sample.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weather {
    
    private String dayOfWeek;
    private String temperature;
    private String rainny;
    private String sunny;

}
