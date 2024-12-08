package thanhluu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thanhluu.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByOtpCode(String otpCode);
}
