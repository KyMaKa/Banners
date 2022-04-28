import * as React from "react";

interface Props {
  updateItem: () => void;
  deleteItem: () => void;
}

export const ContentFooter: React.FC<Props> = ({ updateItem, deleteItem }) => {
  return (
    <footer className="content__footer">
      <div className="content__buttons">
        <button
          className="content__button content__button_dark"
          onClick={updateItem}
        >
          Save
        </button>
        <button
          className="content__button content__button_red"
          onClick={deleteItem}
        >
          Delete
        </button>
      </div>
    </footer>
  );
};
