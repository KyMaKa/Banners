import * as React from "react";
import { CategoryType } from "../../models/Categories";

interface Props {
  categories: CategoryType[];
}

export const ContentDropList: React.FC<Props> = ({ categories }) => {
  return (
    <div className="content__form-item">
      <div className="content__form-item-title">Category</div>
      <div className="content__form-item-content">
        <select className="content__select">
          {categories.map((category) => {
            return (
              <option value="cat" key={category.id}>
                {category.name}
              </option>
            );
          })}
        </select>
      </div>
    </div>
  );
};
