package thanhluu.entity;

import java.util.List;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;



@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "name", nullable = false, columnDefinition = "NVARCHAR(255)")
	String name; // Tên danh mục

	@Column(name = "description", columnDefinition = "NVARCHAR(1000)")
	String description; // Mô tả danh mục

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	List<ProductEntity> products; // Các sản phẩm thuộc danh mục này
}
