package thanhluu.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import thanhluu.entity.OrderEntity;
import thanhluu.entity.UserEntity;
import thanhluu.repository.IOrderRepository;
import thanhluu.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService{

	
	@Autowired
	private IOrderRepository iOrderRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void save(OrderEntity order) {
		// TODO Auto-generated method stub
		iOrderRepository.save(order);
	}

	@Override
	public OrderEntity mergeOrder(OrderEntity order) {
		// TODO Auto-generated method stub
		if (order.getId() == null) {
            entityManager.persist(order); // Lưu mới
        } else {
            order = entityManager.merge(order); // Đồng bộ hóa (merge)
        }
        return order;
	}

	@Override
	public List<OrderEntity> findByUser(UserEntity user) {
		// TODO Auto-generated method stub
		return iOrderRepository.findByUser(user);
	}

	@Override
	public Optional<OrderEntity> findById(Long id) {
		// TODO Auto-generated method stub
		return iOrderRepository.findById(id);
	}

	@Override
	public List<OrderEntity> findByStatusNewAndDelivering() {
		// TODO Auto-generated method stub
		return iOrderRepository.findByStatusNewAndDelivering();
	}

	

	

	
	
}
