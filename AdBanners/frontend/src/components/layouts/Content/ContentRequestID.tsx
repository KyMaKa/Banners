import * as React from "react";

interface Props {
  elementName: string;
}

export const ContentRequestID: React.FC<Props> = ({ elementName }) => {
  return (
    <div className="content__form-item">
      <div className="content__form-item-title">Request ID</div>
      <div className="content__form-item-content">
        <input className="content__input" type="text" value={elementName} />
      </div>
    </div>
  );
};
