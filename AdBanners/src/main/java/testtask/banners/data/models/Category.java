package testtask.banners.data.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

@Entity
public class Category extends RepresentationModel<Category> {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NonNull
  @Column(unique = true)
  private String category_name;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted;

  //It is optimal to use Set, because this reduces SQL operators while deleting.
  @ManyToMany(fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST, CascadeType.MERGE
  })

  private Set<Banner> banners = new HashSet<>();

  public Set<Banner> getBanners() {
    return banners;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public Long getId() {
    return id;
  }

  public void setBanners(Set<Banner> banners) {
    this.banners = banners;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCategory_name() {
    return category_name;
  }

  public void setCategory_name(String name) {
    this.category_name = name;
  }

  public void addBanner(Banner banner) {
    if (!banner.getDeleted()) {
      this.banners.add(banner);
      banner.getCategories().add(this);
    } else {

    }
  }

  public void removeBanner(Banner banner) {
    this.banners.remove(banner);
    banner.getCategories().remove(this);
  }

}
