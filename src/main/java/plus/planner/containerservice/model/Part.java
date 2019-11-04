package plus.planner.containerservice.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "part")
@EntityListeners(AuditingEntityListener.class)
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partid;
    @NotBlank
    private String partname;
    @NotBlank
    private String enddate;
    @Transient
    private String subparts;

    public Part() {
    }

    public Long getPartid() {
        return partid;
    }

    public void setPartid(Long partid) {
        this.partid = partid;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getSubparts() {
        return subparts;
    }

    public void setSubparts(String subparts) {
        this.subparts = subparts;
    }
}
