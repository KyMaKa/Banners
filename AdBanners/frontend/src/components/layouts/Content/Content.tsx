import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../js/ActiveTab";
import { ContentEmpty } from "./ContentEmpty";
import { ContentBannerForm } from "./ContentBannerForm";
import { ContentHeader } from "./ContentHeader";
import { ContentCategoryForm } from "./ContentCategoryForm";
import { CategoryType } from "../../models/Categories";

interface Props {
  // привести к типу.
  element: any;
  type: ActiveTab;
  activeTab: ActiveTab;
  categories: CategoryType[];
}

export const Content: FC<Props> = ({ element, type, categories, activeTab }) => {

  console.log(activeTab);
  console.log(type);

  if (element === null) return <ContentEmpty />;

  if (activeTab === ActiveTab.Banners) return bannerContent();
  return categoryContent();

  function bannerContent() {
    return (
      <section className="content">
        <ContentHeader
          elementId={element.id}
          elementName={element.name}
          activeTab={activeTab}
        />

        <ContentBannerForm element={element} categories={categories} />
      </section>
    );
  }

  function categoryContent() {
    return (
      <section className="content">
        <ContentHeader
          elementId={element.id}
          elementName={element.name}
          activeTab={activeTab}
        />
        <ContentCategoryForm element={element} />
      </section>
    );
  }
};
