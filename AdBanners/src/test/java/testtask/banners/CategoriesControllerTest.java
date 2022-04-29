package testtask.banners;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import testtask.banners.controllers.CategoryController;
import testtask.banners.data.modelAssemblers.CategoryModelAssembler;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.service.CategoryService;

@WebMvcTest(CategoryController.class)
public class CategoriesControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private CategoryService categoryService;


  @MockBean
  private CategoryModelAssembler categoryModelAssembler;

  private CategoryModelAssembler assembler;

  private Category category;

  @BeforeEach
  void setup() {
    category = new Category();
    category.setId(1L);
    category.setName("Some name");
    category.setDeleted(false);

    assembler = spy(CategoryModelAssembler.class);
    //Probably there is a smarter way of doing it...
    //But this is works.
    EntityModel<Category> entity = assembler.toModel(category);
    when(categoryModelAssembler.toModel(any())).thenReturn(entity);
  }

  @Test
  public void testGetAllCategories() throws Exception {
    Category category2;
    category2 = new Category();
    category2.setId(2L);
    category2.setName("Some name");
    category2.setDeleted(false);
    List<Category> categories = new ArrayList<>();
    categories.add(category);
    categories.add(category2);

    when(categoryService.getAllCategory()).thenReturn(categories);
    EntityModel<Category> entityModel1 = EntityModel.of(category);
    when(categoryModelAssembler.toModel(category)).thenReturn(entityModel1);
    EntityModel<Category> entityModel2 = EntityModel.of(category2);
    when(categoryModelAssembler.toModel(category2)).thenReturn(entityModel2);

    //Json response starts like: {
    //    "_embedded": {
    //        "categoryList": [
    //            {
    //                "id": 1,
    //                ...
    mockMvc.perform(get("/categories/all").with(user("user"))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.categoryList[0].id", is(1)))
        .andExpect(jsonPath("_embedded.categoryList[1].id", is(2)));
  }

  @Test
  public void testCreateNewCategory() throws Exception {

    when(categoryService.createCategory(any())).thenReturn(category);

    mockMvc.perform(post("/categories/add").with(user("user"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(category)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1)));
  }

  @Test
  public void testUpdateCategory() throws Exception {
    Category newCategory;
    newCategory = new Category();
    newCategory.setId(1L);
    newCategory.setName("Some other name");
    newCategory.setDeleted(false);
    when(categoryService.updateCategory(any(), any())).thenReturn(newCategory);

    EntityModel<Category> entity = assembler.toModel(newCategory);
    when(categoryModelAssembler.toModel(any())).thenReturn(entity);

    mockMvc.perform(put("/categories/{id}", 1L).with(user("user"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(newCategory)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is(newCategory.getName())));
  }

  @Test
  public void testDeleteCategory() throws Exception {
    when(categoryService.getCategory(anyLong())).thenReturn(category);

    mockMvc.perform(delete("/categories/{id}", 1L).with(user("user")))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""));

  }

  @Test
  public void testDeleteCategoryWithBanners() throws Exception {
    Banner banner = new Banner();
    banner.setId(1L);
    banner.setName("Some banner");
    banner.setText("Some text");
    banner.setPrice(10);
    banner.setDeleted(false);
    category.getBanners().add(banner);
    when(categoryService.getCategory(anyLong())).thenReturn(category);

    mockMvc.perform(delete("/categories/{id}", 1L).with(user("user")))
        .andExpect(status().isMethodNotAllowed());

  }

  @Test
  public void testCreateNewCategoryWithExistingName() throws Exception {
    Category newCategory;
    newCategory = new Category();
    newCategory.setName("Some name");
    newCategory.setDeleted(false);

    when(categoryService.getCategory(anyString())).thenReturn(category);

    mockMvc.perform(post("/categories/add").with(user("user"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(newCategory)))
        .andExpect(status().isConflict());
  }

  @Test
  public void testCreateNewCategoryWithEmptyName() throws Exception {
    Category newCategory;
    newCategory = new Category();
    newCategory.setName("");
    newCategory.setDeleted(false);

    mockMvc.perform(post("/categories/add").with(user("user"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(newCategory)))
        .andExpect(status().isBadRequest());
  }

}
