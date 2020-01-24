package plus.planner.containerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import plus.planner.containerservice.model.Part;
import plus.planner.containerservice.providers.ISubPartProvider;
import plus.planner.containerservice.repository.PartRepository;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RequestMapping("/part")
@RestController
public class PartController {
    private final Logger logger = LoggerFactory.getLogger(PartController.class);
    private final PartRepository repo;
    private final ISubPartProvider subPartProvider;
    private final ObjectMapper objectMapper;

    @Autowired
    public PartController(PartRepository repo, ISubPartProvider subPartProvider, ObjectMapper objectMapper) {
        this.repo = repo;
        this.subPartProvider = subPartProvider;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/create")
    public void createPart(@RequestBody String prt) throws IOException {
        final Part part = objectMapper.readValue(prt, Part.class);
        logger.info("saving part: {}", part.getPartid());
        repo.save(part);
        logger.info("saved part");
    }

    @GetMapping(path = "/read/{projectid}")
    public List<Part> getParts(@PathVariable("projectid") String projectid){
        logger.info("getting parts for projectid: {}", projectid);
        final List<Part> parts = repo.findByProjectId(projectid);
        for (Part p :
                parts) {
            logger.info("getting subparts for part: {}", p.getPartid());
            p.setSubparts(subPartProvider.getSubParts(p.getPartid()));
        }
        logger.info("returning parts");
        return parts;
    }

    @PostMapping(path = "/update")
    public void updatePart(@RequestBody String prt) throws IOException {
        final Part part = objectMapper.readValue(prt, Part.class);
        logger.info("updating part: {}", part.getPartid());
        repo.save(part);
        logger.info("updated part");
    }

    @PostMapping(path = "/delete")
    public void deletePart(@RequestBody String partid) {
        logger.info("deleting part: {}", partid);
        repo.deleteById(partid);
        logger.info("deleted part");
    }
}
