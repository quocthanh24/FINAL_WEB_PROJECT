package thanhluu.service;

import org.springframework.stereotype.Service;

import thanhluu.entity.OrderDetailEntity;

@Service
public interface IOrderDetailService {

	public void save(OrderDetailEntity orderDetail);
	
}
