package thanhluu.controller.manager;

import java.util.List;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import thanhluu.entity.CategoryEntity;
import thanhluu.entity.ProductEntity;
import thanhluu.entity.ProductImageEntity;
import thanhluu.service.ICategoryService;
import thanhluu.service.IProductImageService;
import thanhluu.service.IProductService;

@Controller
@RequestMapping("/manager/products")
public class ManagerProductController {
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IProductImageService imageService;
	
	 // Hiển thị danh sách các mã giảm giá
    @GetMapping
    public String listProducts(Model model,
					    		@RequestParam(defaultValue = "1") int page, // Số trang (mặc định là 1)
								@RequestParam(defaultValue = "5") int size) {
    	Pageable pageable = PageRequest.of(page - 1, size);

		Page<ProductEntity> products =  productService.findAll(pageable);

		model.addAttribute("products", products); // Danh sách sản phẩm
		model.addAttribute("currentPage", page); // Trang hiện tại
		model.addAttribute("totalPages", products.getTotalPages()); // Tổng số trang

		return "manager/product/manager-product-list";
    	
    	
    }

    // Hiển thị form thêm mã giảm giá
    @GetMapping("/add")
    public String showAddForm(Model model) {

    	model.addAttribute("product", new ProductEntity());
    	model.addAttribute("categories", categoryService.findAll());
    	
        return "manager/product/manager-product-add";
    }

    // Xử lý thêm mã giảm giá
    @PostMapping("/add")
    public String addProduct(@ModelAttribute ProductEntity product,                              
                             @RequestParam(value = "imageUrls[]", required = false) List<String> imageUrls) {

    	// Lưu sản phẩm vào cơ sở dữ liệu
        productService.save(product);
        
        
        // Xử lý và lưu ảnh sản phẩm
        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                ProductImageEntity productImage = new ProductImageEntity();
                productImage.setImageUrl(imageUrl);
                productImage.setProduct(product);  // Gán sản phẩm cho ảnh
                
                // Lưu ảnh vào database thông qua service
                imageService.save(productImage);
            }
        }

        

        // Sau khi lưu xong, redirect đến trang danh sách sản phẩm
        return "redirect:/manager/products";
    }

    // Hiển thị form sửa mã giảm giá
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
    	
        Optional<ProductEntity> product = productService.findById(id);
        if (product.isPresent()) {
        	
        	model.addAttribute("categories", categoryService.findAll());	
        	model.addAttribute("product", product.get());
            return "/manager/product/manager-product-edit";
        }

        return "redirect:/manager/products";
    }

    // Xử lý sửa mã giảm giá
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, 
                              @ModelAttribute ProductEntity product,
                              @RequestParam(value = "available", defaultValue = "false") String availableValue,
                              @RequestParam(value = "imageUrls", required = false) List<String> imageUrls) {

        // Kiểm tra xem sản phẩm có tồn tại hay không
        Optional<ProductEntity> optionalProduct = productService.findById(id);
        if (optionalProduct.isEmpty()) {
            return "redirect:/manager/products"; // Nếu không tìm thấy, quay về trang danh sách
        }

        ProductEntity existProduct = optionalProduct.get();

        // Cập nhật thông tin sản phẩm
        existProduct.setName(product.getName());
        existProduct.setPrice(product.getPrice());
        existProduct.setQuantity(product.getQuantity());
        existProduct.setDescription(product.getDescription());
        existProduct.setAvailable("true".equalsIgnoreCase(availableValue));

        // Cập nhật danh sách hình ảnh
        if (imageUrls != null) {
            List<ProductImageEntity> existingImages = existProduct.getProductImages();

            // Cập nhật hoặc thêm mới hình ảnh
            for (int i = 0; i < imageUrls.size(); i++) {
                if (i < existingImages.size()) {
                    // Cập nhật hình ảnh cũ
                    ProductImageEntity existingImage = existingImages.get(i);
                    existingImage.setImageUrl(imageUrls.get(i));
                    existingImage.setProduct(existProduct);
                    imageService.save(existingImage);
                } else {
                    // Thêm hình ảnh mới
                    ProductImageEntity newImage = new ProductImageEntity(null, imageUrls.get(i), existProduct);
                    existingImages.add(newImage);
                    imageService.save(newImage);
                }
            }

            // Xóa hình ảnh dư thừa nếu danh sách cũ nhiều hơn danh sách mới
            if (existingImages.size() > imageUrls.size()) {
                for (int i = imageUrls.size(); i < existingImages.size(); i++) {
                    Long image_id = existingImages.get(i).getId();
                    imageService.deleteById(image_id);
                }
                existingImages.subList(imageUrls.size(), existingImages.size()).clear();
            }

            existProduct.setProductImages(existingImages);
        }

        // Lưu sản phẩm
        productService.save(existProduct);

        return "redirect:/manager/products";
    }
    
    @GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		// Tìm sản phẩm theo id
	    Optional<ProductEntity> optionalProduct = productService.findById(id);

	    // Kiểm tra nếu sản phẩm không tồn tại
	    if (!optionalProduct.isPresent()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại");
	        return "redirect:/manager/products";
	    }
	    
	    productService.deleteById(id);
	    

	    // Quay lại trang quản lý sản phẩm
	    return "redirect:/manager/products";
	}

	
}
