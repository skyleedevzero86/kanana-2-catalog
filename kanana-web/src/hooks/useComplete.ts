import { useState, useCallback } from 'react';
import type { CompleteRequestDto } from '@/types/model';
import { kananaRepository } from '@/api/kananaRepository';

export function useComplete() {
  const [content, setContent] = useState('');
  const [modelUsed, setModelUsed] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);

  const submit = useCallback(async (payload: CompleteRequestDto) => {
    setLoading(true);
    setError(null);
    setContent('');
    setModelUsed('');
    try {
      const res = await kananaRepository.complete(payload);
      setContent(res.content ?? '');
      setModelUsed(res.modelUsed ?? '');
    } catch (e) {
      setError(e instanceof Error ? e : new Error(String(e)));
    } finally {
      setLoading(false);
    }
  }, []);

  const reset = useCallback(() => {
    setContent('');
    setModelUsed('');
    setError(null);
  }, []);

  return { content, modelUsed, loading, error, submit, reset };
}
