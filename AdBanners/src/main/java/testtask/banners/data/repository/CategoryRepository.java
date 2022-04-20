package testtask.banners.data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import testtask.banners.data.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
  List<Category> findCategoriesByDeletedFalse();
  Optional<Category> findCategoryByIdAndDeletedFalse(Long id);

  Category getCategoriesByNameAndDeletedFalse(String name);

}
