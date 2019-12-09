package plus.planner.containerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import plus.planner.containerservice.model.Component;
import plus.planner.containerservice.model.Part;
import plus.planner.containerservice.repository.ComponentRepository;
import plus.planner.containerservice.repository.PartRepository;

import java.io.IOException;
import java.util.List;

@RequestMapping("containerservice/component")
@RestController
public class ComponentController {
    @Autowired
    private PartRepository partRepo;
    @Autowired
    private ComponentRepository componentRepo;
    private ObjectMapper mapper;
    @Autowired
    private RestTemplate restTemplate;

    ComponentController() {
        mapper = new ObjectMapper();
    }

    @RequestMapping(path = "/create/{component}")
    public void createComponent(@PathVariable String component) {
        try {
            componentRepo.save(mapper.readValue(component, Component.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/read/{projectid}")
    public String readComponent(@PathVariable Long projectid) throws IOException {
        List<Component> components = componentRepo.findByProjectId(projectid);
        for (Component c :
                components) {
            c.setParts(partRepo.findByComponentId(c.getComponentid()));
            for (Part p :
                    c.getParts()) {
                String subpart = restTemplate.getForObject("http://plus-planner-subpart-service/subpart/read/" + p.getPartid(), String.class);
                p.setSubparts(subpart);
            }
        }
        String json = mapper.writeValueAsString(components);
        json = json.replace("\"[", "[");
        json = json.replace("]\"", "]");
        json = json.replace("\\\"", "\"");
        return json;
    }

    @RequestMapping(path = "/update/{component}")
    public void updateComponent(@PathVariable String component) {
        try {
            componentRepo.save(mapper.readValue(component, Component.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/delete/{componentid}")
    public void deleteComponent(@PathVariable Long componentid) {
        componentRepo.deleteById(componentid);
    }
}
