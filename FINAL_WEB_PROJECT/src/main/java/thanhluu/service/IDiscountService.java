package thanhluu.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.DiscountEntity;
import thanhluu.entity.UserEntity;



@Service
public interface IDiscountService {
	
	public Page<DiscountEntity> findAll(Pageable pageable);
	
	public Optional<DiscountEntity> findById(Long id);
	
	public DiscountEntity save(DiscountEntity discount);
	
	public void deleteById(Long id);
	
	public Optional<DiscountEntity> findByCode(String code);
	
	
}
