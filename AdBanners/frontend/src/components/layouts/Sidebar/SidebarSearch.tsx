import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../ActiveTab";

interface Props {
  activeTab: ActiveTab;
}

export const SidebarSearch: FC<Props> = ({ activeTab }) => {
  let placeholder;
  if (activeTab === ActiveTab.Banners) {
    placeholder = `Enter banner name...`;
  } else if (activeTab === ActiveTab.Categories) {
    placeholder = "Enter category name...";
  }
  return (
    <div className="sidebar__search">
      <input
        className="sidebar__search-input"
        type="text"
        placeholder={placeholder}
      />
      <span className="sidebar__search-icon" />
    </div>
  );
};
