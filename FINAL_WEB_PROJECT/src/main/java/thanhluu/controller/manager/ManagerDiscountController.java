package thanhluu.controller.manager;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import thanhluu.entity.DiscountEntity;
import thanhluu.service.IDiscountService;

@Controller
@RequestMapping("/manager/discounts")
public class ManagerDiscountController {
	
	@Autowired
	private IDiscountService discountService;
	
	 // Hiển thị danh sách các mã giảm giá
    @GetMapping
    public String listDiscounts(Model model,
					    		@RequestParam(defaultValue = "1") int page, // Số trang (mặc định là 1)
								@RequestParam(defaultValue = "5") int size) {
    	Pageable pageable = PageRequest.of(page - 1, size);

		Page<DiscountEntity> discounts =  discountService.findAll(pageable);

		model.addAttribute("discounts", discounts); // Danh sách sản phẩm
		model.addAttribute("currentPage", page); // Trang hiện tại
		model.addAttribute("totalPages", discounts.getTotalPages()); // Tổng số trang

		return "manager/discount/manager-discount-list";
    	
    	
    }

    // Hiển thị form thêm mã giảm giá
    @GetMapping("/add")
    public String showAddForm() {
       
        return "manager/discount/manager-discount-add";
    }

    // Xử lý thêm mã giảm giá
    @PostMapping("/add")
    public String addDiscount(@ModelAttribute DiscountEntity discount,
    							Model model) {
    	
    	if (discount.getValidFrom().after(discount.getValidUntil())) {
    		
    		model.addAttribute("error","Lỗi: Ngày phát hành lớn hơn ngày hết hạn");
    		
    		return "redirect:/manager/discounts/add";
    	}
    	
        discountService.save(discount);
        return "redirect:/manager/discounts";
    }

    // Hiển thị form sửa mã giảm giá
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
    	
        Optional<DiscountEntity> discount = discountService.findById(id);
        if (discount.isPresent()) {
        	model.addAttribute("discount", discount.get());
            return "/manager/discount/manager-discount-edit";
        }

        return "redirect:/manager/discounts";
    }

    // Xử lý sửa mã giảm giá
    @PostMapping("/edit/{id}")
    public String editDiscount(@PathVariable Long id, @ModelAttribute DiscountEntity discount) {
    	
        discountService.save(discount);
        return "redirect:/manager/discounts";
    }

    // Xử lý xóa mã giảm giá
    @GetMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable Long id) {
        discountService.deleteById(id);
        return "redirect:/manager/discounts";
    }
	
}
