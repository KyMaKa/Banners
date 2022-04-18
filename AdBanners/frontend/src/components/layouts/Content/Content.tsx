import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../js/ActiveTab";
import { BannerType } from "../../models/Banners";
import { CategoryType } from "../../models/Categories";
import { Error } from "../Error/Error";
import { ContentDropList } from "./ContentDropList";
import { ContentEmpty } from "./ContentEmpty";
import { ContentHeader } from "./ContentHeader";
import { ContentName } from "./ContentName";
import { ContentPrice } from "./ContentPrice";
import { ContentRequestID } from "./ContentRequestID";
import { ContentText } from "./ContentText";

interface Props {
  element: BannerType | CategoryType;
  activeTab: ActiveTab;
}

export const Content: FC<Props> = ({ element, activeTab }) => {
  const [view, setView] = React.useState(<ContentEmpty />);
  const [category, setCategory] = React.useState<CategoryType>();
  const [banner, setBanner] = React.useState<BannerType>();
  React.useEffect(() => {
    onUpdate();
    return () => {
      setView(<ContentEmpty />);
    };
  });

  function onUpdate() {
    //console.log(element);
    if (element as BannerType) setCategory(element);
    if (element as CategoryType) setBanner(element);
    if (banner != null)
      if (activeTab === ActiveTab.Banners) setView(bannerContent);
    if (category != null)
      if (activeTab === ActiveTab.Categories) setView(categoryContent);
  }

  //console.log(banner);
  return view;

  function bannerContent() {
    return (
      <section className="content">
        <ContentHeader elementId={banner.id} elementName={banner.name} />

        <div className="content__body">
          <div className="content__form">
            <ContentName elementName={banner.name} />
            <ContentPrice elementPrice={banner.price} />
            <ContentDropList categories={banner.categories} />
            <ContentText elementText={banner.text} />
          </div>
        </div>

        <footer className="content__footer">
          <div className="content__buttons">
            <button className="content__button content__button_dark">
              Save
            </button>
            <button className="content__button content__button_red">
              Delete
            </button>
          </div>
        </footer>

        {/* <Error /> */}
      </section>
    );
  }

  function categoryContent() {
    return (
      <section className="content">
        <ContentHeader elementId={category.id} elementName={category.name} />
        <div className="content__body">
          <div className="content__form">
            <ContentName elementName={category.name} />
            <ContentRequestID elementName={category.name} />
          </div>
        </div>
      </section>
    );
  }
};
