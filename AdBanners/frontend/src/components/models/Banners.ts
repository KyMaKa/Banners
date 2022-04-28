import { CategoryType } from "./Categories";

export interface BannerType {
  id: number,
  name: string,
  text?: string,
  price?: number,
  deleted: boolean,
  categories?: CategoryType[]
}