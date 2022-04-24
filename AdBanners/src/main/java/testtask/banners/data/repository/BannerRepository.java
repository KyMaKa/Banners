package testtask.banners.data.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;

public interface BannerRepository extends CrudRepository<Banner, Long> {

  Banner getBannerByIdAndDeletedFalse(Long id);

  List<Banner> getBannersByDeletedFalse();

  Banner getBannerByNameAndDeletedFalse(String name);

  List<Banner> getBannersByDeletedFalseAndCategoriesIn(Set<Category> categories);

  List<Banner> getBannersByNameContains(String name);

}
