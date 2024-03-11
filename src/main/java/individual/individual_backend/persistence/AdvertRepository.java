package individual.individual_backend.persistence;

import individual.individual_backend.persistence.entity.AdvertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<AdvertEntity, Integer>{
    List<AdvertEntity> findByCar_Brand(String brand);
    List<AdvertEntity> findByUserId(Integer userId);
}
