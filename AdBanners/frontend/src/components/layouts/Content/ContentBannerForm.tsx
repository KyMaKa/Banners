import { AxiosError, AxiosResponse } from "axios";
import * as React from "react";
import { FC, useState } from "react";
import { CategoryType } from "../../models/Categories";
import { BannerService } from "../../services/BannersService";
import { Error } from "../Validation/Error/Error";
import { Success } from "../Validation/Success/Success";
import { ContentFooter } from "./ContentFooter";

interface Props {
  element: any;
}

export const ContentBannerForm: FC<Props> = ({ element }) => {
  const [status, setStatus] = useState<number>(null);
  const [message, setMessage] = useState<string>("");
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
    setStatus(null);
    setMessage("");
  }, [element]);

  React.useEffect(() => {
    setStatus(null);
  }, [name]);

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
                type="number"
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
      <ContentFooter
        updateItem={element.id === 0 ? addBanner : updateBanner}
        deleteItem={deleteBanner}
      />
      {status >= 400 ? <Error message={message} /> : null}
      {status < 300 && status != null ? <Success message={message} /> : null}
    </>
  );

  function handleChangeName(event) {
    const value = event.target.value;
    setName(value);
  }
  function handleChangePrice(event) {
    const value = event.target.value;
    setPrice(value);
  }

  function handleChangeText(event) {
    const value = event.target.value;
    if (value as number)
    setText(value);
  }

  function updateBanner() {
    element.name = name;
    element.price = price;
    element.categories = categories;
    element.text = text;
    BannerService.updateBanner(element.id, element)
      .then((response: AxiosResponse) => {
        setMessage("Banner updated.");
        console.log(response.status);
        setStatus(response.status);
      })
      .catch((error: AxiosError) => {
        setMessage(error.response.data.detail);
        setStatus(error.response.status);
      });
  }

  function deleteBanner() {
    BannerService.deleteBanner(element.id)
      .then((response: AxiosResponse) => {
        setMessage("Banner deleted.");
        setStatus(response.status);
      })
      .catch((error: AxiosError) => {
        setMessage(error.response.data.detail);
        setStatus(error.response.status);
      });
  }

  function addBanner() {
    element.name = name;
    element.price = price;
    element.categories = categories;
    element.text = text;
    BannerService.postBanner(element)
      .then((response: AxiosResponse) => {
        setMessage("Banner added.");
        setStatus(response.status);
      })
      .catch((error: AxiosError) => {
        setMessage(error.response.data.detail);
        setStatus(error.response.status);
      });
  }
};
