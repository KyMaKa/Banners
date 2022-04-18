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
  React.useEffect(() => {
    onUpdate();
  }, [activeTab]);

  function onUpdate() {
    return <ContentEmpty />;
  }

  if (element === null) return <ContentEmpty />;

  if (activeTab === ActiveTab.Banners) return bannerContent();

  if (activeTab === ActiveTab.Categories) return categoryContent();

  function bannerContent() {
    return (
      <section className="content">
        <ContentHeader elementId={element.id} elementName={element.name} />

        <div className="content__body">
          <div className="content__form">
            <ContentName elementName={element.name} />
            <ContentPrice elementPrice={element.price} />
            <ContentDropList categories={element.categories} />
            <ContentText elementText={element.text} />
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
        <ContentHeader elementId={element.id} elementName={element.name} />
        <div className="content__body">
          <div className="content__form">
            <ContentName elementName={element.name} />
            <ContentRequestID elementName={element.name} />
          </div>
        </div>
      </section>
    );
  }
};
