package plus.planner.containerservice.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "component")
@EntityListeners(AuditingEntityListener.class)
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long componentid;
    @NotBlank
    private String componentname;
    @NotBlank
    private String enddate;
    @Transient
    private List<Part> parts;

    public Component() {
    }

    public Long getComponentid() {
        return componentid;
    }

    public void setComponentid(Long componentid) {
        this.componentid = componentid;
    }

    public String getComponentname() {
        return componentname;
    }

    public void setComponentname(String componentname) {
        this.componentname = componentname;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
