import * as React from "react";
import { ActiveTab } from "../../../js/ActiveTab";
import { FC, useState } from "react";
import { BannerType } from "../../models/Banners";
import { CategoryType } from "../../models/Categories";
import { SidebarSearch } from "./SidebarSearch";
import { Footer } from "./Footer";

interface Props {
  activeTab: ActiveTab;
  banners: BannerType[];
  categories: CategoryType[];
  handeClickedItem: (e: CategoryType | BannerType) => void;
}

export const SideBar: FC<Props> = ({
  activeTab,
  banners,
  categories,
  handeClickedItem,
}) => {
  const [activeItem, setActiveItem] = useState<number>();

  React.useEffect(() => {
    return () => {
      setActiveItem(null);
    };
  }, [activeTab]);

  let view: ReturnType<typeof renderList>;
  let placeholder: string;

  if (activeTab === ActiveTab.Banners) {
    view = renderList(banners);
    placeholder = "Banners";
  } else {
    view = renderList(categories);
    placeholder = "Categories";
  }

  return (
    <>
      <header className="sidebar__header">{placeholder}:</header>

      {view}

      <Footer activeTab={activeTab} handler={handeClickedItem} />
    </>
  );

  function renderList<Type extends CategoryType, BannerType>(
    listToRender: Type[]
  ) {
    return (
      <section className="sidebar__content">
        <SidebarSearch activeTab={activeTab} />
        <div className="sidebar__menu">
          {listToRender.map((element) => {
            return (
              <a
                href="#"
                key={element.id}
                className={
                  activeItem === element.id
                    ? "sidebar__menu-item sidebar__menu-item_active"
                    : "sidebar__menu-item"
                }
                onClick={() => {
                  setActiveItem(element.id);
                  handeClickedItem(element);
                }}
              >
                {element.name}
              </a>
            );
          })}
        </div>
      </section>
    );
  }
};
