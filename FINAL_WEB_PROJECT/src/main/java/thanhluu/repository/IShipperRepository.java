package thanhluu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.ShipperEntity;

@Repository
public interface IShipperRepository extends JpaRepository<ShipperEntity, Long>{

}
