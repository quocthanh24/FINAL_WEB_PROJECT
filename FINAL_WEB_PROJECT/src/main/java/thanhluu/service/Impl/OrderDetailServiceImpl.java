package thanhluu.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thanhluu.entity.OrderDetailEntity;
import thanhluu.repository.IOrderDetailRepository;
import thanhluu.service.IOrderDetailService;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService{
	
	@Autowired
	private IOrderDetailRepository iOrderDetailRepository;

	@Override
	public void save(OrderDetailEntity orderDetail) {
		// TODO Auto-generated method stub
		iOrderDetailRepository.save(orderDetail);
	}
	
	

}
