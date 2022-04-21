package testtask.banners.data.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.lang.NonNull;

@Entity
public class Category extends RepresentationModel<Category> {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NonNull
  @Column(nullable = false)
  private String name;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted;

  // It is optimal to use Set, because this reduces SQL operators while deleting.
  @JsonBackReference
  @ManyToMany(fetch = FetchType.LAZY/*,cascade = {
      CascadeType.MERGE,
      CascadeType.PERSIST
  }*/, mappedBy = "categories")
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Category category)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    return getId().equals(category.getId()) && getName().equals(
        category.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getId(), getName());
  }

}
