package plus.planner.containerservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import plus.planner.containerservice.model.Part;
import plus.planner.containerservice.repository.PartRepository;

import java.util.List;

@RequestMapping("containerservice/part")
@RestController
public class PartController {
    private final Logger logger = LoggerFactory.getLogger(PartController.class);
    private final PartRepository repo;
    private final RestTemplate restTemplate;

    @Autowired
    public PartController(PartRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    @RequestMapping(path = "/create/{part}", method = RequestMethod.POST)
    public void createPart(@RequestBody Part part) {
        logger.info("saving part: " + part.getPartid());
        repo.save(part);
        logger.info("saved part");
    }

    @RequestMapping(path = "/read/{projectid}", method = RequestMethod.GET)
    public List<Part> getParts(@PathVariable("projectid") String projectid){
        logger.info("getting parts for projectid: " + projectid);
        final List<Part> parts = repo.findByProjectId(projectid);
        for (Part p :
                parts) {
            logger.info("getting subparts for part: " + p.getPartid());
            p.setSubparts(restTemplate.getForObject("https://plus-planner-subpart-service/read/" +  p.getPartid(), String.class));
        }
        logger.info("returning parts");
        return parts;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public void updatePart(@RequestBody Part part) {
        logger.info("updating part: " + part.getPartid());
        repo.save(part);
        logger.info("updated part");
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public void deletePart(@RequestBody String partid) {
        logger.info("deleting part: " + partid);
        repo.deleteById(partid);
        logger.info("deleted part");
    }
}
