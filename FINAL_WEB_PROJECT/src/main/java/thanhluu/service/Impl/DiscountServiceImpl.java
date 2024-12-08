package thanhluu.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.DiscountEntity;
import thanhluu.entity.UserDiscountEntity;
import thanhluu.entity.UserEntity;
import thanhluu.repository.IDiscountRepository;
import thanhluu.service.IDiscountService;


@Service
public class DiscountServiceImpl implements IDiscountService{

	@Autowired
	private IDiscountRepository discountRepository;

	@Override
	public Page<DiscountEntity> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return discountRepository.findAll(pageable);
	}

	@Override
	public Optional<DiscountEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return discountRepository.findById(id);
	}

	@Override
	public DiscountEntity save(DiscountEntity discount) {
		// TODO Auto-generated method stub
		return discountRepository.save(discount);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		discountRepository.deleteById(id);
	}

	@Override
	public Optional<DiscountEntity> findByCode(String code) {
		// TODO Auto-generated method stub
		return discountRepository.findByCode(code);
	}

	

	
	
	
	
}
