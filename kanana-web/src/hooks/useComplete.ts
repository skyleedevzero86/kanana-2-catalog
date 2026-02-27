import { useState, useCallback } from 'react';
import type { CompleteRequestDto } from '@/types/model';
import { kananaRepository } from '@/api/kananaRepository';

export function useComplete() {
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const submit = useCallback(async (payload: CompleteRequestDto) => {
    setLoading(true);
    setError(null);
    setContent('');
    try {
      const res = await kananaRepository.complete(payload);
      setContent(res.content ?? '');
    } catch (e) {
      setError(e instanceof Error ? e : new Error(String(e)));
    } finally {
      setLoading(false);
    }
  }, []);

  const reset = useCallback(() => {
    setContent('');
    setError(null);
  }, []);

  return { content, loading, error, submit, reset };
}
