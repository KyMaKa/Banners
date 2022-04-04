package testtask.banners.service;

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
    banner.setBanner_name(newBanner.getBanner_name());
    banner.setBanner_text(newBanner.getBanner_text());
    banner.setPrice(newBanner.getPrice());
    banner.setDeleted(false);
    banner.setCategories(newBanner.getCategories());
    bannerRepository.save(banner);
    return banner;
  }

  public void deleteBanner(Long id) {
    Banner banner = getBanner(id);
    banner.setDeleted(true);
    bannerRepository.save(banner);
  }

  public Banner addCategory(Category category, Banner banner) {
    category.addBanner(banner);
    bannerRepository.save(banner);
    categoryRepository.save(category);
    return banner;
  }

  public Banner removeCategory(Category category, Banner banner) {
    category.removeBanner(banner);
    bannerRepository.save(banner);
    categoryRepository.save(category);
    return banner;
  }
}
