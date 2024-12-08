package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thanhluu.entity.RatingEntity;
import thanhluu.entity.UserEntity;
import thanhluu.repository.IRatingRepository;
import thanhluu.service.IRatingService;

@Service
public class RatingServiceImpl implements IRatingService{

	@Autowired
	private IRatingRepository iRatingRepository;
	
	@Override
	public List<RatingEntity> findByProductId(Long productId) {
		// TODO Auto-generated method stub
		return iRatingRepository.findByProductId(productId);
	}

	@Override
	public void save(RatingEntity rating) {
		// TODO Auto-generated method stub
		iRatingRepository.save(rating);
	}

	@Override
	public Optional<RatingEntity> findByUser(UserEntity user) {
		// TODO Auto-generated method stub
		return iRatingRepository.findByUser(user);
	}



}
