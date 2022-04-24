import * as React from "react";
import { SideBar } from "./Sidebar/SideBar";
import { Header } from "./Header";
import { ActiveTab } from "../../js/ActiveTab";
import { useEffect, useState } from "react";
import { BannerService } from "../services/BannersService";
import { CategoryService } from "../services/CatergoriesService";
import { BannerType } from "../models/Banners";
import { CategoryType } from "../models/Categories";
import { ContentEmpty } from "./Content/ContentEmpty";
import { Content } from "./Content/Content";

export default function Main() {
  const [type, setType] = useState(ActiveTab.Banners);
  const [activeTab, setActiveTab] = useState(ActiveTab.Banners);
  const [banners, setBanners] = useState<BannerType[]>([]);
  const [categories, setCategories] = useState<CategoryType[]>([]);
  // опечатка
  const [clickedItem, setClicledItem] = useState<CategoryType | BannerType>(
    null
  );

  useEffect(() => {
    BannerService.getBanners().then((data) => {
      setBanners(data);
    });
    CategoryService.getCategories().then((data) => {
      setCategories(data);
    });
    return () => {};
  }, []);

  useEffect(() => {
    return () => setClicledItem(null);
  }, [activeTab]);

  return (
    <>
      <Header
        clickBanner={handleClickBanners}
        clickCategory={handleClickCategories}
        activeTab={activeTab}
      />
      <main className="main">
        <aside className="sidebar">
          <SideBar
            activeTab={activeTab}
            banners={banners}
            categories={categories}
            handeClickedItem={handleSelectedContent}
          />
        </aside>
        <Content element={clickedItem} type={type} categories={categories} activeTab={activeTab} />
      </main>
    </>
  );

  function handleClickBanners() {
    console.log("clicked");
    setActiveTab(ActiveTab.Banners);
  }

  function handleClickCategories() {
    console.log("clicked");
    setActiveTab(ActiveTab.Categories);
  }

  function handleSelectedContent(e: CategoryType | BannerType) {
    console.log("clicked sidebar item");
    setClicledItem(e);
    //For some reason i have to do this. Just using activeTab isn't working for Content.
    activeTab === ActiveTab.Banners
      ? setType(ActiveTab.Banners)
      : setType(ActiveTab.Categories);
  }
}
