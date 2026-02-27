import { useState, useEffect, useCallback } from 'react';
import type { ServingPresetDto } from '@/types/model';
import { kananaRepository } from '@/api/kananaRepository';

export function useServingPreset(modelId: string | undefined) {
  const [preset, setPreset] = useState<ServingPresetDto | null>(null);
  const [loading, setLoading] = useState(!!modelId);
  const [error, setError] = useState<Error | null>(null);

  const fetchPreset = useCallback(async (id: string) => {
    setLoading(true);
    setError(null);
    try {
      const data = await kananaRepository.getServingPreset(id);
      setPreset(data);
    } catch (e) {
      setError(e instanceof Error ? e : new Error(String(e)));
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (modelId) fetchPreset(modelId);
    else {
      setPreset(null);
      setLoading(false);
    }
  }, [modelId, fetchPreset]);

  return { preset, loading, error, refetch: () => modelId && fetchPreset(modelId) };
}
