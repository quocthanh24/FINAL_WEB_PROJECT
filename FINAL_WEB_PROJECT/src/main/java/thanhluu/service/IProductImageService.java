package thanhluu.service;

import org.springframework.stereotype.Service;

import thanhluu.entity.ProductImageEntity;

@Service
public interface IProductImageService {

	public void save(ProductImageEntity productImageEntity);
	
	public void deleteById(Long id);
}
