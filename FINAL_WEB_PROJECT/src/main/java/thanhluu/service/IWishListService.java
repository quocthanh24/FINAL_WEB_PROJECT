package thanhluu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.UserEntity;
import thanhluu.entity.WishListEntity;

@Service
public interface IWishListService {
	
	public void save(WishListEntity wishlist);
	
	public void deleteById (Long id);
	
	public Optional<WishListEntity> findById(Long id);
	
	public Page<WishListEntity> findAll(Pageable pageable);
	
	public List<WishListEntity> findALl();
	
	public WishListEntity findByUser(UserEntity user);
	
	public void deleteProductFromWishList(Long wishlistId, Long productId);
	
	
}
