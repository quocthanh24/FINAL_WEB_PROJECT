package thanhluu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
		model.addAttribute("orders", listOrder);
		
		return "ordertracking";		
	}
}
