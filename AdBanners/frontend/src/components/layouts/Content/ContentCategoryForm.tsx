import * as React from "react";
import { FC, useState } from "react";
import { BannerService } from "../../services/BannersService";
import { ContentFooter } from "./ContentFooter";

interface Props {
  element: any;
}

export const ContentCategoryForm: FC<Props> = ({ element }) => {
  const [name, setName] = useState<string>(element.name);

  React.useEffect(() => {
    setName(element.name);
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
      <ContentFooter updateItem={updateCategory} deleteItem={deleteCategory} />
      {/* <Error /> */}
    </>
  );

  function handleChangeName(event) {
    const value = event.target.value;
    setName(value);
    console.log(value);
    //element.name = value;
  }

  function updateCategory() {
    element.name = name;
    BannerService.updateBanner(element.id, element).then((data) => {
      console.log(data);
    });
  }

  function deleteCategory() {
    BannerService.deleteBanner(element.id);
  }
};
