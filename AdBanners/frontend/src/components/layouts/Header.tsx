import * as React from "react";
import { ActiveTab } from "../../ActiveTab";
import { FC } from "react";

interface Props {
  activeTab: ActiveTab;
  clickBanner: () => void;
  clickCategory: () => void;
}

export const Header: FC<Props> = ({
  clickBanner,
  clickCategory,
  activeTab,
}) => {
  return (
    <header className="header">
      <nav className="header__nav">
        <a
          href="#"
          className={
            activeTab === ActiveTab.Banners
              ? "header__link header__link_active"
              : "header__link"
          }
          onClick={clickBanner}
        >
          Banners
        </a>
        <a
          href="#"
          className={
            activeTab === ActiveTab.Categories
              ? "header__link header__link_active"
              : "header__link"
          }
          onClick={clickCategory}
        >
          Categories
        </a>
      </nav>
    </header>
  );
};
