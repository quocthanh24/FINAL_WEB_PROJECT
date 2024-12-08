package thanhluu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.CartItemEntity;
import thanhluu.entity.ShoppingCartEntity;

import java.util.List;
import java.util.Optional;
import thanhluu.entity.ProductEntity;



@Repository
public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long>{

	//public CartItemEntity findByUser (Long userId);
	
	public List<CartItemEntity> findByShoppingCart(ShoppingCartEntity shoppingCart);
	
	public Optional<CartItemEntity> findByProductId(Long id);
	
	public void deleteByProduct(ProductEntity product);
	
	
}
