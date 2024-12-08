package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.ProductEntity;
import thanhluu.repository.IProductRepository;
import thanhluu.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	private IProductRepository productRepository;
	
	@Override
	public Page<ProductEntity> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findAll(pageable);
	}

	@Override
	public ProductEntity save(ProductEntity product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}

	@Override
	public Optional<ProductEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
	}

	@Override
	public List<ProductEntity> findAll() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

}
