package thanhluu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import thanhluu.entity.ShoppingCartEntity;
import thanhluu.entity.UserEntity;

@Repository
public interface IShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long>{

	@Query("SELECT sc FROM ShoppingCartEntity sc WHERE sc.user.id = :userId")
    public Optional<ShoppingCartEntity> findByUser_Id(@Param("userId") Long userId);
	
	
}
