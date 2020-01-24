package plus.planner.containerservice.providers;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SubPartProvider implements ISubPartProvider {
    private final RestTemplate restTemplate;

    public SubPartProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getSubParts(String partid) {
        return restTemplate.getForObject("https://plus-planner-subpart-service/subpart/read/" +  partid, String.class);
    }
}
