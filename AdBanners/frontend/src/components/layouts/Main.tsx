import * as React from "react";
import { SideBar } from "./Sidebar/SideBar";
import { Header } from "./Header";
import { ActiveTab } from "../../js/ActiveTab";
import { useEffect, useState } from "react";
import { BannerService } from "../services/BannersService";
import { CategoryService } from "../services/CatergoriesService";
import { BannerType } from "../models/Banners";
import { CategoryType } from "../models/Categories";

export default function Main() {
  const [activeTab, setActiveTab] = useState(ActiveTab.Banner);
  const [banners, setBanners] = useState<BannerType[]>([]);
  const [categories, setCategories] = useState<CategoryType[]>([]);

  useEffect(() => {
    BannerService.getBanners().then((data) => {
      setBanners(data);
    });
    /*axios.get('http://localhost:8080/banners/all')
    .then(r => {
      const allBanners = r.data._embedded.bannerList
      setBanners(allBanners);
    });*/
    CategoryService.getCategories().then((data) => {
      setCategories(data);
    });
    return () => {};
  }, []);

  return (
    <>
      <Header
        clickBanner={() => handleClickBanners()}
        clickCategory={() => handleClickCategories()}
        activeTab={activeTab}
      />
      <main className="main">
        <SideBar activeTab={activeTab}
          banners={banners}
          categories={categories}
        />

        <section className="content content_center">Choose an action.</section>
      </main>
    </>
  );

  function handleClickBanners() {
    console.log("clicked");
    setActiveTab(ActiveTab.Banner);
  }

  function handleClickCategories() {
    console.log("clicked");
    setActiveTab(ActiveTab.Category);
  }
}
