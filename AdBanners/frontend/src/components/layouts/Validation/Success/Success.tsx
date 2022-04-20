import * as React from "react";

export const Success = ({ message }) => {
  return (
    <div className="info">
      <span className="success__text">{message}</span>
    </div>
  );
};
