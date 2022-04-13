import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../js/ActiveTab";

interface Props {
  activeTab: ActiveTab;
}

export const SidebarSearch: FC<Props> = ({ activeTab }) => {
  const placeholder = `Enter ${activeTab.toString} name...`;
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
