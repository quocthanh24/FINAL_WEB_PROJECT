package thanhluu.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import thanhluu.entity.ProductEntity;
import thanhluu.entity.UserEntity;
import thanhluu.entity.WishListEntity;
import thanhluu.service.IProductService;
import thanhluu.service.IUserService;
import thanhluu.service.IWishListService;

@Controller
@RequestMapping("/wishlist")
public class WishListController {

	
	@Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IWishListService wishListService;
    
    
    @GetMapping
    public String getWishListForm(HttpSession session, Model model) {
    	 // Lấy user hiện tại từ session
        UserEntity user = (UserEntity) session.getAttribute("user");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (user == null) {
            model.addAttribute("message", "Bạn cần đăng nhập để xem Wishlist.");
            return "redirect:/login";  // Điều hướng người dùng đến trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy wishlist của người dùng
        WishListEntity wishlist = wishListService.findByUser(user);
        
        if (wishlist != null) {
            // Nếu wishlist tồn tại, thêm danh sách sản phẩm vào model
            model.addAttribute("products", wishlist.getProducts());
            model.addAttribute("wishlist", wishlist);
        } else {
            // Nếu không có wishlist, thông báo cho người dùng
        	
            model.addAttribute("message", "Wishlist của bạn hiện tại trống.");
        }

        // Trả về view để hiển thị wishlist
        return "wishlist"; 
    }
    
    @PostMapping("/add")
    public String getWishList(@RequestParam("productId") Long id,
    							HttpSession session,
    							Model model) {
    	
    	
    	UserEntity user = (UserEntity) session.getAttribute("user"); // Lấy user hiện tại (có thể thông qua session hoặc authentication)
        
    	
        if (user == null) {
            model.addAttribute("message", "Bạn cần đăng nhập để thêm sản phẩm vào Wishlist.");
            return "redirect:/login";  // Điều hướng người dùng đến trang đăng nhập nếu chưa đăng nhập
        }
        
        Optional<ProductEntity> optionalProduct = productService.findById(id);
        
        if (optionalProduct != null) {
            // Kiểm tra xem wishlist của user đã tồn tại chưa, nếu chưa tạo mới
            WishListEntity wishlist = wishListService.findByUser(user);
            if (wishlist == null) {
                wishlist = new WishListEntity();
                wishlist.setUser(user);
                wishlist.setProducts(new ArrayList<>());
            }
            
         // Kiểm tra xem sản phẩm đã có trong wishlist chưa
            boolean isProductInWishlist = wishlist.getProducts().stream()
                    .anyMatch(product -> product.getId().equals(id));
           
            if (isProductInWishlist) {
            	 model.addAttribute("message", "Sản phẩm này đã có trong Wishlist.");
            	 return "redirect:/shop";
            }
            else {
	            // Thêm sản phẩm vào wishlist
	            wishlist.getProducts().add(optionalProduct.get());
	            wishListService.save(wishlist);
	
	            model.addAttribute("message", "Sản phẩm đã được thêm vào Wishlist!");
            }
        } else {
            model.addAttribute("message", "Sản phẩm không tồn tại.");
        }

        return "redirect:/shop";
    	
    	
    }
    
    
    @PostMapping("/delete/{id}/{wishlistId}")
    public String deleteItemWishList(@PathVariable Long id,
    								@PathVariable Long wishlistId,
    								Model model) {
    	
    	 try {
             // Xóa sản phẩm khỏi wishlist
             wishListService.deleteProductFromWishList(wishlistId, id);

             // Thêm thông báo thành công
             model.addAttribute("successMessage", "Sản phẩm đã được xóa khỏi danh sách yêu thích.");
         } catch (IllegalArgumentException e) {
             // Thêm thông báo lỗi
             model.addAttribute("errorMessage", e.getMessage());
         }
        return "redirect:/wishlist";
    }
    
}
