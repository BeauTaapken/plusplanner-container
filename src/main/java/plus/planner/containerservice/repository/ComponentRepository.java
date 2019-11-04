package plus.planner.containerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plus.planner.containerservice.model.Component;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
}
