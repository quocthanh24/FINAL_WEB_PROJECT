package thanhluu.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "price", nullable = false)
	private Long price;
	
	@Column(name = "quantity", nullable = false)
	private int quantity = 0;

	// Thêm quan hệ OneToMany với ProductImageEntity
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductImageEntity> productImages; // Danh sách ảnh của sản phẩm

	@Column(name = "description")
	private String description;

	@Column(name = "available")
	private boolean available;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity category;
	
	
}
