import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../ActiveTab";

interface Props {
  elementId: number;
  elementName: string;
  activeTab: ActiveTab;
}

export const ContentHeader: FC<Props> = ({
  elementId,
  elementName,
  activeTab,
}) => {
  if (elementId === 0) return createHeader();
  return standardHeader(elementName, elementId);

  function createHeader() {
    return (
      <header className="content__header">
        <span className="content__header-text">Create new {activeTab}</span>
      </header>
    );
  }

  function standardHeader(elementName: string, elementId: number) {
    return (
      <header className="content__header">
        <span className="content__header-text">
          {elementName + " ID: " + elementId}
        </span>
      </header>
    );
  }
};
