import { Link, Outlet } from 'react-router-dom';

export function Layout() {
  return (
    <div className="layout">
      <header className="header">
        <Link to="/" className="logo">Kanana-2</Link>
        <nav>
          <Link to="/">홈</Link>
          <Link to="/models">모델 목록</Link>
          <Link to="/complete">인퍼런스</Link>
        </nav>
      </header>
      <main className="main">
        <Outlet />
      </main>
    </div>
  );
}
