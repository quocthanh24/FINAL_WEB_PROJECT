package thanhluu.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import thanhluu.entity.CartItemEntity;
import thanhluu.entity.CategoryEntity;
import thanhluu.service.ICartItemService;
import thanhluu.service.ICategoryService;

@Controller
public class TestController {
	
	@Autowired
	private ICategoryService categoryServiceImpl;
	

	@GetMapping("/test_cart")
	public String test(){
		
//		Pageable pageable = PageRequest.of(page - 1, size);
//
//		Page<CategoryEntity> categories = categoryServiceImpl.findAll(pageable);
//
//		model.addAttribute("categories", categories); // Danh sách sản phẩm
//		model.addAttribute("currentPage", page); // Trang hiện tại
//		model.addAttribute("totalPages", categories.getTotalPages()); // Tổng số trang
		
	
		
		return "shoping-cart";
	}
	
	@GetMapping("/check-out")
	public String test1(){
		

		
		return "checkout";
	}
	
	@GetMapping("/admin/shopping-cart")
	public String test2(){
		

		
		return "shopping-cart";
	}
}
