package thanhluu.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "total_amount")
	private Long totalAmount;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

//	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL) // Cập nhật mối quan hệ
//    private OrderDetailEntity orderDetail;

	@OneToOne(cascade = CascadeType.ALL) // Chuyển sang OneToOne
    @JoinColumn(name = "shipper_id")
    private ShipperEntity shipper;
	
	@OneToOne(cascade = CascadeType.ALL) // Chuyển sang OneToOne
    @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

	@ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCartEntity shoppingCart;
	
	
}
