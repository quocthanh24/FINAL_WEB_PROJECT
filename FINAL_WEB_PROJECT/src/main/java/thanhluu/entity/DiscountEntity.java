package thanhluu.entity;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "discount_percentage")
    private double discountPercentage;
    
    @Column(name = "code", unique = true)
    private String code;
    
    @Column(name = "max_value")
    private Long max_value;
    
    @Column(name = "valid_from")
    private Date validFrom;
    
    @Column(name = "valid_until")
    private Date validUntil;
}
