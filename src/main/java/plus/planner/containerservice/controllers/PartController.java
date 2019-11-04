package plus.planner.containerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.planner.containerservice.model.Part;
import plus.planner.containerservice.repository.PartRepository;

import java.io.IOException;

@RequestMapping("ContainerService/Part")
@RestController
public class PartController {
    @Autowired
    PartRepository repo;
    ObjectMapper mapper;

    PartController(){
        mapper = new ObjectMapper();
    }

    @RequestMapping(path = "/create/{part}")
    public void createSubPart(@PathVariable String part) {
        try {
            repo.save(mapper.readValue(part, Part.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/update/{part}")
    public void updateSubPart(@PathVariable String part) {
        try {
            repo.save(mapper.readValue(part, Part.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/delete/{partid}")
    public void deleteSubPart(@PathVariable Long partid){
        repo.deleteById(partid);
    }
}
