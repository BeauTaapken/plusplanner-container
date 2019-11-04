package plus.planner.containerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import plus.planner.containerservice.model.Part;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
