package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thanhluu.entity.CartItemEntity;
import thanhluu.entity.ProductEntity;
import thanhluu.entity.ShoppingCartEntity;
import thanhluu.repository.ICartItemRepository;
import thanhluu.service.ICartItemService;

@Service
public class CartItemServiceImpl implements ICartItemService{

	@Autowired
	private ICartItemRepository cartItemRepository;

	@Override
	public Optional<CartItemEntity> findByProductId(Long id) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByProductId(id);
	}

	@Override
	public List<CartItemEntity> findByShoppingCart(ShoppingCartEntity shoppingCart) {
		// TODO Auto-generated method stub
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	@Override
	public void deleteById(Long cartId) {
		// TODO Auto-generated method stub
		cartItemRepository.deleteById(cartId);
	}

	

	
	
	
}
