package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;


    @Autowired
    private WeatherService weatherService;

    // BEGIN
    @GetMapping(path = "/cities/{id}")
    public Map<String, String> getCity(@PathVariable Long id) throws JsonProcessingException {
        City city = cityRepository.findById(id).orElseThrow(() -> new CityNotFoundException("City not found"));
        return weatherService.getWeather(city);
    }

    @GetMapping(path = "/search")
    public Iterable<Map<String, String>> getToSearchCities(@RequestParam(value = "name", required = false) String name) {


        List<Map<String, String>> mapList = new ArrayList<>();
        List<City> cityList;
        if (name != null) {
            cityList = (List<City>) cityRepository.findByNameStartingWithIgnoreCase(name);
        } else {
            cityList = (List<City>) cityRepository.findByOrderByNameAsc();
        }

        for (City city : cityList) {
            try {
                Map<String, String> weatherCity = weatherService.getWeather(city);
                mapList.add(Map.of("temperature", weatherCity.get("temperature"),"name", city.getName()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        return mapList;
    }
    // END
}
