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
      <button onClick={() => activeTab} className="sidebar__submit-button">
        Create new {activeTab}
      </button>
    </footer>
  );
};
