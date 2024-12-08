package thanhluu.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thanhluu.entity.OrderEntity;
import thanhluu.entity.UserEntity;
import thanhluu.repository.IOrderRepository;
import thanhluu.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService{

	
	@Autowired
	private IOrderRepository iOrderRepository;
	
	@Override
	public void save(OrderEntity order) {
		// TODO Auto-generated method stub
		iOrderRepository.save(order);
	}

	

	
	
}
