package thanhluu.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shippers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
	@OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
	
    @Column(name = "name")
    private String name;
    
    @Column(name = "phone")
    private String phone;
}
