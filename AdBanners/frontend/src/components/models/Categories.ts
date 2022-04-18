import { BannerType } from "./Banners";

export interface CategoryType extends BannerType {
  id: number;
  name: string;
  deleted: boolean;
}
