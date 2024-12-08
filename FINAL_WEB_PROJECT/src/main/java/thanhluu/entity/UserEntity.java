package thanhluu.entity;

import java.time.LocalDateTime;


//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;
	
	@Column(name = "address")
	private String address;

	@Column(name = "role")
	private String role; // Admin, Customer, etc.

	@Column(name = "active")
	private boolean active; // Status of the user

	// Thuộc tính OTP
	@Column(name = "otp_code")
	private String otpCode; // OTP code gửi đến người dùng
	
	@Column(name = "otp_expiration_time")
    private LocalDateTime otpExpirationTime; // Thời gian hết hạn của OTP

    @Column(name = "otp_verified")
    private boolean otpVerified; // Trạng thái xác nhận OTP (đã xác thực hay chưa)

    // Mối quan hệ One-to-One với ShipperEntity
    @OneToOne(mappedBy = "user")
    private ShipperEntity shipper; // Tạo liên kết với ShipperEntity nếu người dùng là shipper
}
