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

const warnStyle: React.CSSProperties = {
  fontSize: '0.83rem',
  background: 'rgba(245, 158, 11, 0.1)',
  border: '1px solid rgba(245, 158, 11, 0.35)',
  color: '#fcd34d',
  borderRadius: 'var(--radius)',
  padding: '0.55rem 0.85rem',
  marginTop: '0.4rem',
  lineHeight: 1.55,
};

const hintStyle: React.CSSProperties = {
  fontSize: '0.83rem',
  color: 'var(--color-text-muted)',
  marginTop: '-0.5rem',
  marginBottom: '1rem',
};

function isVllmOnlyModel(modelId: string) {
  return modelId.startsWith('kanana-');
}

function CompletePage() {
  const [searchParams] = useSearchParams();
  const { models, loading: modelsLoading } = useModels();
  const { content, loading: completing, error, submit, reset } = useComplete();

  const [selectedModel, setSelectedModel] = useState(searchParams.get('model') ?? '');
  const [message, setMessage] = useState('');
  const [history, setHistory] = useState<Message[]>([]);
  const [lastMessage, setLastMessage] = useState('');

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

    setLastMessage(trimmed);
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
    setLastMessage('');
    reset();
  };

  const handleSwitchToDefault = () => {
    setSelectedModel('');
    reset();
    if (lastMessage) {
      setHistory((prev) => [...prev, { role: 'user', text: lastMessage }]);
      submit({ message: lastMessage });
    }
  };

  const instructModels = models.filter(
    (m) => m.variant === 'INSTRUCT' || m.variant === 'THINKING'
  );

  const showVllmWarning = selectedModel !== '' && isVllmOnlyModel(selectedModel);

  const isVllmError =
    error !== null &&
    (error.message.includes('모델을 찾을 수 없습니다') ||
      (error.message.includes('거부되었습니다') && error.message.includes('404')));

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
            <>
              <select
                id="model-select"
                value={selectedModel}
                onChange={(e) => { setSelectedModel(e.target.value); reset(); }}
              >
                <option value="">기본 모델 사용 (현재 설정된 외부 API)</option>
                <optgroup label="로컬 vLLM 전용 ↓">
                  {instructModels.map((m) => (
                    <option key={m.id} value={m.id}>
                      {m.name} ({m.variant})
                    </option>
                  ))}
                </optgroup>
              </select>
              {showVllmWarning && (
                <p style={warnStyle}>
                  ⚠️ <strong>로컬 vLLM 전용 모델</strong>입니다. Groq 등 외부 API에서는 이 모델 ID를 인식하지 못해 오류가 발생합니다.
                  {' '}외부 API를 사용하려면 <strong>기본 모델 사용</strong>을 선택하세요.
                </p>
              )}
            </>
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
          {isVllmError && (
            <div style={{ ...hintStyle, marginTop: 0, marginBottom: '1rem' }}>
              <p style={{ margin: '0 0 0.6rem' }}>
                선택한 Kanana 모델은 <strong>로컬 vLLM 서버 전용</strong>입니다. 외부 API(Groq 등)에서는 이 모델 ID를 인식하지 못합니다.
                {' '}로컬 vLLM 서버를 실행하고 <code>LLM_INFERENCE_URL</code> 환경변수를 설정하거나,{' '}
                아래 버튼으로 기본 모델(외부 API)을 사용하세요.
              </p>
              <button
                type="button"
                onClick={handleSwitchToDefault}
                style={{
                  padding: '0.4rem 0.9rem',
                  background: 'rgba(245,158,11,0.15)',
                  border: '1px solid rgba(245,158,11,0.4)',
                  color: '#fcd34d',
                  borderRadius: 'var(--radius)',
                  cursor: 'pointer',
                  fontSize: '0.83rem',
                  fontWeight: 600,
                }}
              >
                기본 모델로 전환하여 재시도
              </button>
            </div>
          )}
          {!isVllmError && error.message.includes('연결할 수 없습니다') && (
            <p style={hintStyle}>
              LLM 서버에 연결할 수 없습니다. 서버가 실행 중인지, 백엔드 설정의{' '}
              <code>LLM_INFERENCE_URL</code>이 올바른지 확인하세요.
            </p>
          )}
          {!isVllmError && error.message.includes('인증에 실패했습니다') && (
            <p style={hintStyle}>
              LLM 서버 인증에 실패했습니다. <code>LLM_API_KEY</code> 환경변수를 확인하세요.
            </p>
          )}
          {!isVllmError &&
            error.message.includes('거부되었습니다') &&
            !error.message.includes('404') && (
              <p style={hintStyle}>
                LLM 서버가 요청을 거부했습니다. API 키와 요청 형식이 올바른지 확인하세요.
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
