package thanhluu.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import thanhluu.entity.ProductEntity;
import thanhluu.entity.ProductImageEntity;
import thanhluu.entity.RatingEntity;
import thanhluu.service.Impl.CategoryServiceImpl;
import thanhluu.service.Impl.ProductImageServiceImpl;
import thanhluu.service.Impl.ProductServiceImpl;
import thanhluu.service.Impl.RatingServiceImpl;

@Controller
@RequestMapping("/product/detail")
public class ProductDetailController {

	
	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	@Autowired
	private ProductImageServiceImpl imageServiceImpl;
	
	
	@Autowired
	private CategoryServiceImpl categoryServiceImpl;
	
	@Autowired
	private RatingServiceImpl ratingServiceImpl;
		
	
	
	
	@GetMapping("/{id}")
	public String getProductDetail(@PathVariable Long id, Model model) {
		
		ProductEntity product = productServiceImpl.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

		 Iterable<RatingEntity> ratings = ratingServiceImpl.findByProductId(id);
		 
	        // Tính toán điểm rating trung bình (optional)
		 double averageRating = StreamSupport.stream(ratings.spliterator(), false)
		            .mapToInt(RatingEntity::getRating)
		            .average()
		            .orElse(0.0);

	        // Thêm sản phẩm, đánh giá và điểm trung bình vào model
	        model.addAttribute("product", product);
	        model.addAttribute("ratings", ratings);
	        model.addAttribute("averageRating", averageRating);

	        // Trả về view sản phẩm chi tiết
	        return "shop-details";	
		
		
	}
}
