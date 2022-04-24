// import * as React from "react";
// import { createRoot } from "react-dom/client";
// import { BannerService } from "./components/services/BannersService";

// function Bid() {
//   const [text, setText] = React.useState<string>();

//   React.useEffect(() => {
//     BannerService.getBanners().then((data) => {
//       setText(data);
//     });
//     return () => {};
//   }, []);

//   return <>{text}</>;
// }

// const container = document.getElementById("app");
// const root = createRoot(container);
// root.render(<Bid />);
