package thanhluu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thanhluu.entity.RatingEntity;
import thanhluu.entity.UserEntity;

@Service
public interface IRatingService {

	List<RatingEntity> findByProductId(Long productId);
	
	public void save(RatingEntity rating);
	
	public Optional<RatingEntity> findByUser(UserEntity user);
}
