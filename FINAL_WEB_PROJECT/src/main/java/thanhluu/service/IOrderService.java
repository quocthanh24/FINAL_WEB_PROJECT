package thanhluu.service;

import org.springframework.stereotype.Service;

import thanhluu.entity.OrderEntity;
import thanhluu.entity.UserEntity;

@Service
public interface IOrderService {

	public void save(OrderEntity order);
	

}
