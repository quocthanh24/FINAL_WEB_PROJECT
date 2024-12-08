package thanhluu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.RatingEntity;
import thanhluu.entity.UserEntity;

@Repository
public interface IRatingRepository extends JpaRepository<RatingEntity, Long>{

	
	public List<RatingEntity> findByProductId(Long productId);
	
	public Optional<RatingEntity> findByUser(UserEntity user);
}
