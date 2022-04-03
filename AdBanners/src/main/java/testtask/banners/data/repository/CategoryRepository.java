package testtask.banners.data.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import testtask.banners.data.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

  public List<Category> findCategoriesByDeletedFalse();

  public Optional<Category> findCategoryByIdAndDeletedFalse(Long id);

}
