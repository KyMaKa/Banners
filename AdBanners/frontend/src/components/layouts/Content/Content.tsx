import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../js/ActiveTab";
import { ContentEmpty } from "./ContentEmpty";
import { ContentBannerForm } from "./ContentBannerForm";
import { ContentHeader } from "./ContentHeader";
import { ContentCategoryForm } from "./ContentCategoryForm";

interface Props {
  element: any;
  type: ActiveTab;
}

export const Content: FC<Props> = ({ element, type }) => {
  if (element === null) return <ContentEmpty />;

  if (type === ActiveTab.Banners) return bannerContent();
  return categoryContent();

  function bannerContent() {
    return (
      <section className="content">
        <ContentHeader
          elementId={element.id}
          elementName={element.name}
          activeTab={type}
        />

        <ContentBannerForm element={element} />
      </section>
    );
  }

  function categoryContent() {
    return (
      <section className="content">
        <ContentHeader
          elementId={element.id}
          elementName={element.name}
          activeTab={type}
        />
        <ContentCategoryForm element={element} />
      </section>
    );
  }
};
