package thanhluu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.ProductEntity;

@Service
public interface IProductService {
	
	public Page<ProductEntity> findAll(Pageable pageable);
	
	public ProductEntity save(ProductEntity product);
	
	public Optional<ProductEntity> findById(Long id);
	
	public void deleteById(Long id);
	
	public List<ProductEntity> findAll();
}
