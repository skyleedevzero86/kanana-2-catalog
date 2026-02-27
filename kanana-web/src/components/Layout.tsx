import { Outlet, Link } from 'react-router-dom'
import styles from './Layout.module.css'

function Layout() {
  return (
    <div className={styles.wrapper}>
      <header className={styles.header}>
        <div className={styles.headerInner}>
          <Link to="/" className={styles.logo}>
            Kanana
          </Link>
          <nav className={styles.nav}>
            <Link to="/">홈</Link>
          </nav>
        </div>
      </header>
      <main className={styles.main}>
        <Outlet />
      </main>
    </div>
  )
}

export default Layout
