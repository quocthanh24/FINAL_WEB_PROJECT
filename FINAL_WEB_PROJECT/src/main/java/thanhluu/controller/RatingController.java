package thanhluu.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import thanhluu.entity.ProductEntity;
import thanhluu.entity.RatingEntity;
import thanhluu.entity.UserEntity;
import thanhluu.service.IProductService;
import thanhluu.service.IRatingService;

@Controller
@RequestMapping("/product")
public class RatingController {

	
	@Autowired
    private IProductService productService;

    @Autowired
    private IRatingService ratingService;

    @PostMapping("/rate/{id}")
    public String addRating(@PathVariable Long id, 
                            @RequestParam("rating") int rating, 
                            @RequestParam("comment") String comment, 
                            HttpSession session, 
                            Model model) {
        
        // Lấy người dùng hiện tại từ session
        UserEntity user = (UserEntity) session.getAttribute("user");
        
        // Kiểm tra người dùng có đăng nhập không
        if (user == null) {
            model.addAttribute("message", "Bạn cần đăng nhập để đánh giá sản phẩm.");
            return "redirect:/login";
        }

        // Tìm sản phẩm từ ID
        Optional<ProductEntity> productOpt = productService.findById(id);
        if (productOpt.isPresent()) {
            ProductEntity product = productOpt.get();
            
            // Tạo RatingEntity mới
            Optional<RatingEntity> optionalRating = ratingService.findByUser(user);
            
            if (optionalRating.isPresent()) {
            	RatingEntity existRating = optionalRating.get();
            	existRating.setRating(rating);
            	existRating.setComment(comment);
            	existRating.setProduct(product);
            	existRating.setUser(user);
            	// Lưu Rating vào cơ sở dữ liệu
                ratingService.save(existRating);
            }
            else {
            	RatingEntity newRating = new RatingEntity();
            	newRating.setRating(rating);
            	newRating.setComment(comment);
            	newRating.setProduct(product);
            	newRating.setUser(user);
            	// Lưu Rating vào cơ sở dữ liệu
                ratingService.save(newRating);
            }
            

            

            model.addAttribute("message", "Cảm ơn bạn đã đánh giá sản phẩm!");
            return "redirect:/product/detail/" + id;  // Trở lại trang chi tiết sản phẩm
        } else {
            model.addAttribute("message", "Sản phẩm không tồn tại.");
            return "redirect:/shop";  // Nếu sản phẩm không tồn tại, quay lại trang shop
        }
    }
}
