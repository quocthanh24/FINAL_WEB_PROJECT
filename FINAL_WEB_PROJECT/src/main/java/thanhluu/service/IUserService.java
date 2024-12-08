package thanhluu.service;

import java.util.Optional;

import thanhluu.entity.UserEntity;

public interface IUserService {
	
	
	public Optional<UserEntity> findById(Long id);
	
	public Optional<UserEntity> findByEmail(String email);
	
	public Optional<UserEntity> findByOtpCode(String otpCode);
	
	public UserEntity save(UserEntity user);
	
	
	
	public void sendOtp(String email, String otpCode);
	
	public void sendPasswordResetLink(String email, String otpCode);
	
	public String generateOTP();
}
