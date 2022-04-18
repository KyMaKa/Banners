import * as React from "react";

interface Props {
  elementPrice: number;
}

export const ContentPrice: React.FC<Props> = ({ elementPrice }) => {
  return (
    <div className="content__form-item">
      <div className="content__form-item-title">Price</div>
      <div className="content__form-item-content">
        <input className="content__input" type="text" value={elementPrice} />
      </div>
    </div>
  );
};
