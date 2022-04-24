package testtask.banners.service;

import java.util.Iterator;
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
    /*Set<Category> oldCategories = banner.getCategories();
    Set<Category> newCategories = newBanner.getCategories();
    Iterator<Category> categoryIterator = banner.getCategories().iterator();
    Banner b = banner;
    while (categoryIterator.hasNext()) {
      Category category = categoryIterator.next();
      if (!newCategories.contains(category)) {
        categoryIterator.remove();
        //categoryRepository.save(category);
      }
    }
    for (Category category : newBanner.getCategories()) {
      banner.getCategories().add(category);
      //categoryRepository.save(category);
    }*/
    //I HONESTLY DON'T KNOW WHY THIS IS WORKING. PROBABLY 'mappedBy' IS
    //DOING SOME BLACK MAGIC.
    //I can't understand how to safely (without ConcurrentModificationException)
    //remove categories from banner entity. Method below works fine, but im concern
    //that this is a bad way of updating.
    banner.setCategories(newBanner.getCategories());
    bannerRepository.save(banner);
    return banner;
  }

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
