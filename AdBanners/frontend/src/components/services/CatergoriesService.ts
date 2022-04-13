import axios, { AxiosResponse } from "axios";
import { CategoryType } from "../models/Categories";

const instance = axios.create({
  baseURL: "http://localhost:8080/",
});

const responseBody = (response: AxiosResponse) =>
  response.data._embedded.categoryList;

const request = {
  get: (url: string) => instance.get(url).then(responseBody),
};

export const CategoryService = {
  getCategories: (): Promise<CategoryType[]> => request.get("categories/all"),
};
