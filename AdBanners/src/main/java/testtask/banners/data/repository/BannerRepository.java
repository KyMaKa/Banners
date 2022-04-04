package testtask.banners.data.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import testtask.banners.data.models.Banner;

public interface BannerRepository extends CrudRepository<Banner, Long> {

  public Banner getBannerByIdAndDeletedFalse(Long id);

  public List<Banner> getBannersByDeletedFalse();

}
