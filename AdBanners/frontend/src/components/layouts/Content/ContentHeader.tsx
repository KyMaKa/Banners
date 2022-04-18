import * as React from "react";
import { FC } from "react";
import { ActiveTab } from "../../../js/ActiveTab";

interface Props {
  elementId: number;
  elementName: string;
}

export const ContentHeader: FC<Props> = ({ elementId, elementName }) => {
  return standartHeader(elementName, elementId);

  function createHeader() {
    return (
      <header className="content__header">
        <span className="content__header-text">Create new banner</span>
      </header>
    );
  }

  function standartHeader(elementName, elementId) {
    return (
      <header className="content__header">
        <span className="content__header-text">
          {elementName + " ID: " + elementId}
        </span>
      </header>
    );
  }
};
