package testtask.banners.data.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.springframework.lang.NonNull;

@Entity(name = "Banners")
public class Banner {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NonNull
  @Column(nullable = false)
  private String name;
  private String text;
  private Integer price;
  @Column(name = "deleted")
  private Boolean deleted;
  // It is optimal to use Set, because this reduces SQL operators while deleting.
  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Category> categories = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Banner banner)) {
      return false;
    }
    return getId().equals(banner.getId()) && getName().equals(banner.getName())
        && Objects.equals(getCategories(), banner.getCategories());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }

  public String getName() {
    return name;
  }

  public void setName(String banner_name) {
    this.name = banner_name;
  }

  public String getText() {
    return text;
  }

  public void setText(String banner_text) {
    this.text = banner_text;
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

  public void setCategories(@NonNull Set<Category> categories) {
    this.categories = categories;
  }

  @Override
  public String toString() {
    return "Banner{" +
        "id=" + id +
        ", banner_name='" + name + '\'' +
        ", banner_text='" + text + '\'' +
        ", price=" + price +
        ", deleted=" + deleted +
        '}';
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

  /**
   * Simply adds category to this banner.
   * @param category to add.
   */
  public void addCategory(Category category) {
    this.categories.add(category);
  }

  /**
   * Removes category from this banner.
   * @param category to add.
   */
  public void removeCategory(Category category) {
    this.categories.remove(category);
  }
}
