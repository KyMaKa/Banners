package testtask.banners.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.data.repository.BannerRepository;
import testtask.banners.data.repository.CategoryRepository;
import testtask.banners.exception.ResourceNotFoundException;

@Service
public class BannerService {

  public final BannerRepository bannerRepository;
  public final CategoryRepository categoryRepository;

  @Autowired
  public BannerService(BannerRepository bannerRepository,
      CategoryRepository categoryRepository) {
    this.bannerRepository = bannerRepository;
    this.categoryRepository = categoryRepository;
  }

  public Banner createBanner(Banner banner) {
    bannerRepository.save(banner);
    return banner;
  }

  public Banner getBanner(Long id) {
    return Optional.ofNullable(bannerRepository.getBannerByIdAndDeletedFalse(id))
        .orElseThrow(() -> new ResourceNotFoundException("Banner", "id:", id));
  }

  public List<Banner> getAllBanner() {
    return bannerRepository.getBannersByDeletedFalse();
  }

  public Banner updateBanner(Banner newBanner, Long id) {
    Banner banner = getBanner(id);
    banner.setName(newBanner.getName());
    banner.setText(newBanner.getText());
    banner.setPrice(newBanner.getPrice());
    banner.setDeleted(false);
    banner.setCategories(newBanner.getCategories());
    bannerRepository.save(banner);
    return banner;
  }

  public void deleteBanner(Long id) {
    Banner banner = getBanner(id);
    Set<Category> categories = banner.getCategories();
    for (Category c : categories) {
      c.removeBanner(banner);
      categoryRepository.save(c);
    }
    banner.setDeleted(true);
    bannerRepository.save(banner);
  }

  public Banner addCategory(Category category, Long id) {
    Banner banner = bannerRepository.getBannerByIdAndDeletedFalse(id);
    category.addBanner(banner);
    bannerRepository.save(banner);
    categoryRepository.save(category);
    return banner;

  }

  public Banner removeCategory(Category category, Long banner_id) {
    Banner banner = bannerRepository.getBannerByIdAndDeletedFalse(banner_id);
    category.removeBanner(banner);
    bannerRepository.save(banner);
    categoryRepository.save(category);
    return banner;
  }
}
