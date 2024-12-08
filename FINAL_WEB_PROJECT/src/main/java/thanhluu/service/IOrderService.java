package thanhluu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import thanhluu.entity.OrderEntity;
import thanhluu.entity.UserEntity;

@Service
public interface IOrderService {

	public void save(OrderEntity order);
	
	public OrderEntity mergeOrder(OrderEntity order);
	
	public List<OrderEntity> findByUser(UserEntity user);
}
