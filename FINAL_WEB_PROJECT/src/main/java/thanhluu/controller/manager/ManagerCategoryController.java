package thanhluu.controller.manager;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thanhluu.entity.CategoryEntity;
import thanhluu.service.ICategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/manager/categories")
public class ManagerCategoryController {

	@Autowired
	private ICategoryService categoryServiceImpl;

	@GetMapping
	public String categories(Model model,
							@RequestParam(defaultValue = "1") int page, // Số trang (mặc định là 1)
							@RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page - 1, size);

		Page<CategoryEntity> categories = categoryServiceImpl.findAll(pageable);

		model.addAttribute("categories", categories); // Danh sách sản phẩm
		model.addAttribute("currentPage", page); // Trang hiện tại
		model.addAttribute("totalPages", categories.getTotalPages()); // Tổng số trang

		return "manager/category/manager-category-list";
	}

	@GetMapping("/add")
	public String addCategories() {
		return "/manager/category/manager-category-add";
	}

	@PostMapping("/add")
	public String addCategoriesResp(Model model, @RequestParam String name, @RequestParam String description) {

		CategoryEntity category = new CategoryEntity();
		category.setName(name);
		category.setDescription(description);

		categoryServiceImpl.save(category);

		return "redirect:/manager/categories";
	}

	@GetMapping("/edit/{id}")
	public String editCategories(@PathVariable Long id, Model model) {
		Optional<CategoryEntity> category = categoryServiceImpl.findById(id);

		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "/manager/category/manager-category-edit";
		}

		return "redirect:/manager/categories/add";

	}

	@PostMapping("/edit/{id}")
	public String editCategoriesResp(@PathVariable Long id, @ModelAttribute CategoryEntity category) {
		
		categoryServiceImpl.save(category);

		return "redirect:/manager/categories";

	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteCategories(@PathVariable Long id) {
		
		
		categoryServiceImpl.deleteById(id);
		
		return "redirect:/manager/categories";
	}
	
	
}
