package testtask.banners.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    return bannerRepository.getBannerByIdAndDeletedFalse(id);
  }

  public Banner getBanner(String name) {
    return bannerRepository.getBannerByNameAndDeletedFalse(name);
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
    Set<Category> categories = banner.getCategories();
//    for (Category category : categories) {
//      if (!newBanner.getCategories().contains(category)) {
//        category.removeBanner(banner);
//        categoryRepository.save(category);
//      }
//    }
    for (Category category : newBanner.getCategories()) {
      /*category.addBanner(banner);
      categoryRepository.save(category)*/;
      addCategory(category, id);
    }
    //banner.setCategories(newBanner.getCategories());
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
