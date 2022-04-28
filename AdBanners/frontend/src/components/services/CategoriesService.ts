import axios, { AxiosResponse } from "axios";
import { CategoryType } from "../models/Categories";

const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

const responseBody = (response: AxiosResponse) =>
  response.data._embedded.categoryList;
const responseSingle = (response: AxiosResponse) => response;

const request = {
  get: (url: string) => instance.get(url).then(responseBody),
  updateCategory: (url: string, body: CategoryType) =>
    instance.put(url, body).then(responseSingle),
  deleteCategory: (url: string) => instance.delete(url).then(responseSingle),
  post: (url: string, body: CategoryType) =>
    instance.post(url, body).then(responseSingle),
};

export const CategoryService = {
  getCategories: (): Promise<CategoryType[]> => request.get("categories/all"),
  updateCategory: (id: number, body: CategoryType): Promise<AxiosResponse> =>
    request.updateCategory(`categories/${id}`, body),
  deleteCategory: (id: number): Promise<AxiosResponse> =>
    request.deleteCategory(`categories/${id}`),
  postCategory: (body: CategoryType): Promise<AxiosResponse> =>
    request.post("categories/add", body),
};
