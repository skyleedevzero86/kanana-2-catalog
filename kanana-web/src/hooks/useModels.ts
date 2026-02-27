import { useState, useEffect, useCallback } from 'react';
import type { ModelDto } from '@/types/model';
import { kananaRepository } from '@/api/kananaRepository';

export function useModels() {
  const [models, setModels] = useState<ModelDto[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  const fetchModels = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await kananaRepository.getModels();
      setModels(data);
    } catch (e) {
      setError(e instanceof Error ? e : new Error(String(e)));
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchModels();
  }, [fetchModels]);

  return { models, loading, error, refetch: fetchModels };
}
