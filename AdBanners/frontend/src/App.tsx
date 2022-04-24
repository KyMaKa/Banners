import * as React from "react";
import { createRoot } from "react-dom/client";
import Main from "./components/layouts/Main";

class App extends React.Component {
  render() {
    return <Main />;
  }
}

const container = document.getElementById("app");
const root = createRoot(container);
root.render(<App />);
