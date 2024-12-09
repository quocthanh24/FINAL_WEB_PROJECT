package thanhluu.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import thanhluu.entity.CartItemEntity;
import thanhluu.entity.DiscountEntity;
import thanhluu.entity.OrderDetailEntity;
import thanhluu.entity.OrderEntity;
import thanhluu.entity.ShoppingCartEntity;
import thanhluu.entity.UserDiscountEntity;
import thanhluu.entity.UserEntity;
import thanhluu.service.IOrderDetailService;
import thanhluu.service.IOrderService;
import thanhluu.service.IShoppingCartService;
import thanhluu.service.IUserDiscountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/checkout")
public class CheckOutController {

	@Autowired
	private IShoppingCartService iShoppingCartService;
	
	@Autowired
	private IUserDiscountService iUserDiscountService;
	
	@Autowired
	private IOrderService iOrderService;
	
	@Autowired
	private IOrderDetailService iOrderDetailService;
	
	@GetMapping
	public String getOrder(Model model,
							HttpSession session) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		ShoppingCartEntity shoppingCart = (ShoppingCartEntity) session.getAttribute("shoppingCart");
		//OrderEntity order = (OrderEntity) session.getAttribute("order");
		
		if (user == null) {
			
			return "redirect:/login";
		}
		
		if (shoppingCart == null) {
			
			return "redirect:/cart";
		}
		
		
		List<CartItemEntity> cartItems = iShoppingCartService.getCartItemsByShoppingCartId(shoppingCart.getId());
        
        
        // Tính tổng giá trị giỏ hàng
        long total = shoppingCart.getCartItems().stream()
                                 .mapToLong(item -> item.getUnitPrice() * item.getQuantity())
                                 .sum();
        
//        if (order == null) {
//			order = new OrderEntity();
//			order.setUser(user);
//			order.setShoppingCart(shoppingCart);
//			order.setOrderDate(Date.valueOf(LocalDate.now()));
//			order.setTotalAmount(total);
//			
//			iOrderService.save(order);
//		}
       
        
        
        OrderEntity order = new OrderEntity();
		order.setUser(user);
		order.setShoppingCart(shoppingCart);
		order.setOrderDate(Date.valueOf(LocalDate.now()));
		order.setTotalAmount(total);
		order.setTotalAmount(total);
		order.setShoppingCart(shoppingCart);
		iOrderService.save(order);
		
        
        
        
		if (order.getDiscount() != null) {
			model.addAttribute("discount", order.getDiscount().getDiscountPercentage());
			model.addAttribute("subtotal", total);
	        model.addAttribute("total", order.getTotalAmount());
	        model.addAttribute("cartItems", cartItems);
		}
		else {
			model.addAttribute("subtotal", total);
	        model.addAttribute("total", total);
	        model.addAttribute("cartItems", cartItems);
		}
        
		
		
		session.setAttribute("order", order);
        
		return "checkout";
	}
	
	
	@PostMapping("/add-orderdetail")
	public String saveOrder(@RequestParam("firstName") String firstName,
				            @RequestParam("lastName") String lastName,
				            @RequestParam("address") String address,
				            @RequestParam("phone") String phone,
				            @RequestParam("note") String note,
				            HttpSession session,
				            Model model) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		OrderEntity order = (OrderEntity) session.getAttribute("order");
		
		
		
		
		if (user == null) {
			return "redirect:/login";
		}

		OrderDetailEntity orderDetail = new OrderDetailEntity();
		orderDetail.setFirstname(firstName);
		orderDetail.setLastname(lastName);
		orderDetail.setAddress(address);
		orderDetail.setPhone(phone);
		orderDetail.setNote(note);
		orderDetail.setStatus("NEW");
		orderDetail.setOrder(order);
		
		iOrderDetailService.save(orderDetail);

		order.setOrderDetail(orderDetail);
		
		iOrderService.save(order);
		

		
		return "redirect:/shop";
	}
	
}
