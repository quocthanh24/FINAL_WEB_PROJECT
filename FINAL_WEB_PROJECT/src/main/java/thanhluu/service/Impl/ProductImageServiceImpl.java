package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import thanhluu.entity.ProductImageEntity;
import thanhluu.repository.IProductImageRepository;
import thanhluu.service.IProductImageService;

@Service
public class ProductImageServiceImpl implements IProductImageService{

	@Autowired
	private IProductImageRepository imageRepository;
	
	@Override
	public void save(ProductImageEntity productImageEntity) {
		// TODO Auto-generated method stub
		imageRepository.save(productImageEntity);
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		imageRepository.deleteById(id);
	}
	

	
}
