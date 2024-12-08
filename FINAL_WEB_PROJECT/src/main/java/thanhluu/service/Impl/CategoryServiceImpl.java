package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.CategoryEntity;
import thanhluu.repository.ICategoryRepository;
import thanhluu.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService{

	@Autowired
	private ICategoryRepository categoryRepository;
	
	
	@Override
	public Page<CategoryEntity> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return categoryRepository.findAll(pageable);
	}


	@Override
	public CategoryEntity save(CategoryEntity category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}


	@Override
	public Optional<CategoryEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id);
	}


	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);
	}


	@Override
	public List<CategoryEntity> findAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

}
