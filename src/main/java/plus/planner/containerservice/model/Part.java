package plus.planner.containerservice.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "part")
@EntityListeners(AuditingEntityListener.class)
public class Part {
    @Id
    private String partid;
    private String projectid;
    private String partname;
    private String enddate;
    private String url;
    @Transient
    @JsonRawValue
    private String subparts;
}
