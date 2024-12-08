package thanhluu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thanhluu.entity.CartItemEntity;
import thanhluu.entity.ProductEntity;
import thanhluu.entity.ShoppingCartEntity;

@Service
public interface ICartItemService {

	public Optional<CartItemEntity> findByProductId(Long id);
	
	public List<CartItemEntity> findByShoppingCart(ShoppingCartEntity shoppingCart);
	
	public void deleteById(Long cartId);
}
