package individual.individual_backend.persistence;

import individual.individual_backend.persistence.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Integer> {
    @Query("SELECT DISTINCT c.brand FROM CarEntity c")
    List<String> findDistinctBrands();

}
