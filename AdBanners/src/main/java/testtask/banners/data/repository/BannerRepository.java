package testtask.banners.data.repository;

import org.springframework.data.repository.CrudRepository;
import testtask.banners.data.models.Banner;

public interface BannerRepository extends CrudRepository<Banner, Long> {

}
