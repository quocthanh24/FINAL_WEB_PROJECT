package thanhluu.dto;



public class CartUpdateDto {

	 private Long id;  // ID của sản phẩm
	 private int quantity;
	 
	 // Getter và Setter
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}					
}
