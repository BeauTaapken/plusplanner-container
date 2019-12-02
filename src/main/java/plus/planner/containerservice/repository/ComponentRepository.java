package plus.planner.containerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import plus.planner.containerservice.model.Component;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    @Query("SELECT c FROM Component c WHERE c.projectid = :projectid")
    List<Component> findByProjectId(@Param("projectid") Long Projectid);
}
