import axios, {AxiosResponse} from "axios";
import {BannerType} from "../models/Banners";

const instance = axios.create({
  baseURL: 'http://localhost:8080/',
});

const responseBody = (response: AxiosResponse) => response.data._embedded.bannerList;

const request = {
  get: (url:string) => instance.get(url).then(responseBody)
};

export const BannerService = {
  getBanners: (): Promise<BannerType[]> => request.get('banners/all')
};