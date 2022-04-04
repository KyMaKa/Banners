package testtask.banners.data.modelAssemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.web.BannerController;
import testtask.banners.web.CategoryController;

@Component
public class BannerModelAssembler implements
    RepresentationModelAssembler<Banner, EntityModel<Banner>> {


  @Override
  public EntityModel<Banner> toModel(Banner banner) {
    EntityModel<Banner> bannerEntityModel = EntityModel.of(banner,
        linkTo(methodOn(BannerController.class).getBanner(banner.getId())).withSelfRel(),
        linkTo(methodOn(BannerController.class).getAllBanner()).withRel("banners"));
      bannerEntityModel.add(linkTo(methodOn(BannerController.class).deleteBanner(banner.getId()))
          .withRel("Delete banner"));
    return bannerEntityModel;
  }
}
