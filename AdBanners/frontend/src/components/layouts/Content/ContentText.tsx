import * as React from "react";

interface Props {
  elementText: string;
}

export const ContentText: React.FC<Props> = ({ elementText }) => {
  return (
    <div className="content__form-item">
      <div className="content__form-item-title">Text</div>
      <div className="content__form-item-content">
        <textarea className="content__textarea" value={elementText}></textarea>
      </div>
    </div>
  );
};
