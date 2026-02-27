import { useState, useEffect, useCallback } from 'react';
import type { ModelDto } from '@/types/model';
import { kananaRepository } from '@/api/kananaRepository';

export function useModelById(id: string | undefined) {
  const [model, setModel] = useState<ModelDto | null>(null);
  const [loading, setLoading] = useState(!!id);
  const [error, setError] = useState<Error | null>(null);

  const fetchModel = useCallback(async (modelId: string) => {
    setLoading(true);
    setError(null);
    try {
      const data = await kananaRepository.getModelById(modelId);
      setModel(data);
    } catch (e) {
      setError(e instanceof Error ? e : new Error(String(e)));
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (id) fetchModel(id);
    else {
      setModel(null);
      setLoading(false);
    }
  }, [id, fetchModel]);

  return { model, loading, error, refetch: () => id && fetchModel(id) };
}
