package thanhluu.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "unit_price")
	private Long unitPrice;

	@ManyToOne
	@JoinColumn(name = "shopping_cart_id")
	private ShoppingCartEntity shoppingCart; // The shopping cart this item belongs to

	@ManyToOne
	@JoinColumn(name = "product_id")
	private ProductEntity product;
}
