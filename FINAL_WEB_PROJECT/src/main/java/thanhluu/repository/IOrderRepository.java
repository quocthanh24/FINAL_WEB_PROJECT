package thanhluu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.OrderEntity;
import java.util.List;
import thanhluu.entity.UserEntity;


@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long>{

	
	public List<OrderEntity> findByUser(UserEntity user);
	
}
