import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../js/ActiveTab";

interface Props {
  activeTab: ActiveTab;
}
//TODO: add button handler.
export const Footer: FC<Props> = ({ activeTab }) => {
  return (
    <footer className="sidebar__footer">
      <button className="sidebar__submit-button"
        onClick={null}
      >
        Create new {activeTab}
      </button>
    </footer>
  );
};
