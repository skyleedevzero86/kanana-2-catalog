import { useModels } from '@/hooks/useModels';
import { ModelCard } from '@/components/ModelCard';
import { Loading } from '@/components/Loading';
import { ErrorAlert } from '@/components/ErrorAlert';

function ModelListPage() {
  const { models, loading, error, refetch } = useModels();

  return (
    <div className="page">
      <h1>모델 목록</h1>
      <p className="page-desc">
        Kanana-2 모델 카탈로그에 등록된 모든 모델을 확인할 수 있습니다.
      </p>

      {loading && <Loading />}
      {error && <ErrorAlert error={error} onRetry={refetch} />}

      {!loading && !error && models.length === 0 && (
        <p style={{ color: 'var(--color-text-muted)' }}>등록된 모델이 없습니다.</p>
      )}

      {!loading && models.length > 0 && (
        <div className="model-grid">
          {models.map((m) => (
            <ModelCard key={m.id} model={m} />
          ))}
        </div>
      )}
    </div>
  );
}

export default ModelListPage;
