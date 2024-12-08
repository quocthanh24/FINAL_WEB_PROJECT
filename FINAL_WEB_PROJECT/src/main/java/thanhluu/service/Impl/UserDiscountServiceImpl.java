package thanhluu.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thanhluu.entity.DiscountEntity;
import thanhluu.entity.UserDiscountEntity;
import thanhluu.entity.UserEntity;
import thanhluu.repository.IUserDiscountRepository;
import thanhluu.service.IUserDiscountService;

@Service
public class UserDiscountServiceImpl implements IUserDiscountService{

	@Autowired
	private IUserDiscountRepository userDiscountRepository;

	@Override
	public UserDiscountEntity findByUserAndDiscount(UserEntity user, DiscountEntity discount) {
		// TODO Auto-generated method stub
		return userDiscountRepository.findByUserAndDiscount(user, discount);
	}

	@Override
	public void save(UserDiscountEntity userDiscount) {
		// TODO Auto-generated method stub
		userDiscountRepository.save(userDiscount);
	}

	@Override
	public List<UserDiscountEntity> findByUser(UserEntity user) {
		// TODO Auto-generated method stub
		return userDiscountRepository.findByUser(user);
	}
	
	
}
