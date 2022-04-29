package testtask.banners;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import testtask.banners.data.modelAssemblers.BannerModelAssembler;
import testtask.banners.data.models.Banner;
import testtask.banners.data.models.Category;
import testtask.banners.service.BannerService;
import testtask.banners.controllers.BannerController;

@WebMvcTest(BannerController.class)
class BannersControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private BannerService bannerService;


  @MockBean
  private BannerModelAssembler bannerModelAssembler;

  private Banner banner;

  private BannerModelAssembler assembler;

  @BeforeEach
  void setup() {
    banner = new Banner();
    banner.setId(1L);
    banner.setName("Some banner");
    banner.setText("Some text");
    banner.setPrice(10);
    banner.setDeleted(false);

    assembler = spy(BannerModelAssembler.class);
    //Probably there is a smarter way of doing it...
    //But this is works.
    EntityModel<Banner> entity = assembler.toModel(banner);
    when(bannerModelAssembler.toModel(any())).thenReturn(entity);
  }

  @Test
  public void testGetSingleBanner() throws Exception {
    when(bannerService.getBanner((Long) any())).thenReturn(banner);

    mockMvc.perform(get("/banners/{id}", 1L).with(user("user"))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)));
  }

  @Test
  public void testGetAllBanners() throws Exception {
    Banner banner2;
    banner2 = new Banner();
    banner2.setId(2L);
    banner2.setName("Some other banner");
    banner2.setText("Some text");
    banner2.setPrice(10);
    banner2.setDeleted(false);
    List<Banner> banners = new ArrayList<>();
    banners.add(banner);
    banners.add(banner2);

    when(bannerService.getAllBanner()).thenReturn(banners);
    EntityModel<Banner> entityModel1 = EntityModel.of(banner);
    when(bannerModelAssembler.toModel(banner)).thenReturn(entityModel1);
    EntityModel<Banner> entityModel2 = EntityModel.of(banner2);
    when(bannerModelAssembler.toModel(banner2)).thenReturn(entityModel2);

    //Json response starts like: {
    //    "_embedded": {
    //        "bannerList": [
    //            {
    //                "id": 1,
    //                ...
    mockMvc.perform(get("/banners/all").with(user("user"))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("_embedded.bannerList[0].id", is(1)))
        .andExpect(jsonPath("_embedded.bannerList[1].id", is(2)));
  }

  @Test
  public void testCreateNewBanner() throws Exception {

    when(bannerService.createBanner(any())).thenReturn(banner);

    mockMvc.perform(post("/banners").with(user("user"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsBytes(banner)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1)));
  }

  @Test
  public void testUpdateBanner() throws Exception {
    Banner updatedBanner = new Banner();
    updatedBanner.setId(banner.getId());
    updatedBanner.setName("Some banner");
    updatedBanner.setText("Some text");
    updatedBanner.setPrice(290);
    updatedBanner.setDeleted(false);
    when(bannerService.updateBanner(any(), any())).thenReturn(updatedBanner);

    EntityModel<Banner> entity = assembler.toModel(updatedBanner);
    when(bannerModelAssembler.toModel(any())).thenReturn(entity);

    mockMvc.perform(put("/banners/{id}", 1L).with(user("user"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsBytes(updatedBanner)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.price", is(updatedBanner.getPrice())));
  }

  @Test
  public void testDeleteBanner() throws Exception {

    doNothing().when(bannerService).deleteBanner(any());

    mockMvc.perform(delete("/banners/{id}", 1L).with(user("user")))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""));

  }

  @Test
  public void testAddCategoryToBanner() throws Exception {
    Category category = new Category();
    category.setName("Some category");
    category.setId(1L);
    category.setDeleted(false);
    Set<Category> set = new HashSet<>();
    set.add(category);
    banner.setCategories(set);

    when(bannerService.addCategory(any(), any())).thenReturn(banner);

    mockMvc.perform(put("/banners/add/{id}", 1L).with(user("user"))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsBytes(category)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.categories[0].id", is(1)));
  }

  @Test
  public void testRemoveCategoryFromBanner() throws Exception {
    Category category = new Category();
    category.setName("Some category");
    category.setId(1L);
    category.setDeleted(false);

    when(bannerService.removeCategory(any(), any())).thenReturn(banner);

    mockMvc.perform(put("/banners/add/{id}", 1L).with(user("user"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(category)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.categories.size()", is(0)));
  }

  @Test
  public void testCreateNewBannerWithEmptyName() throws Exception {
    Banner newBanner;
    newBanner = new Banner();
    newBanner.setName("");
    newBanner.setDeleted(false);

    mockMvc.perform(post("/banners").with(user("user"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(newBanner)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateNewBannerWithExistingName() throws Exception {
    Banner newBanner;
    newBanner = new Banner();
    newBanner.setName("Some banner");
    newBanner.setDeleted(false);

    when(bannerService.getBanner(anyString())).thenReturn(banner);

    mockMvc.perform(post("/banners").with(user("user"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsBytes(newBanner)))
        .andExpect(status().isConflict());
  }
}
