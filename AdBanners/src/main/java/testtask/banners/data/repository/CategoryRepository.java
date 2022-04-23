package testtask.banners.data.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import testtask.banners.data.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
  List<Category> findCategoriesByDeletedFalse();
  Category findCategoryByIdAndDeletedFalse(Long id);
  Category getCategoryByNameAndDeletedFalse(String name);

}
