package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import exercise.HttpClient;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import exercise.CityNotFoundException;
import exercise.repository.CityRepository;
import exercise.model.City;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    // Клиент
    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
    WeatherService(HttpClient client) {
        this.client = client;
    }

    // BEGIN

    public Map<String, String> getWeather(City city) throws JsonProcessingException {
        List<City> list = (List<City>) cityRepository.findAll();
        if (!list.contains(city)) {
            throw new CityNotFoundException("City not found");
        }
        ObjectMapper objectMapper = new ObjectMapper();

        String json = client.get("http://weather/api/v2/cities/"+city.getName());
        return objectMapper.readValue(json, new TypeReference<>() {
        });
    }

    // END
}
