import type {
  ModelDto,
  BenchmarkDto,
  ServingPresetDto,
  CompleteRequestDto,
  CompleteResponseDto,
} from '@/types/model';
import { apiClient } from './client';

export const kananaRepository = {
  getModels: (): Promise<ModelDto[]> =>
    apiClient.get<ModelDto[]>('/models'),

  getModelById: (id: string): Promise<ModelDto | null> =>
    apiClient.get<ModelDto>(`/models/${encodeURIComponent(id)}`).catch(() => null),

  getBenchmarks: (modelId: string, category?: string): Promise<BenchmarkDto[]> => {
    const q = category ? `?category=${encodeURIComponent(category)}` : '';
    return apiClient.get<BenchmarkDto[]>(`/models/${encodeURIComponent(modelId)}/benchmarks${q}`);
  },

  getServingPreset: (modelId: string): Promise<ServingPresetDto | null> =>
    apiClient
      .get<ServingPresetDto>(`/models/${encodeURIComponent(modelId)}/serving`)
      .catch(() => null),

  complete: (body: CompleteRequestDto): Promise<CompleteResponseDto> =>
    apiClient.post<CompleteResponseDto>('/complete', body),
};
