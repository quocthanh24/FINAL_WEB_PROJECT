package thanhluu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import thanhluu.entity.OrderEntity;
import thanhluu.entity.UserEntity;
import thanhluu.service.IOrderService;

@Controller
public class OrderTrackingController {

	@Autowired
	private IOrderService iOrderService;
	
	@GetMapping("/ordertracking")
	public String getOrderList(HttpSession session,Model model){
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		List<OrderEntity> listOrder = iOrderService.findByUser(user); 
		
		System.out.println("Số order Detail là : " + listOrder.size());
		
		model.addAttribute("orders", listOrder);
		
		return "ordertracking";		
	}
	
	@PostMapping("/orders/cancel/{id}")
	public String cancelOrder(@PathVariable Long id) {
		// Lấy đơn hàng theo id
		Optional<OrderEntity> optionalOrder = iOrderService.findById(id);

		if (!optionalOrder.isPresent()) {
			return "redirect:/shop";
		}
		
		OrderEntity order = optionalOrder.get();
		
		// Kiểm tra nếu đơn hàng tồn tại và chưa bị hủy
		if (order != null && order.getOrderDetail() != null && !order.getOrderDetail().getStatus().equals("CANCELED")) {
			// Cập nhật trạng thái đơn hàng thành "CANCELED"
			order.getOrderDetail().setStatus("CANCELED");
			// Lưu lại thay đổi vào cơ sở dữ liệu
			iOrderService.save(order);
		}

		// Sau khi hủy đơn, chuyển hướng về trang danh sách đơn hàng
		return "redirect:/ordertracking"; // Chuyển hướng đến danh sách đơn hàng
	}
}
