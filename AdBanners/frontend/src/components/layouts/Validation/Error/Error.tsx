import * as React from "react";


export const Error = ({message}) => {
  return (
    <div className="error">
      <span className="error__text">
        {message}
      </span>
    </div>
  );
}
