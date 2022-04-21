package testtask.banners.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.data.repository.BannerRepository;
import testtask.banners.data.repository.CategoryRepository;
import testtask.banners.exception.ResourceNotFoundException;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final BannerRepository bannerRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository, BannerRepository bannerRepository) {
    this.categoryRepository = categoryRepository;
    this.bannerRepository = bannerRepository;
  }

  public Category createCategory(Category category) {
    //Category c = new Category(category);
    categoryRepository.save(category);
    return category;
  }

  public Category getCategory(Long category_id) {
    Optional<Category> optionalCategory = Optional.ofNullable(categoryRepository
        .findCategoryByIdAndDeletedFalse(category_id)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", category_id)));
    return optionalCategory.get();
  }

  public Category getCategory(String name) {
    return categoryRepository.getCategoryByNameAndDeletedFalse(name);
  }

  public List<Category> getAllCategory() {
    return categoryRepository.findCategoriesByDeletedFalse();
  }

  public Category updateCategory(Category newCategory, Long id) {
    Category category = getCategory(id);
    category.setName(newCategory.getName());
    for (Banner banner : newCategory.getBanners()) {
      banner.addCategory(category);
      bannerRepository.save(banner);
    }
    categoryRepository.save(category);
    return category;
  }

  public void deleteCategory(Long id) {
    Category category = getCategory(id);
    category.setDeleted(true);
    categoryRepository.save(category);
  }

}
