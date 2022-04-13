import * as React from "react";
import { ActiveTab } from "../../../js/ActiveTab";
import { FC } from "react";
import { BannerType } from "../../models/Banners";
import { CategoryType } from "../../models/Categories";
import { SidebarSearch } from "./SidebarSearch";

interface Props {
  activeTab: ActiveTab;
  banners: BannerType[];
  categories: CategoryType[];
}

export const SideBar: FC<Props> = ({ activeTab, banners, categories }) => {
  let view;
  if (activeTab === ActiveTab.Banner) {
    view = renderList(banners);
  } else if (activeTab === ActiveTab.Category) {
    view = renderList(categories);
  }

  return (
    <aside className="sidebar">
      <header className="sidebar__header">Banners:</header>

      {view}

      <footer className="sidebar__footer">
        <button className="sidebar__submit-button">Create new Banner</button>
      </footer>
    </aside>
  );

  function renderBanners() {
    return (
      <section className="sidebar__content">
        <SidebarSearch activeTab={activeTab} />
        <div className="sidebar__menu">
          {banners.map((banner) => {
            return (
              <a href="#" key={banner.id} className="sidebar__menu-item">
                {banner.name}
              </a>
            );
          })}
        </div>
      </section>
    );
  }

  function renderCategories() {
    return (
      <section className="sidebar__content">
        <SidebarSearch activeTab={activeTab} />
        <div className="sidebar__menu">
          {categories.map((category) => {
            return (
              <a href="#" key={category.id} className="sidebar__menu-item">
                {category.name}
              </a>
            );
          })}
        </div>
      </section>
    );
  }

  function renderList<Type extends CategoryType, BannerType>(
    listToRender: Type[]
  ) {
    return (
      <section className="sidebar__content">
        <SidebarSearch activeTab={activeTab} />
        <div className="sidebar__menu">
          {listToRender.map((element) => {
            return (
              <a href="#" key={element.id} className="sidebar__menu-item">
                {element.name}
              </a>
            );
          })}
        </div>
      </section>
    );
  }
};
