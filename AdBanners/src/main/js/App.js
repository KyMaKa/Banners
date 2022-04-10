import React from 'react';
import ReactDOM from "react-dom";
import Header from "../../../frontend/src/components/layouts/Header";
import Main from "../../../frontend/src/components/layouts/Main";

class App extends React.Component {
  render() {
    return (
        <div className="container">
          <Header />
          <Main />
        </div>
    );
  }
}


ReactDOM.render(<App />, document.querySelector("#app"));