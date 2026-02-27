import { useState, useEffect, useCallback } from 'react';
import type { BenchmarkDto } from '@/types/model';
import { kananaRepository } from '@/api/kananaRepository';

export function useBenchmarks(modelId: string | undefined, category?: string) {
  const [benchmarks, setBenchmarks] = useState<BenchmarkDto[]>([]);
  const [loading, setLoading] = useState(!!modelId);
  const [error, setError] = useState<Error | null>(null);

  const fetchBenchmarks = useCallback(
    async (id: string) => {
      setLoading(true);
      setError(null);
      try {
        const data = await kananaRepository.getBenchmarks(id, category);
        setBenchmarks(data);
      } catch (e) {
        setError(e instanceof Error ? e : new Error(String(e)));
      } finally {
        setLoading(false);
      }
    },
    [category]
  );

  useEffect(() => {
    if (modelId) fetchBenchmarks(modelId);
    else {
      setBenchmarks([]);
      setLoading(false);
    }
  }, [modelId, fetchBenchmarks]);

  return {
    benchmarks,
    loading,
    error,
    refetch: () => modelId && fetchBenchmarks(modelId),
  };
}
