package testtask.banners.data.modelAssemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;
import testtask.banners.data.models.Category;
import testtask.banners.web.CategoryController;

@Component
public class CategoryModelAssembler implements
    RepresentationModelAssembler<Category, EntityModel<Category>> {

  @Override
  public EntityModel<Category> toModel(Category category) {
    EntityModel<Category> categoryEntityModel = EntityModel.of(category,
        linkTo(methodOn(CategoryController.class).getCategory(category.getId())).withSelfRel(),
        linkTo(methodOn(CategoryController.class).getAllCategory()).withRel("categorys"));

    if (category.getBanners().isEmpty()) {
      categoryEntityModel.add(linkTo(methodOn(CategoryController.class).deleteCategory(category.getId()))
          .withRel("Delete empty category"));
    }
    return categoryEntityModel;
  }

  @Override
  public CollectionModel<EntityModel<Category>> toCollectionModel(
      Iterable<? extends Category> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
