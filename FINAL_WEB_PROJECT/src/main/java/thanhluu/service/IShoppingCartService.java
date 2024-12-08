package thanhluu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import thanhluu.entity.CartItemEntity;
import thanhluu.entity.ProductEntity;
import thanhluu.entity.ShoppingCartEntity;
import thanhluu.entity.UserEntity;

@Service
public interface IShoppingCartService {
	
	public Optional<ShoppingCartEntity> findByUserId(Long user_id);
	
	public void updateCartItemQuantity(Long itemId, int quantity);
	
	public Optional<ShoppingCartEntity> findByUser(UserEntity user);
	
	public ShoppingCartEntity createCartForUser(UserEntity user);
	
	public void addProductToCart(ShoppingCartEntity cart, Long productId, int quantity);
	
	public void updateProductToCart(ShoppingCartEntity cart, Long productId, int quantity);
	
	public Long getSubTotal(Long userId);
	
	public void save(ShoppingCartEntity cart);
	
	
	
	 public List<CartItemEntity> getCartItemsByShoppingCartId(Long shoppingCartId);
}
