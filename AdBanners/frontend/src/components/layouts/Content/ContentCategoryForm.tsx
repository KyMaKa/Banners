import { AxiosResponse, AxiosError } from "axios";
import * as React from "react";
import { FC, useState } from "react";
import { CategoryType } from "../../models/Categories";
import { CategoryService } from "../../services/CategoriesService";
import { Error } from "../Validation/Error";
import { Success } from "../Validation/Success";
import { ContentFooter } from "./ContentFooter";

interface Props {
  element: CategoryType;
}

export const ContentCategoryForm: FC<Props> = ({ element }) => {
  const [name, setName] = useState<string>(element.name);
  const [status, setStatus] = useState<number>(null);
  const [message, setMessage] = useState<string>("");

  //Sets the element values on selecting element.
  React.useEffect(() => {
    setName(element.name);
    setStatus(null);
    setMessage("");
  }, [element]);

  //Resets validation status.
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
            <div className="content__form-item-title">Request ID</div>
            <div className="content__form-item-content">
              <input
                className="content__input"
                type="text"
                readOnly
                value={name}
              />
            </div>
          </div>
        </div>
      </div>
      <ContentFooter
        updateItem={element.id === 0 ? addCategory : updateCategory}
        deleteItem={deleteCategory}
      />
      {status >= 400 ? <Error message={message} /> : null}
      {status != null && status < 300 ? <Success message={message} /> : null}
    </>
  );

  function handleChangeName(event: React.BaseSyntheticEvent) {
    const value = event.target.value;
    setName(value);
    console.log(value);
  }

  //Updates existing category.
  function updateCategory() {
    let category: CategoryType;
    category.id = element.id;
    category.name = name;
    category.deleted = false;
    CategoryService.updateCategory(category.id, category)
      .then((response: AxiosResponse) => {
        setMessage("Category updated.");
        console.log(response.status);
        setStatus(response.status);
      })
      .catch((error: AxiosError) => {
        setMessage(error.response.data.detail);
        setStatus(error.response.status);
      });
  }

  function deleteCategory() {
    CategoryService.deleteCategory(element.id)
      .then((response: AxiosResponse) => {
        setMessage("Category deleted.");
        setStatus(response.status);
      })
      .catch((error: AxiosError) => {
        setMessage(error.response.data.detail);
        setStatus(error.response.status);
      });
  }

  //Sends request to add new category.
  function addCategory() {
    element.name = name;
    CategoryService.postCategory(element)
      .then((response: AxiosResponse) => {
        setMessage("Category added.");
        setStatus(response.status);
      })
      .catch((error: AxiosError) => {
        setMessage(error.response.data.detail);
        setStatus(error.response.status);
      });
  }
};
