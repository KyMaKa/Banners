import React from "react";

function Header(){
  return(
      <header className="header">
        <nav className="header__nav">
          <a href="#" className="header__link header__link_active">Banners</a>
          <a href="#" className="header__link">Categories</a>
        </nav>
      </header>
  );

}
export default Header;
