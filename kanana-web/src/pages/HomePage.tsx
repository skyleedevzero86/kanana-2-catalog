import styles from './HomePage.module.css'

function HomePage() {
  return (
    <div className={styles.container}>
      <section className={styles.hero}>
        <h1 className={styles.title}>Kanana Catalog</h1>
        <p className={styles.subtitle}>
          데이터 카탈로그 관리 플랫폼에 오신 것을 환영합니다.
        </p>
      </section>
    </div>
  )
}

export default HomePage
