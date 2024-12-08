package thanhluu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDiscountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "discount_id", referencedColumnName = "id")
	private DiscountEntity discount;

	@Column(name = "used")
	private boolean used; // Trạng thái mã đã được sử dụng hay chưa
	
	

}
