package thanhluu.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import thanhluu.entity.CartItemEntity;
import thanhluu.entity.DiscountEntity;
import thanhluu.entity.OrderEntity;
import thanhluu.entity.ShoppingCartEntity;
import thanhluu.entity.UserDiscountEntity;
import thanhluu.entity.UserEntity;
import thanhluu.service.IOrderService;
import thanhluu.service.IShoppingCartService;
import thanhluu.service.IUserDiscountService;

@Controller
@RequestMapping("/checkout")
public class CheckOutController {

	@Autowired
	private IShoppingCartService iShoppingCartService;
	
	@Autowired
	private IUserDiscountService iUserDiscountService;
	
	@Autowired
	private IOrderService iOrderService;
	
	@GetMapping
	public String getOrder(Model model,
							HttpSession session) {
		
		UserEntity user = (UserEntity) session.getAttribute("user");
		ShoppingCartEntity shoppingCart = (ShoppingCartEntity) session.getAttribute("shoppingCart");
		OrderEntity order = (OrderEntity) session.getAttribute("order");
		
		if (user == null) {
			
			return "redirect:/login";
		}
		if (order == null) {
			return "redirect:/shop";
		}
		if (shoppingCart == null) {
			
			return "redirect:/cart";
		}
		
		
		List<CartItemEntity> cartItems = iShoppingCartService.getCartItemsByShoppingCartId(shoppingCart.getId());
        
        
        // Tính tổng giá trị giỏ hàng
        long total = shoppingCart.getCartItems().stream()
                                 .mapToLong(item -> item.getUnitPrice() * item.getQuantity())
                                 .sum();
		
        
        
       
        
        double discountAmount = 0;
        
//        OrderEntity order = new OrderEntity();
//        order.setOrderDate(Date.valueOf(LocalDate.now()));
//        order.setUser(user);
//        order.setShoppingCart(shoppingCart);
//        order.setTotalAmount(total);
        
        
//        iOrderService.save(order);
       
        model.addAttribute("total", total - discountAmount);
        model.addAttribute("cartItems", cartItems);
        
		return "checkout";
	}
}
