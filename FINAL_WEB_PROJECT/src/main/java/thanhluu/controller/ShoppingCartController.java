package thanhluu.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import thanhluu.dto.CartUpdateDto;
import thanhluu.entity.CartItemEntity;
import thanhluu.entity.DiscountEntity;
import thanhluu.entity.OrderEntity;
import thanhluu.entity.ProductEntity;
import thanhluu.entity.ShoppingCartEntity;
import thanhluu.entity.UserDiscountEntity;
import thanhluu.entity.UserEntity;
import thanhluu.service.ICartItemService;
import thanhluu.service.IDiscountService;
import thanhluu.service.IOrderService;
import thanhluu.service.IProductService;
import thanhluu.service.IShoppingCartService;
import thanhluu.service.IUserDiscountService;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

	 	@Autowired
	    private IShoppingCartService shoppingCartService;

	 	@Autowired
	    private ICartItemService iCartItemService;
	 	
	    @Autowired
	    private IProductService productService;

	    @Autowired
	    private IDiscountService discountService;
	    
	    @Autowired
	    private IUserDiscountService iUserDiscountService;
	    
	    @Autowired
	    private IOrderService iOrderService;

	    @GetMapping
	    public String getShoppingCart(Model model,
	    							HttpSession session) {
	        // Giả sử bạn có userId từ session
	        UserEntity user = (UserEntity) session.getAttribute("user"); // Thay thế bằng session hoặc xác thực thực tế

	        if (user == null) {
	        	return "redirect:/login";
	        }
	        
	        Long userId = user.getId();
	        
	        // Lấy thông tin giỏ hàng của người dùng
	        Optional<ShoppingCartEntity> optionalCart = shoppingCartService.findByUserId(userId);
	        
	        if (optionalCart.isPresent()) {
	        	
	        	ShoppingCartEntity shoppingCart = optionalCart.get();
	        	
	        	session.setAttribute("shoppingCart", shoppingCart);
	        	
	        	List<CartItemEntity> cartItems = shoppingCart.getCartItems();

		        // Tính tổng giá và tổng số lượng
		        Long subtotal = cartItems.stream()
		                .mapToLong(item -> item.getProduct().getPrice() * item.getQuantity())
		                .sum();

		        // Thêm dữ liệu vào model
		        model.addAttribute("cartItems", cartItems);
		        model.addAttribute("subtotal", subtotal);
		        model.addAttribute("total", subtotal); // Nếu có thêm phí hoặc giảm giá, hãy tính ở đây.

		        return "shoping-cart"; // Tên file HTML
	        }
	        
	        return "redirect:/shop";
	    }

	    @PostMapping("/apply-discount")
	    public String applyDiscount(@RequestParam("couponCode") String couponCode, 
	    							HttpSession session,
	    							Model model) {
	    	
	    	UserEntity user = (UserEntity) session.getAttribute("user");
	    	ShoppingCartEntity shoppingCart = (ShoppingCartEntity) session.getAttribute("shoppingCart");
	    	
	    	if (user == null) {
	    		return "redirect:/login";
	    	}
	    	
	    	if (shoppingCart == null) {
	    		return "redirect:/cart";
	    	}
	        // Xử lý mã giảm giá
	        Optional<DiscountEntity> optionalDiscount = discountService.findByCode(couponCode);

	        if (optionalDiscount.isPresent()) {
	        	
	        	DiscountEntity discount = optionalDiscount.get();
	        	
	        	// Kiểm tra nếu mã giảm giá đã hết hạn
	            Date currentDate = new Date(System.currentTimeMillis());
	            if (currentDate.before(discount.getValidFrom()) || currentDate.after(discount.getValidUntil())) {
	                model.addAttribute("error", "Mã Đã Hết Hạn!");
	                return "redirect:/cart";
	            }
	            
	            // Áp dụng giảm giá
	            Long discountValue = (long) ((discount.getDiscountPercentage() / 100) * shoppingCartService.getSubTotal(user.getId()));
	            if (discount.getMax_value() != null) {
	                discountValue = Math.min(discountValue, discount.getMax_value());
	            }
	            Long total = shoppingCartService.getSubTotal(user.getId()) - discountValue;
	            
	            
	            
	            UserDiscountEntity userDiscount = iUserDiscountService.findByUserAndDiscount(user, discount);
	            
	            Optional<ShoppingCartEntity> optionalCart = shoppingCartService.findByUserId(user.getId());
	            
	            List<CartItemEntity> cartItems = optionalCart.get().getCartItems();

		        // Tính tổng giá và tổng số lượng
		        Long subtotal = cartItems.stream()
		                .mapToLong(item -> item.getProduct().getPrice() * item.getQuantity())
		                .sum();
		        
	            // Lưu trạng thái mã giảm giá đã sử dụng
	            if (userDiscount == null) {
	                userDiscount = new UserDiscountEntity();
	                userDiscount.setUser(user);
	                userDiscount.setDiscount(discount);
	            }
	            userDiscount.setUsed(true);
	            iUserDiscountService.save(userDiscount);

	            // Set mã giảm giá cho order và tạo order mới
	            OrderEntity order = new OrderEntity();
	            order.setDiscount(discount);
	            order.setTotalAmount(subtotal - discountValue);
	            order.setUser(user);
	            order.setOrderDate(java.sql.Date.valueOf(LocalDate.now()));
	            order.setShoppingCart(shoppingCart);
	            iOrderService.save(order);	  
	            
	            session.setAttribute("order", order);
	            
	            // Cập nhật model và giao diện
	            model.addAttribute("cartItems", cartItems);
		        model.addAttribute("subtotal", subtotal);
	            model.addAttribute("total", total);
	            model.addAttribute("success", "Coupon applied successfully!");
	            return "shoping-cart";
	        	 
	        }
	        

	        return "redirect:/cart";
	    }

	    @PostMapping("/add")
	    public String addToCart(@RequestParam("productId") Long productId,
	    						@RequestParam("quantity") int quantity,
	    						RedirectAttributes redirectAttributes,
	    						HttpSession session) {
	    	
	    	UserEntity user = (UserEntity) session.getAttribute("user");
	    	OrderEntity order = (OrderEntity) session.getAttribute("order");
	    	
	    	if (user == null) {
	    		return "redirect:/login";
	    	}
	    	
	    	if (order == null) {
            	
            	//Tạo order nếu 0 áp mã giảm giá
            	order = new OrderEntity();

	            order.setUser(user);
	            order.setOrderDate(java.sql.Date.valueOf(LocalDate.now()));
	            iOrderService.save(order);	 
            }
	    	
	    	// Lấy giỏ hàng từ session (hoặc từ cơ sở dữ liệu nếu cần)
	        Optional<ShoppingCartEntity> optionalShoppingCart = shoppingCartService.findByUserId(user.getId());
	        
	        if (!optionalShoppingCart.isPresent()) {
	            ShoppingCartEntity shoppingcart = new ShoppingCartEntity();
	            List<CartItemEntity> list_cartItem = new ArrayList<CartItemEntity>();
	            shoppingcart.setUser(user);
	            shoppingcart.setCartItems(list_cartItem);
	            shoppingCartService.save(shoppingcart);
	            shoppingCartService.addProductToCart(shoppingcart, productId, quantity);
	            
	            session.setAttribute("shoppingCart", shoppingcart); // Set giỏ hàng vào session nếu chưa có
	           

	            order.setShoppingCart(shoppingcart);
	            iOrderService.save(order);	
         
	            
	        }	
	        else {
	        	// Thêm sản phẩm vào giỏ hàng
	        	shoppingCartService.addProductToCart(optionalShoppingCart.get(), productId, quantity);
	        	
	        }
	        
	        session.setAttribute("order", order);
	        
	        return "redirect:/cart";
	  
	    }
	    
	    @GetMapping("/remove/{id}")
	    public String removeProductInCart(@PathVariable Long id,
	    									HttpSession session) {
	    	
	    	
	    	 // Lấy thông tin người dùng từ session
	        UserEntity user = (UserEntity) session.getAttribute("user");

	        // Kiểm tra nếu người dùng chưa đăng nhập
	        if (user == null) {
	            return "redirect:/login";
	        }

	       ShoppingCartEntity shoppingCart = (ShoppingCartEntity) session.getAttribute("shoppingCart");

	        // Kiểm tra nếu giỏ hàng không tồn tại
	        if (shoppingCart == null) {
	            return "redirect:/cart";
	        }

	       List<CartItemEntity> list_cartItem = iCartItemService.findByShoppingCart(shoppingCart); // cartItemService.findByShoppingCart

	       
	       
	       for (CartItemEntity cartItem : list_cartItem) {
	    	   	
	    	    if (cartItem.getProduct().getId().equals(id)) {
	    	    	
	    	    	
	    	    	
	    	    	Long cartId = cartItem.getId();
	    	    	
	    	    	iCartItemService.deleteById(cartId);
	    	    	System.out.println("Đây là đoạn if để xóa cartItem");
	    	    	break;
	    	    }
	    	}
		        

	        return "redirect:/cart";
	    }
	    
	    
	    @PostMapping("/update")
	    public String updateCart(@ModelAttribute("items") List<CartUpdateDto> items) {
	    	
	    	//ShoppingCartEntity shoppingCart = (ShoppingCartEntity) session.getAttribute("shoppingCart");
	    	
//			try {
//				// Lặp qua từng sản phẩm để cập nhật số lượng
//				for (CartUpdateDto item : items) {
//					shoppingCartService.updateCartItemQuantity(item.getId(), item.getQuantity());
//				}
//			} catch (Exception e) {
//				System.err.println("Error updating cart: " + e.getMessage());
//			}
//
//			// Quay lại trang giỏ hàng
//			return "redirect:/cart";
	    	try {
	            for (CartUpdateDto item : items) {
	                shoppingCartService.updateCartItemQuantity(item.getId(), item.getQuantity());
	            }
	        } catch (Exception e) {
	            System.err.println("Error updating cart: " + e.getMessage());
	        }
	        return "redirect:/cart";

	    }
	
}
