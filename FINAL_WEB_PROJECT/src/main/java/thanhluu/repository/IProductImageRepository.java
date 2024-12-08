package thanhluu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.ProductImageEntity;

@Repository
public interface IProductImageRepository extends JpaRepository<ProductImageEntity, Long>{

}
