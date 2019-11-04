package plus.planner.containerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.planner.containerservice.model.Component;
import plus.planner.containerservice.model.Part;
import plus.planner.containerservice.repository.ComponentRepository;
import plus.planner.containerservice.repository.PartRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("ContainerService/Component")
@RestController
public class ComponentController {
    @Autowired
    PartRepository partRepo;
    @Autowired
    ComponentRepository componentRepo;
    ObjectMapper mapper;

    ComponentController(){
        mapper = new ObjectMapper();
    }

    @RequestMapping(path = "/create/{component}")
    public void createSubPart(@PathVariable String component) {
        try {
            componentRepo.save(mapper.readValue(component, Component.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/read/{projectid}")
    public String readSubPart(@PathVariable Long projectid) throws IOException {
        List<Component> components = componentRepo.findAll();
        components = components.stream().filter(x -> x.getComponentid() == projectid).collect(Collectors.toList());
        for (Component c:
             components) {
            c.setParts(partRepo.findAll());
            for (Part p :
                    c.getParts()) {
                URL url = null;
                URLConnection conn = null;
                try {
                    url = new URL("http://localhost:8081/SubPart/read/" + p.getPartid());
                    conn = url.openConnection();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                p.setSubparts(sb.toString());
            }
        }
        String json = mapper.writeValueAsString(components);
        json = json.replace("\"[", "[");
        json = json.replace("]\"", "]");
        return json;
    }

    @RequestMapping(path = "/update/{component}")
    public void updateSubPart(@PathVariable String component) {
        try {
            componentRepo.save(mapper.readValue(component, Component.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/delete/{componentid}")
    public void deleteSubPart(@PathVariable Long componentid){
        componentRepo.deleteById(componentid);
    }
}
