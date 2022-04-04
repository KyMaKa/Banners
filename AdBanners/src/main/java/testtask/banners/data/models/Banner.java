package testtask.banners.data.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.lang.NonNull;

@Entity(name = "Banners")
public class Banner {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NonNull
  @Column(unique = true)
  private String banner_name;

  private String banner_text;

  private Integer price;

  @Column(name = "deleted")
  private Boolean deleted;


  //It is optimal to use Set, because this reduces SQL operators while deleting.
  @ManyToMany(mappedBy = "banners")
  private Set<Category> categories = new HashSet<>();

  public void setCategories(@NonNull Set<Category> categories) {
    this.categories = categories;
  }

  public String getBanner_name() {
    return banner_name;
  }

  public void setBanner_name(String banner_name) {
    this.banner_name = banner_name;
  }

  public String getBanner_text() {
    return banner_text;
  }

  public void setBanner_text(String banner_text) {
    this.banner_text = banner_text;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }
}
