package thanhluu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.CategoryEntity;

@Service
public interface ICategoryService {
	
	public Page<CategoryEntity> findAll(Pageable pageable);
	
	public CategoryEntity save(CategoryEntity category);
	
	public Optional<CategoryEntity> findById(Long id);
	
	public void deleteById(Long id);
	
	public List<CategoryEntity> findAll();
}
