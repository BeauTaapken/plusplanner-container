package plus.planner.containerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.planner.containerservice.model.Component;
import plus.planner.containerservice.model.Part;
import plus.planner.containerservice.repository.ComponentRepository;
import plus.planner.containerservice.repository.PartRepository;

import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("containerservice/component")
@RestController
public class ComponentController {
    @Autowired
    private PartRepository partRepo;
    @Autowired
    private ComponentRepository componentRepo;
    private Session session;
    private ObjectMapper mapper;

    ComponentController(){
        mapper = new ObjectMapper();
        Object o = /*((Session)*/Persistence.createEntityManagerFactory("contservice");//.createEntityManager().getDelegate()).unwrap(Session.class);
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
        List<Component> components = session.createSQLQuery("CALL getcomponents(:projectid)").addEntity(Component.class).setParameter("projectid", projectid).list();
        components = components.stream().filter(x -> x.getComponentid() == projectid).collect(Collectors.toList());
        for (Component c:
             components) {
            c.setParts(session.createSQLQuery("CALL getparts(:componentid)").addEntity(Part.class).setParameter("componentid", c.getComponentid()).list());
            for (Part p :
                    c.getParts()) {
                URL url = null;
                URLConnection conn = null;
                try {
                    url = new URL("http://localhost:8081/subpart/read/" + p.getPartid());
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
    public void deleteComponent(@PathVariable Long componentid){
        componentRepo.deleteById(componentid);
    }
}
