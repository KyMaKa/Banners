import React from "react";

function Main() {
  return (
      <main className="main">
        <aside className="sidebar">
          <header className="sidebar__header">Banners:</header>

          <section className="sidebar__content">
            <div className="sidebar__search">
              <input className="sidebar__search-input" type="text"
                     placeholder="Enter banner name..."/>
              <span className="sidebar__search-icon"/>
            </div>
            <div className="sidebar__menu">
              <a href="#" className="sidebar__menu-item">Some banner</a>
              <a href="#" className="sidebar__menu-item">Second banner</a>
              <a href="#" className="sidebar__menu-item">New test banner</a>
            </div>
          </section>

          <footer className="sidebar__footer">
            <button className="sidebar__submit-button">Create new Banner
            </button>
          </footer>
        </aside>

        <section className="content content_center">
          Choose an action.
        </section>
      </main>
  );
}
export default Main;