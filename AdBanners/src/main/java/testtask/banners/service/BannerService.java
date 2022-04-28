package testtask.banners.service;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.data.repository.BannerRepository;
import testtask.banners.data.repository.CategoryRepository;

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

  /**
   * Simply creates new banner.
   * @param banner - banner to create.
   * @return created banner.
   */
  public Banner createBanner(Banner banner) {
    bannerRepository.save(banner);
    return banner;
  }

  /**
   * Get banner by id.
   * @param id - id of the banner.
   * @return - banner with given id.
   */
  public Banner getBanner(Long id) {
    return bannerRepository.getBannerByIdAndDeletedFalse(id);
  }

  public Banner getBanner(String name) {
    return bannerRepository.getBannerByNameAndDeletedFalse(name);
  }

  public List<Banner> getAllBanner() {
    return bannerRepository.getBannersByDeletedFalse();
  }

  /**
   * Update banner with given id.
   * @param newBanner - changed banner.
   * @param id - of the banner to update.
   * @return - updated banner.
   */
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

  /**
   * Deletes banner. Keeps it in DB, simply marks it as deleted.
   * Also removes linked categories.
   * @param id - of the banner to delete.
   */
  public void deleteBanner(Long id) {
    Banner banner = getBanner(id);
    Set<Category> categories = banner.getCategories();
    for (Category c : categories) {
      banner.removeCategory(c);
      categoryRepository.save(c);
    }
    banner.setDeleted(true);
    bannerRepository.save(banner);
  }

  public Banner addCategory(Category category, Long id) {
    Banner banner = bannerRepository.getBannerByIdAndDeletedFalse(id);
    banner.addCategory(category);
    bannerRepository.save(banner);
    categoryRepository.save(category);
    return banner;

  }

  public Banner removeCategory(Category category, Long banner_id) {
    Banner banner = bannerRepository.getBannerByIdAndDeletedFalse(banner_id);
    banner.removeCategory(category);
    bannerRepository.save(banner);
    categoryRepository.save(category);
    return banner;
  }
}
