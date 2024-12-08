package thanhluu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.UserEntity;
import thanhluu.entity.WishListEntity;

@Repository
public interface IWishListRepository extends JpaRepository<WishListEntity, Long>{

	public WishListEntity findByUser(UserEntity user);
	
	
}
