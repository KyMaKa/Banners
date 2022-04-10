import React from "react";

class Header extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      activeB: false,
      activeC: false,
    };
  }

  handleClickBanners() {
    console.log('clicked');
    this.setState(prevState => ({
      activeB: !prevState.activeB,
      activeC: false
    }));
  }

  handleClickCategories() {
    console.log('clicked');
    this.setState(prevState => ({
      activeC: !prevState.activeC,
      activeB: false
    }));
  }

  render() {
    return (
        <header className="header">
          <nav className="header__nav">
            <a href="#"
               className={this.state.activeB ? 'header__link header__link_active'
                   : 'header__link'}
               onClick={() => this.handleClickBanners()}
            >
              Banners
            </a>
            <a href="#"
               className={this.state.activeC ? 'header__link header__link_active'
                   : 'header__link'}
               onClick={() => this.handleClickCategories()}
            >
              Categories
            </a>
          </nav>
        </header>
    );
  }
}
export default Header;
