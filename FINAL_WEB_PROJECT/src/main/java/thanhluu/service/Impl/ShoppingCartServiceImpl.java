package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thanhluu.entity.CartItemEntity;
import thanhluu.entity.ProductEntity;
import thanhluu.entity.ShoppingCartEntity;
import thanhluu.entity.UserEntity;
import thanhluu.repository.ICartItemRepository;
import thanhluu.repository.IProductRepository;
import thanhluu.repository.IShoppingCartRepository;
import thanhluu.service.IShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService{

	@Autowired
	private IProductRepository iProductRepository;
	
	
	@Autowired
	private IShoppingCartRepository shoppingCartRepository;
	
	
	@Autowired
	private ICartItemRepository cartItemRepository;
	
	@Override
	public Optional<ShoppingCartEntity> findByUserId(Long user_id) {
		// TODO Auto-generated method stub
		return shoppingCartRepository.findByUser_Id(user_id) ;
	}

	@Override
	public void updateCartItemQuantity(Long itemId, int quantity) {
		// TODO Auto-generated method stub
		CartItemEntity cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found: " + itemId));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
		
	}

	@Override
	public Optional<ShoppingCartEntity> findByUser(UserEntity user) {
		// TODO Auto-generated method stub
		return shoppingCartRepository.findByUser_Id(user.getId());
	}

	@Override
	public ShoppingCartEntity createCartForUser(UserEntity user) {
		// TODO Auto-generated method stub
		ShoppingCartEntity newCart = new ShoppingCartEntity();
	    newCart.setUser(user);
	    return shoppingCartRepository.save(newCart);
	}

	@Override
	public void addProductToCart(ShoppingCartEntity cart, Long productId, int quantity) {
		// TODO Auto-generated method stub
		// Kiểm tra nếu sản phẩm đã có trong giỏ hàng
		
		
		
        Optional<CartItemEntity> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        
        if (existingItem.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ, tăng số lượng
        	CartItemEntity item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            
            cartItemRepository.save(item);
        } else {
            // Nếu sản phẩm chưa có, thêm mới vào giỏ hàng
            ProductEntity product = iProductRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            CartItemEntity newItem = new CartItemEntity();
            
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            newItem.setShoppingCart(cart);
            
            cartItemRepository.save(newItem);
            
            cart.getCartItems().add(newItem);
        }
    }

	@Override
	public Long getSubTotal(Long userId) {
		// TODO Auto-generated method stub
		 // Tính tổng giá trị sản phẩm trong giỏ hàng
		
		Optional<ShoppingCartEntity> optionalShoppingcart = shoppingCartRepository.findByUser_Id(userId);
		
		if (optionalShoppingcart.isPresent()) {
			
			ShoppingCartEntity shoppingcart = optionalShoppingcart.get();
			
			List<CartItemEntity> items = cartItemRepository.findByShoppingCart(shoppingcart);
		    return items.stream().mapToLong(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
		}
		
		return null;
	   
	}

	@Override
	public void updateProductToCart(ShoppingCartEntity cart, Long productId, int quantity) {
		// TODO Auto-generated method stub
		Optional<CartItemEntity> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        
        if (existingItem.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ, tăng số lượng
        	CartItemEntity item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // Nếu sản phẩm chưa có, thêm mới vào giỏ hàng
            ProductEntity product = iProductRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            CartItemEntity newItem = new CartItemEntity();
            
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            newItem.setShoppingCart(cart);
            
            cartItemRepository.save(newItem);
            
            cart.getCartItems().add(newItem);
        }
	}

	@Override
	public void save(ShoppingCartEntity cart) {
		// TODO Auto-generated method stub
		shoppingCartRepository.save(cart);
	}

	@Override
	public List<CartItemEntity> getCartItemsByShoppingCartId(Long shoppingCartId) {
		// TODO Auto-generated method stub
		 ShoppingCartEntity shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElse(null);
	        if (shoppingCart != null) {
	            return shoppingCart.getCartItems();
	        }
	     return null;
	}
	
}
