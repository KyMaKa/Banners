import axios, { AxiosResponse } from "axios";
import { BannerType } from "../models/Banners";

const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

const responseBody = (response: AxiosResponse) =>
  response.data._embedded.bannerList;
const responseSingle = (response: AxiosResponse) => response.data;

const request = {
  getAll: (url: string) => instance.get(url).then(responseBody),
  getSingle: (url: string) => instance.get(url).then(responseSingle),
  updateBanner: (url: string, body: BannerType) =>
    instance.put(url, body).then(responseSingle),
  deleteBanner: (url: string) => instance.delete(url).then(responseSingle),
};

export const BannerService = {
  getBanners: (): Promise<BannerType[]> => request.getAll("banners/all"),
  getBanner: (id: number): Promise<BannerType> =>
    request.getSingle(`banners/${id}`),
  updateBanner: (id: number, body: BannerType): Promise<BannerType> =>
    request.updateBanner(`banners/${id}`, body),
  deleteBanner: (id: number): Promise<void> =>
    request.deleteBanner(`banners/${id}`),
};
