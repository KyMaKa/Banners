import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../ActiveTab";

interface Props {
  activeTab: ActiveTab;
  searchHandler: (event: React.BaseSyntheticEvent) => void;
}

export const SidebarSearch: FC<Props> = ({ activeTab, searchHandler }) => {
  let placeholder: string;
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
        onChange={searchHandler}
      />
      <span className="sidebar__search-icon" />
    </div>
  );
};
