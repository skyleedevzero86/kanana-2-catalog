const BASE = '/api/v1';

interface ApiErrorBody {
  message?: string;
  errorCode?: string;
}

function toKoreanMessage(status: number, bodyMessage?: string | null): string {
  if (bodyMessage && bodyMessage.trim()) return bodyMessage;
  if (status === 400) return '입력값을 확인해 주세요.';
  if (status === 404) return '요청한 리소스를 찾을 수 없습니다.';
  if (status === 502) return 'LLM 서버에 연결할 수 없습니다. 잠시 후 다시 시도해 주세요.';
  if (status >= 500) return '서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.';
  if (status >= 400) return `요청이 실패했습니다. (${status})`;
  return '알 수 없는 오류가 발생했습니다.';
}

async function request<T>(
  path: string,
  options?: RequestInit
): Promise<T> {
  const res = await fetch(`${BASE}${path}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...options?.headers,
    },
  });
  if (!res.ok) {
    const text = await res.text();
    let bodyMessage: string | null = null;
    try {
      if (text?.trim()) {
        const body = JSON.parse(text) as ApiErrorBody;
        bodyMessage = body?.message ?? null;
        if (bodyMessage == null && text.trim().length < 200) bodyMessage = text.trim();
        if (bodyMessage && bodyMessage.length > 300) bodyMessage = null;
      }
    } catch {
      if (text?.trim() && text.trim().length < 200) bodyMessage = text.trim();
    }
    const message = toKoreanMessage(res.status, bodyMessage);
    throw new Error(message);
  }
  if (res.status === 204 || res.headers.get('content-length') === '0') {
    return undefined as T;
  }
  return res.json() as Promise<T>;
}

export const apiClient = {
  get: <T>(path: string) => request<T>(path, { method: 'GET' }),
  post: <T>(path: string, body: unknown) =>
    request<T>(path, { method: 'POST', body: JSON.stringify(body) }),
};
