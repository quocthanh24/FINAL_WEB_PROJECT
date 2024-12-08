package thanhluu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.DiscountEntity;

@Repository
public interface IDiscountRepository extends JpaRepository<DiscountEntity, Long>{

	public Optional<DiscountEntity> findByCode(String code);
	
}
