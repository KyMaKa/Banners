import axios, { AxiosResponse } from "axios";
import { BannerType } from "../models/Banners";

const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

const responseBody = (response: AxiosResponse) =>
  response.data._embedded.bannerList;
const responseSingle = (response: AxiosResponse) => response;

const request = {
  getAll: (url: string) => instance.get(url).then(responseBody),
  getSingle: (url: string) => instance.get(url).then(responseSingle),
  updateBanner: (url: string, body: BannerType) =>
    instance.put(url, body).then(responseSingle),
  deleteBanner: (url: string) => instance.delete(url).then(responseSingle),
  post: (url: string, body: BannerType) =>
    instance.post(url, body).then(responseSingle),
};

export const BannerService = {
  getBanners: (): Promise<BannerType[]> => request.getAll("banners/all"),
  getBanner: (id: number): Promise<AxiosResponse> =>
    request.getSingle(`banners/${id}`),
  updateBanner: (id: number, body: BannerType): Promise<AxiosResponse> =>
    request.updateBanner(`banners/${id}`, body),
  deleteBanner: (id: number): Promise<AxiosResponse> =>
    request.deleteBanner(`banners/${id}`),
  postBanner: (body: BannerType): Promise<AxiosResponse> =>            
    request.post("banners/", body),
};
