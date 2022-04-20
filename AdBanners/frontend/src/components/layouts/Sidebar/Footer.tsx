import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../js/ActiveTab";
import { BannerType } from "../../models/Banners";

interface Props {
  activeTab: ActiveTab;
  handler: any;
}

export const Footer: FC<Props> = ({ activeTab, handler }) => {
  const element: BannerType = {
    id: 0,
    name: "",
    text: "",
    price: 0,
    deleted: false,
    categories: [],
  };
  return (
    <footer className="sidebar__footer">
      <button
        className="sidebar__submit-button"
        onClick={() => handler(element)}
      >
        Create new {activeTab}
      </button>
    </footer>
  );

  function createBanner() {}

  function createCategory() {}
};
