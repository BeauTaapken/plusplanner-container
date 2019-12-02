package plus.planner.containerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plus.planner.containerservice.model.Part;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    @Query("SELECT p FROM Part p WHERE p.componentid = :componentid")
    List<Part> findByComponentId(@Param("componentid") Long componentid);
}
