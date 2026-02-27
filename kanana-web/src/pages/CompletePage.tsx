import { useState, useEffect, FormEvent } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useModels } from '@/hooks/useModels';
import { useComplete } from '@/hooks/useComplete';
import { Loading } from '@/components/Loading';
import { ErrorAlert } from '@/components/ErrorAlert';

interface Message {
  role: 'user' | 'assistant';
  text: string;
}

function CompletePage() {
  const [searchParams] = useSearchParams();
  const { models, loading: modelsLoading } = useModels();
  const { content, loading: completing, error, submit, reset } = useComplete();

  const [selectedModel, setSelectedModel] = useState(searchParams.get('model') ?? '');
  const [message, setMessage] = useState('');
  const [history, setHistory] = useState<Message[]>([]);

  useEffect(() => {
    const param = searchParams.get('model');
    if (param) setSelectedModel(param);
  }, [searchParams]);

  useEffect(() => {
    if (content) {
      setHistory((prev) => [...prev, { role: 'assistant', text: content }]);
    }
  }, [content]);

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    const trimmed = message.trim();
    if (!trimmed || completing) return;

    setHistory((prev) => [...prev, { role: 'user', text: trimmed }]);
    submit({
      modelId: selectedModel || undefined,
      message: trimmed,
    });
    setMessage('');
  };

  const handleReset = () => {
    setHistory([]);
    setMessage('');
    reset();
  };

  const instructModels = models.filter(
    (m) => m.variant === 'INSTRUCT' || m.variant === 'THINKING'
  );

  return (
    <div className="page">
      <h1>인퍼런스</h1>
      <p className="page-desc">
        Kanana-2 모델에 메시지를 보내고 응답을 확인합니다. INSTRUCT, THINKING 변형만 사용 가능합니다.
      </p>

      <div className="complete-form">
        <div className="form-group">
          <label htmlFor="model-select">모델 선택</label>
          {modelsLoading ? (
            <Loading />
          ) : (
            <select
              id="model-select"
              value={selectedModel}
              onChange={(e) => setSelectedModel(e.target.value)}
            >
              <option value="">기본 모델 사용</option>
              {instructModels.map((m) => (
                <option key={m.id} value={m.id}>
                  {m.name} ({m.variant})
                </option>
              ))}
            </select>
          )}
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="message-input">메시지</label>
            <textarea
              id="message-input"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder="메시지를 입력하세요..."
              rows={3}
              disabled={completing}
            />
          </div>
          <div className="form-actions">
            <button type="submit" disabled={completing || !message.trim()}>
              {completing ? '생성 중...' : '전송'}
            </button>
            <button type="button" onClick={handleReset} disabled={completing}>
              초기화
            </button>
          </div>
        </form>
      </div>

      {error && (
        <div>
          <ErrorAlert error={error} />
          {error.message.includes('LLM') && (
            <p style={{
              fontSize: '0.85rem',
              color: 'var(--color-text-muted)',
              marginTop: '-0.5rem',
              marginBottom: '1rem',
            }}>
              vLLM 서버가 <code>localhost:8000</code>에서 실행 중인지 확인하세요.
              LLM 서버 없이는 인퍼런스 기능을 사용할 수 없습니다.
            </p>
          )}
        </div>
      )}

      {(history.length > 0 || completing) && (
        <div className="complete-result">
          <h2>대화 내역</h2>
          <div>
            {history.map((msg, i) => (
              <div
                key={i}
                style={{
                  display: 'flex',
                  gap: '0.75rem',
                  padding: '0.75rem 0',
                  borderBottom:
                    i < history.length - 1 || completing
                      ? '1px solid var(--color-border)'
                      : 'none',
                }}
              >
                <span
                  style={{
                    flexShrink: 0,
                    fontSize: '0.75rem',
                    fontWeight: 600,
                    color:
                      msg.role === 'user'
                        ? 'var(--color-primary)'
                        : 'var(--color-text-muted)',
                    width: '3rem',
                    paddingTop: '0.15rem',
                  }}
                >
                  {msg.role === 'user' ? 'USER' : 'AI'}
                </span>
                <p className="result-content">{msg.text}</p>
              </div>
            ))}
            {completing && (
              <div style={{ padding: '0.75rem 0' }}>
                <Loading />
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}

export default CompletePage;
