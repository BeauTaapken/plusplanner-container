package plus.planner.containerservice.model;

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
    private Long partid;
    private Long componentid;
    private String partname;
    private String enddate;
    @Transient
    private String subparts;
}
