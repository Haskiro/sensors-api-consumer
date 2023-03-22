import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer {
    private final static RestTemplate restTemplate;
    private final static HttpHeaders headers = new HttpHeaders();

    static {
        restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    public static void main(String[] args) throws JsonProcessingException {
        Sensor sensor4 = new Sensor("Sensor1");
        registerNewSensor(sensor4);
        addNewMeasurements(sensor4, 1000);

    }

    public static void registerNewSensor(Sensor sensor) {
        HttpEntity<Sensor> request = new HttpEntity<>(sensor, headers);
        String urlRegisterSensor = "http://localhost:8080/sensors/registration";

        String response = restTemplate.postForObject(urlRegisterSensor, request, String.class);
        System.out.println(response);
    }

    public static void addNewMeasurements(Sensor sensor, int count) {
        String urlAddMeasurement = "http://localhost:8080/measurements/add";
        Measurement measurement;

        for (int i = 0; i < count; i ++) {
            measurement = new Measurement(ThreadLocalRandom.current().nextDouble(-100, 100),
                    ThreadLocalRandom.current().nextBoolean(),
                    sensor);
            HttpEntity<Measurement> request = new HttpEntity<>(measurement, headers);

            restTemplate.postForObject(urlAddMeasurement, request, String.class);
        }
    }
}
