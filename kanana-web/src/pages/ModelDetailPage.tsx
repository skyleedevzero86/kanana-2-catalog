import { useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useModelById } from '@/hooks/useModelById';
import { useBenchmarks } from '@/hooks/useBenchmarks';
import { useServingPreset } from '@/hooks/useServingPreset';
import { BenchmarkChart } from '@/components/BenchmarkChart';
import { Loading } from '@/components/Loading';
import { ErrorAlert } from '@/components/ErrorAlert';

const BENCHMARK_CATEGORIES = [
  { value: '', label: '전체' },
  { value: 'GENERAL', label: '일반' },
  { value: 'MATHEMATICS', label: '수학' },
  { value: 'CODING', label: '코딩' },
  { value: 'KOREAN', label: '한국어' },
  { value: 'LONG_CONTEXT', label: '긴 컨텍스트' },
  { value: 'CHAT', label: '채팅' },
  { value: 'INSTRUCTION_FOLLOWING', label: '지시 따르기' },
  { value: 'TOOL_CALLING', label: '도구 호출' },
  { value: 'REASONING_KNOWLEDGE', label: '추론/지식' },
];

const LANGUAGE_LABELS: Record<string, string> = {
  ko: '한국어',
  en: '영어',
  ja: '일본어',
  zh: '중국어',
  th: '태국어',
  vi: '베트남어',
};

const SERVING_VARIANTS = new Set(['INSTRUCT', 'THINKING']);

function ModelDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { model, loading, error, refetch } = useModelById(id);
  const [category, setCategory] = useState('');
  const {
    benchmarks,
    loading: benchLoading,
    error: benchError,
  } = useBenchmarks(id, category || undefined);

  const hasServing = model ? SERVING_VARIANTS.has(model.variant) : false;
  const { preset, loading: servingLoading } = useServingPreset(hasServing ? id : undefined);

  if (loading) return <Loading />;
  if (error) return <ErrorAlert error={error} onRetry={refetch} />;
  if (!model) {
    return (
      <div className="page">
        <div className="breadcrumb">
          <Link to="/models">모델 목록</Link> / 찾을 수 없음
        </div>
        <p style={{ color: 'var(--color-text-muted)' }}>모델을 찾을 수 없습니다.</p>
      </div>
    );
  }

  return (
    <div className="page">
      <div className="breadcrumb">
        <Link to="/models">모델 목록</Link> / {model.name}
      </div>

      <div className="detail-header">
        <h1>{model.name}</h1>
        <span className={`variant variant-${model.variant.toLowerCase()}`}>
          {model.variant}
        </span>
        {model.supportsToolCalling && <span className="badge">Tool Calling</span>}
      </div>

      <section className="spec-section">
        <h2>기본 정보</h2>
        <dl className="spec-list">
          <dt>지원 언어</dt>
          <dd>
            {model.supportedLanguages
              .map((l) => LANGUAGE_LABELS[l] ?? l)
              .join(', ')}
          </dd>
          <dt>Hugging Face</dt>
          <dd>
            <a href={model.huggingFaceUrl} target="_blank" rel="noopener noreferrer">
              {model.huggingFaceUrl.replace('https://huggingface.co/', '')} →
            </a>
          </dd>
        </dl>
      </section>

      <section className="spec-section">
        <h2>모델 사양</h2>
        <dl className="spec-list">
          <dt>총 파라미터</dt>
          <dd>{model.spec.totalParameters}</dd>
          <dt>활성 파라미터</dt>
          <dd>{model.spec.activatedParameters}</dd>
          <dt>레이어 수</dt>
          <dd>{model.spec.layers}</dd>
          <dt>전문가 수</dt>
          <dd>{model.spec.experts}</dd>
          <dt>선택된 전문가</dt>
          <dd>{model.spec.selectedExperts}</dd>
          <dt>어텐션 메커니즘</dt>
          <dd>{model.spec.attentionMechanism}</dd>
          <dt>어휘 크기</dt>
          <dd>{model.spec.vocabularySize.toLocaleString()}</dd>
          <dt>컨텍스트 길이</dt>
          <dd>{model.spec.contextLength.toLocaleString()}</dd>
        </dl>
      </section>

      <section className="benchmark-section">
        <h2>벤치마크 결과</h2>
        <div style={{ marginBottom: '1rem' }}>
          <label
            htmlFor="category-select"
            style={{ fontSize: '0.9rem', color: 'var(--color-text-muted)', marginRight: '0.5rem' }}
          >
            카테고리:
          </label>
          <select
            id="category-select"
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            style={{
              padding: '0.4rem 0.75rem',
              background: 'var(--color-surface)',
              border: '1px solid var(--color-border)',
              borderRadius: 'var(--radius)',
              color: 'var(--color-text)',
              fontSize: '0.9rem',
            }}
          >
            {BENCHMARK_CATEGORIES.map((c) => (
              <option key={c.value} value={c.value}>
                {c.label}
              </option>
            ))}
          </select>
        </div>

        {benchLoading && <Loading />}
        {benchError && <ErrorAlert error={benchError} />}
        {!benchLoading && !benchError && (
          <BenchmarkChart
            data={benchmarks}
            title={
              category
                ? `${BENCHMARK_CATEGORIES.find((c) => c.value === category)?.label ?? category} 벤치마크`
                : '전체 벤치마크'
            }
          />
        )}

        {!benchLoading && benchmarks.length > 0 && (
          <div style={{ marginTop: '1rem' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '0.85rem' }}>
              <thead>
                <tr style={{ borderBottom: '1px solid var(--color-border)' }}>
                  <th style={{ textAlign: 'left', padding: '0.5rem', color: 'var(--color-text-muted)' }}>벤치마크</th>
                  <th style={{ textAlign: 'left', padding: '0.5rem', color: 'var(--color-text-muted)' }}>카테고리</th>
                  <th style={{ textAlign: 'left', padding: '0.5rem', color: 'var(--color-text-muted)' }}>메트릭</th>
                  <th style={{ textAlign: 'right', padding: '0.5rem', color: 'var(--color-text-muted)' }}>Shot</th>
                  <th style={{ textAlign: 'right', padding: '0.5rem', color: 'var(--color-text-muted)' }}>점수</th>
                </tr>
              </thead>
              <tbody>
                {benchmarks.map((b, i) => (
                  <tr
                    key={`${b.benchmarkName}-${b.category}-${i}`}
                    style={{ borderBottom: '1px solid var(--color-border)' }}
                  >
                    <td style={{ padding: '0.5rem' }}>{b.benchmarkName}</td>
                    <td style={{ padding: '0.5rem', color: 'var(--color-text-muted)' }}>{b.category}</td>
                    <td style={{ padding: '0.5rem', color: 'var(--color-text-muted)' }}>{b.metric}</td>
                    <td style={{ textAlign: 'right', padding: '0.5rem' }}>{b.shot}</td>
                    <td style={{ textAlign: 'right', padding: '0.5rem', fontWeight: 600, color: 'var(--color-primary)' }}>
                      {b.value.toFixed(2)}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>

      <section className="spec-section">
        <h2>서빙 프리셋</h2>
        {servingLoading && <Loading />}
        {!servingLoading && !preset && (
          <p style={{ color: 'var(--color-text-muted)' }}>
            이 모델의 서빙 프리셋이 등록되어 있지 않습니다.
          </p>
        )}
        {!servingLoading && preset && (
          <>
            <dl className="spec-list">
              <dt>백엔드</dt>
              <dd>{preset.backend}</dd>
              <dt>최소 GPU 메모리</dt>
              <dd>{(preset.minGpuMemoryMb / 1024).toFixed(1)} GB ({preset.minGpuMemoryMb.toLocaleString()} MB)</dd>
              {preset.toolCallParser && (
                <>
                  <dt>Tool Call Parser</dt>
                  <dd>{preset.toolCallParser}</dd>
                </>
              )}
              {preset.reasoningParser && (
                <>
                  <dt>Reasoning Parser</dt>
                  <dd>{preset.reasoningParser}</dd>
                </>
              )}
            </dl>
            <div style={{ marginTop: '1rem' }}>
              <p style={{ fontSize: '0.9rem', color: 'var(--color-text-muted)', marginBottom: '0.5rem' }}>
                실행 명령어:
              </p>
              <pre
                style={{
                  background: '#0d0d10',
                  border: '1px solid var(--color-border)',
                  borderRadius: 'var(--radius)',
                  padding: '1rem',
                  fontSize: '0.85rem',
                  overflowX: 'auto',
                  whiteSpace: 'pre-wrap',
                  wordBreak: 'break-all',
                }}
              >
                <code>{preset.commandTemplate}</code>
              </pre>
            </div>
          </>
        )}
      </section>

      <div style={{ marginTop: '1rem' }}>
        <Link to={`/complete?model=${model.id}`} className="button">
          이 모델로 인퍼런스
        </Link>
        <Link to="/models" className="button secondary">
          목록으로
        </Link>
      </div>
    </div>
  );
}

export default ModelDetailPage;
