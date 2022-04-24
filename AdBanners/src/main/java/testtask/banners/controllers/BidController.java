package testtask.banners.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

  private Banner findBanner(List<Banner> banners, Cookie[] cookies) {
    Banner banner = banners.get(0);
    if (cookies == null)
      return banner;
    for (Banner b : banners) {
      banner = b;
      for (Cookie c : cookies) {
        if (b.getId().toString().equals(c.getValue())) {
          banner = null;
          break;
        }
      }
    }
    return banner;
  }

  @GetMapping
  public ResponseEntity<?> getBannerText(@RequestParam("cat") List<String> categories,
      HttpServletRequest request
      , HttpServletResponse response
      , Model model) {

    Set<Category> set = new HashSet<>();
    for (String s : categories) {
      Category c = categoryService.getCategory(s);
      set.add(c);
    }

    Cookie[] cookies = request.getCookies();

    List<Banner> banners = bannerService.bannerRepository.getBannersByDeletedFalseAndCategoriesInOrderByPriceDesc(
        set);
    if (banners.isEmpty()) {
      //model.addAttribute("name", "No banners for given categories.");
      /*return ResponseEntity
          .status(HttpStatus.NO_CONTENT)
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
          .body(Problem.create()
              .withTitle("No content.")
              .withDetail("No banners for given categories."));*/
      return new ResponseEntity<>("No content.", HttpStatus.NO_CONTENT);
    }
    //find not shown banner
    Banner banner = findBanner(banners, cookies);
//    String address = request.getRemoteAddr();
//    //System.out.println(request.getRequestURI());
//
//    //System.out.println(address);
//    if ("0:0:0:0:0:0:0:1".equals(address)) {
//      InetAddress inetAddress = null;
//      try {
//        inetAddress = InetAddress.getLocalHost();
//      } catch (UnknownHostException e) {
//        throw new RuntimeException(e);
//      }
//      //System.out.println(inetAddress.getHostAddress());
//    }

    //String userAgent = request.getHeader("User-Agent");
    //System.out.println(userAgent);

    if (cookies != null)
      for (Cookie c : cookies) {
        response.addCookie(c);
      }
    if (banner != null) {
      Cookie cookie = new Cookie("BannerId" + banner.getId().toString(), banner.getId().toString());
      cookie.setMaxAge(24 * 60 * 60);
      cookie.setHttpOnly(true);
      response.addCookie(cookie);
      return new ResponseEntity<>(banner.getText(), HttpStatus.OK);
    }
    //model.addAttribute("name", "No banners for given categories.");
    return new ResponseEntity<>("No content.", HttpStatus.NO_CONTENT);
  }
}
