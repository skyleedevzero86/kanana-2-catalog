import { Link } from 'react-router-dom';
import { useModels } from '@/hooks/useModels';
import { Loading } from '@/components/Loading';
import { ErrorAlert } from '@/components/ErrorAlert';

function HomePage() {
  const { models, loading, error, refetch } = useModels();

  const variants = new Set(models.map((m) => m.variant));
  const toolCallingCount = models.filter((m) => m.supportsToolCalling).length;

  return (
    <div className="page">
      <div style={{ textAlign: 'center', padding: '2rem 0 1rem' }}>
        <h1 style={{ fontSize: '2.25rem', fontWeight: 800, margin: 0 }}>Kanana-2 Catalog</h1>
        <p className="page-desc" style={{ marginBottom: 0 }}>
          Kanana-2 모델 카탈로그 — 모델 정보, 벤치마크, 서빙 설정, 인퍼런스를 한눈에 확인하세요.
        </p>
      </div>

      {loading && <Loading />}
      {error && <ErrorAlert error={error} onRetry={refetch} />}

      {!loading && !error && (
        <>
          <div className="summary-cards">
            <div className="summary-card">
              <span className="number">{models.length}</span>
              <span>등록 모델</span>
            </div>
            <div className="summary-card">
              <span className="number">{variants.size}</span>
              <span>변형 종류</span>
            </div>
            <div className="summary-card">
              <span className="number">{toolCallingCount}</span>
              <span>Tool Calling</span>
            </div>
          </div>

          <div style={{ display: 'flex', gap: '0.5rem', justifyContent: 'center', marginBottom: '2rem' }}>
            <Link to="/models" className="button">모델 목록 보기</Link>
            <Link to="/complete" className="button secondary">인퍼런스 테스트</Link>
          </div>

          {models.length > 0 && (
            <div>
              <h2 style={{ fontSize: '1.1rem', marginBottom: '0.75rem' }}>등록된 모델</h2>
              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(260px, 1fr))', gap: '0.75rem' }}>
                {models.map((m) => (
                  <Link
                    key={m.id}
                    to={`/models/${m.id}`}
                    style={{
                      background: 'var(--color-surface)',
                      border: '1px solid var(--color-border)',
                      borderRadius: 'var(--radius)',
                      padding: '1rem',
                      textDecoration: 'none',
                      color: 'inherit',
                      display: 'flex',
                      flexDirection: 'column',
                      gap: '0.35rem',
                    }}
                  >
                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                      <span style={{ fontWeight: 600, fontSize: '0.95rem' }}>{m.name}</span>
                      <span className={`variant variant-${m.variant.toLowerCase()}`}>{m.variant}</span>
                    </div>
                    <span style={{ fontSize: '0.8rem', color: 'var(--color-text-muted)' }}>
                      {m.spec.totalParameters} · Active {m.spec.activatedParameters} · {m.spec.contextLength.toLocaleString()} ctx
                    </span>
                  </Link>
                ))}
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default HomePage;
