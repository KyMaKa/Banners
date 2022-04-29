package testtask.banners.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import testtask.banners.data.modelAssemblers.BannerModelAssembler;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.service.BannerService;

@RestController
@RequestMapping(path = "/banners")
public class BannerController {

  private final BannerService bannerService;
  private final BannerModelAssembler assembler;

  @Autowired
  public BannerController(BannerService bannerService,
      BannerModelAssembler assembler) {
    this.bannerService = bannerService;
    this.assembler = assembler;
  }

  /**
   * Gets single banner with given id. Not used in client.
   * @param id - of banner to get.
   * @return - banner with given id.
   */
  @GetMapping(path = "/{id}")
  public @ResponseBody
  EntityModel<Banner> getBanner(@PathVariable(name = "id") Long id) {
    Banner banner = bannerService.getBanner(id);
    return assembler.toModel(banner);
  }

  /**
   * Get all banners.
   * @return - list of banners.
   */
  @GetMapping(path = "/all")
  public @ResponseBody
  CollectionModel<EntityModel<Banner>> getAllBanner() {
    List<Banner> banners = bannerService.getAllBanner();
    List<EntityModel<Banner>> b = banners.stream().map(assembler::toModel).toList();
    return CollectionModel.of(b,
        linkTo(methodOn(BannerController.class).getAllBanner()).withSelfRel());
  }

  /**
   * Adds a new banner entity to DB.
   * @param banner - entity to add.
   * @return - added entity, Bad Request if name is empty, Conflict id banner with
   * given name already exists.
   */
  @PostMapping()
  public ResponseEntity<?> addBanner(@RequestBody Banner banner) {
    if (banner.getName().isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
          .body(Problem.create()
              .withTitle("Bad request")
              .withDetail("Banner name can't be empty."));
    }

    if (bannerService.getBanner(banner.getName()) == null) {
      EntityModel<Banner> entityModel = assembler.toModel(bannerService.createBanner(banner));

      return ResponseEntity
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
          .body(entityModel);
    }

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create()
            .withTitle("Conflict")
            .withDetail("Banner with name " + banner.getName() + " already exist."));
  }

  /**
   * Updates existing banner.
   * @param newBanner - new entity with changed fields.
   * @param id - of banner to update.
   * @return - updated banner, Bad Request if name is empty, Conflict if new name
   * is already taken by different banner.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateBanner(@RequestBody Banner newBanner,
      @PathVariable("id") Long id) {
    if (newBanner.getName().isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
          .body(Problem.create()
              .withTitle("Bad request")
              .withDetail("Banner name can't be empty."));
    }

    if (bannerService.getBanner(newBanner.getName()) == null
        || bannerService.getBanner(newBanner.getName()).getId().equals(id)) {
      Banner updatedBanner = bannerService.updateBanner(newBanner, id);
      EntityModel<Banner> entityModel = assembler.toModel(updatedBanner);
      return ResponseEntity
          .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
          .body(entityModel);
    }

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create()
            .withTitle("Conflict")
            .withDetail("Banner with name " + newBanner.getName() + " already exist."));
  }

  /**
   * Adds category to banner. Not used by client. Can be deleted.
   * @param category - entity to add.
   * @param id - of banner.
   * @return - updated banner.
   */
  @PutMapping(path = "/add/{id}")
  public ResponseEntity<?> addCategoryToBanner(@RequestBody Category category,
      @PathVariable("id") Long id) {
    Banner banner = bannerService.addCategory(category, id);
    EntityModel<Banner> entityModel = assembler.toModel(banner);
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  /**
   * Removes category from banner. Not used by client. Can be deleted.
   * @param category - to remove.
   * @param id - of banner.
   * @return - updated banner.
   */
  @PutMapping(path = "/remove/{id}")
  public ResponseEntity<?> removeCategoryFromBanner(@RequestBody Category category,
      @PathVariable("id") Long id) {
    Banner banner = bannerService.removeCategory(category, id);
    EntityModel<Banner> entityModel = assembler.toModel(banner);
    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  /**
   * Marks banner as deleted in DB.
   * @param id - of banner.
   * @return - no content.
   */
  @DeleteMapping(path = "{id}")
  public ResponseEntity<?> deleteBanner(@PathVariable(name = "id") Long id) {
    bannerService.deleteBanner(id);

    return ResponseEntity.noContent().build();
  }

}