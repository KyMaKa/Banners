package testtask.banners.controllers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.data.models.Log;
import testtask.banners.service.BannerService;
import testtask.banners.service.CategoryService;
import testtask.banners.service.LogService;


@RestController
@RequestMapping(path = "/bid")
public class BidController {

  private final BannerService bannerService;

  private final LogService logService;
  private final CategoryService categoryService;

  @Autowired
  public BidController(BannerService bannerService,
      LogService logService, CategoryService categoryService) {
    this.bannerService = bannerService;
    this.logService = logService;
    this.categoryService = categoryService;
  }

  private void createLog(Log log, Banner banner, String ipAddress, String userAgent) {
    if (banner == null) {
      log.setBannerId(null);
      log.setBannerPrice(null);
    } else {
      log.setBannerId(banner.getId());
      log.setBannerPrice(banner.getPrice());
    }
    log.setDate(LocalDateTime.now());
    log.setIpAddress(ipAddress);
    log.setUserAgent(userAgent);
    logService.createLog(log);
  }

  private Banner findBanner(List<Banner> banners, List<Log> logs) {
    Banner banner = banners.get(0);
    if (logs.isEmpty()) {
      return banner;
    }
    for (Banner b : banners) {
      banner = b;
      for (Log l : logs) {
        if (l.getBannerId() != null) {
          if (l.getBannerId().equals(b.getId())) {
            System.out.println(l.getBannerId());
            banner = null;
            break;
          }
        }
      }
      if (banner != null) {
        return banner;
      }
    }
    return null;
  }

  @GetMapping
  public ResponseEntity<?> getBannerText(@RequestParam("cat") List<String> categories,
      HttpServletRequest request) {

    Log log = new Log();
    String cats = "";
    Set<Category> set = new HashSet<>();
    for (String s : categories) {
      Category c = categoryService.getCategory(s);
      cats = cats.concat(c.getId() + " ");
      set.add(c);
    }
    log.setCategoriesId(cats);

    String userAgent = request.getHeader("User-Agent");
    String ipAddress = request.getRemoteAddr();

    List<Banner> banners = bannerService.bannerRepository.getBannersByDeletedFalseAndCategoriesInOrderByPriceDesc(
        set);

    LocalDateTime currentDate = LocalDateTime.now();
    LocalDateTime pastDate = currentDate.minusHours(24);
    List<Log> logs = logService.getLogsForDay(userAgent, ipAddress, pastDate, currentDate);

    if (banners.isEmpty()) {
      log.setNoContent("No banners for given categories.");
      createLog(log, null, ipAddress, userAgent);
      return new ResponseEntity<>("No content.", HttpStatus.NO_CONTENT);
    }

    Banner banner = findBanner(banners, logs);

    if (banner != null) {
      createLog(log, banner, ipAddress, userAgent);
      return new ResponseEntity<>(banner.getText(), HttpStatus.OK);
    }

    log.setNoContent("All banners for given categories is shown.");
    createLog(log, null, ipAddress, userAgent);
    return new ResponseEntity<>("No content.", HttpStatus.NO_CONTENT);
  }
}
