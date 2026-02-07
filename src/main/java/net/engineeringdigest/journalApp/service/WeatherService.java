package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String apiKey="c8ece6c7fc5da3f776e4d4f797e47667";

    private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse=redisService.get("weather_of_"+city,WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }else{
            String finalAPI=API.replace("CITY",city).replace("API_KEY",apiKey);

            ResponseEntity<WeatherResponse>response= restTemplate.exchange(finalAPI, HttpMethod.POST,null, WeatherResponse.class);
            WeatherResponse body=response.getBody();
            if(body!=null){
                redisService.set("weather_of"+city,body,300l);
            }
            return body;
        }

        /*HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set("key","value");


        User user=User.builder().userName("ram").password("ram").build();
        HttpEntity<User> httpEntity=new HttpEntity<>(user,httpHeaders);
*/



    }
}
