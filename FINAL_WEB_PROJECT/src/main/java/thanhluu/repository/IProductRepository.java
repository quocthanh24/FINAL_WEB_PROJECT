package thanhluu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import thanhluu.entity.ProductEntity;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long>{

	
	@Query("SELECT p FROM ProductEntity p WHERE p.category.name = :categoryName")
    Page<ProductEntity> findAllByCategory(@Param("categoryName") String categoryName, Pageable pageable);
}

