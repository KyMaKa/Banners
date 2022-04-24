package testtask.banners.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testtask.banners.data.modelAssemblers.BannerModelAssembler;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.service.BannerService;
import testtask.banners.service.CategoryService;


@RestController
@RequestMapping(path = "/bid")
public class BidController {

  private final BannerService bannerService;

  private final CategoryService categoryService;

  @Autowired
  public BidController(BannerService bannerService,
      CategoryService categoryService) {
    this.bannerService = bannerService;
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<String> getBannerText(@RequestParam("cat") List<String> categories) {
    System.out.println("Im here");
    Set<Category> set = new HashSet<>();
    for (String s : categories) {
      Category c = categoryService.getCategory(s);
      set.add(c);
    }
    System.out.println("Im here");
    List<Banner> banners = bannerService.bannerRepository.getBannersByDeletedFalseAndCategoriesIn(
        set);
    System.out.println(banners);
    return new ResponseEntity<>(banners.get(0).getText(), HttpStatus.OK);
  }

}
