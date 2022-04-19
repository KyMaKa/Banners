import * as React from "react";
import { FC, useState } from "react";
import { CategoryType } from "../../models/Categories";
import { BannerService } from "../../services/BannersService";
import { ContentFooter } from "./ContentFooter";

interface Props {
  element: any;
}

export const ContentBannerForm: FC<Props> = ({ element }) => {
  const [name, setName] = useState<string>(element.name);
  const [price, setPrice] = useState<number>(element.price);
  const [text, setText] = useState<string>(element.text);
  const [categories, setCategories] = useState<CategoryType[]>(
    element.categories
  );

  React.useEffect(() => {
    setName(element.name);
    setPrice(element.price);
    setCategories(element.categories);
    setText(element.text);
    console.log(element);
  }, [element]);

  return (
    <>
      <div className="content__body">
        <div className="content__form">
          <div className="content__form-item">
            <div className="content__form-item-title">Name</div>
            <div className="content__form-item-content">
              <input
                className="content__input"
                type="text"
                value={name}
                onChange={handleChangeName}
              />
            </div>
          </div>
          <div className="content__form-item">
            <div className="content__form-item-title">Price</div>
            <div className="content__form-item-content">
              <input
                className="content__input"
                type="text"
                value={price}
                onChange={handleChangePrice}
              />
            </div>
          </div>
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
          <div className="content__form-item">
            <div className="content__form-item-title">Text</div>
            <div className="content__form-item-content">
              <textarea
                className="content__textarea"
                value={text}
                onChange={handleChangeText}
              ></textarea>
            </div>
          </div>
        </div>
      </div>
      <ContentFooter updateItem={updateBanner} deleteItem={deleteBanner} />
      {/* <Error /> */}
    </>
  );
  function handleChangeName(event) {
    const value = event.target.value;
    setName(value);
    console.log(value);
    //element.name = value;
  }
  function handleChangePrice(event) {
    const value = event.target.value;
    setPrice(value);
    //element.price = value;
  }

  function handleChangeText(event) {
    const value = event.target.value;
    setText(value);
    //element.text = value;
  }

  function updateBanner() {
    element.name = name;
    element.price = price;
    element.text = text;
    BannerService.updateBanner(element.id, element).then((data) => {
      console.log(data);
    });
  }

  function deleteBanner() {
    BannerService.deleteBanner(element.id);
  }
};
