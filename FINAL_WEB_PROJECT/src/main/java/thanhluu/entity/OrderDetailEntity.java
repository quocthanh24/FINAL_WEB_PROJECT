package thanhluu.entity;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailEntity {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
	
	@OneToOne(mappedBy = "orderDetail")  
    private OrderEntity order;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "status")
	private String status;
}
