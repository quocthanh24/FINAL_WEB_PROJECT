package thanhluu.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	


    // Liên kết với User (Người đánh giá)
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    // Liên kết với Product (Sản phẩm được đánh giá)
    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductEntity product;

    // Đánh giá sao
    Integer rating;  // Ví dụ: 1-5 sao

    // Bình luận của người dùng
    String comment;
}
