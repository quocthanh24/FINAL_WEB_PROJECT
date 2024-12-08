package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import thanhluu.entity.ProductEntity;
import thanhluu.entity.UserEntity;
import thanhluu.entity.WishListEntity;
import thanhluu.repository.IWishListRepository;
import thanhluu.service.IWishListService;

@Service
public class WishListServiceImpl implements IWishListService {

	
	@Autowired
	private IWishListRepository iWishListRepository;
	
	@Override
	public void save(WishListEntity wishlist) {
		// TODO Auto-generated method stub
		iWishListRepository.save(wishlist);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		iWishListRepository.deleteById(id);
	}

	@Override
	public Optional<WishListEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return iWishListRepository.findById(id);
	}

	@Override
	public Page<WishListEntity> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return iWishListRepository.findAll(pageable);
	}

	@Override
	public List<WishListEntity> findALl() {
		// TODO Auto-generated method stub
		return iWishListRepository.findAll();
	}

	@Override
	public WishListEntity findByUser(UserEntity user) {
		// TODO Auto-generated method stub
		return iWishListRepository.findByUser(user);
	}

	@Override
	public void deleteProductFromWishList(Long wishlistId, Long productId) {
		// TODO Auto-generated method stub
		 WishListEntity wishlist = iWishListRepository.findById(wishlistId)
	                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));

		 List<ProductEntity> products = wishlist.getProducts();
	     products.removeIf(product -> product.getId().equals(productId));

	     wishlist.setProducts(products);
	     iWishListRepository.save(wishlist);
	}

}
