package thanhluu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import thanhluu.entity.OrderDetailEntity;
import thanhluu.entity.OrderEntity;
import thanhluu.service.IOrderService;

@Controller
@RequestMapping("/order")
public class ShipperController {

	@Autowired
	private IOrderService iOrderService;
	
	@GetMapping("/shipping")
	public String getShippingList(Model model) {
		
		List<OrderEntity> orders = iOrderService.findByStatusNewAndDelivering();
	    model.addAttribute("orders", orders);
	    
		
		
		return "shipping-list";
	}
	
	@PostMapping("update-status/{id}/{status}")
	public String updateOrderStatus(@PathVariable("id") Long id,
									@PathVariable("status") String status,
									Model model) {
		// Lấy đơn hàng từ cơ sở dữ liệu
        Optional<OrderEntity> optionalOrder = iOrderService.findById(id);
        
        if (optionalOrder.isPresent()) {
        	
        	OrderEntity order = optionalOrder.get();
        	// Kiểm tra nếu đơn hàng tồn tại và có OrderDetail
            if (order.getOrderDetail() != null) {
                OrderDetailEntity orderDetail = order.getOrderDetail();
                // Cập nhật trạng thái của đơn hàng
                orderDetail.setStatus(status);
                iOrderService.save(order); // Lưu đơn hàng với trạng thái mới
            }

            // Trả về trang danh sách đơn hàng
            //model.addAttribute("orders", orderService.getOrdersWithNewStatus());
            return "redirect:/order/shipping";
        }
        
        return "redirect:/home";
	}
}
