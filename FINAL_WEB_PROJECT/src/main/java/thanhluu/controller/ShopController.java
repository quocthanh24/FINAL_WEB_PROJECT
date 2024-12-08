package thanhluu.controller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thanhluu.entity.ProductEntity;
import thanhluu.service.ICategoryService;
import thanhluu.service.IProductImageService;
import thanhluu.service.IProductService;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private IProductImageService iProductImageService;
	
	@Autowired
	private IProductService iProductService;
	
	@Autowired
	private ICategoryService iCategoryService;
	
	
	@GetMapping
	public String getAllProduct(@RequestParam(defaultValue = "0") int page, Model model) {
		
		Page<ProductEntity> productPage = iProductService.findAll(PageRequest.of(page, 9));
		
		
		
		model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
		 
	     return "shop-grid";
	}
	
	
	
	
}
