export interface ModelSpecDto {
  totalParameters: string;
  activatedParameters: string;
  layers: number;
  denseLayers: number;
  experts: number;
  selectedExperts: number;
  sharedExperts: number;
  attentionMechanism: string;
  vocabularySize: number;
  contextLength: number;
}

export interface ModelDto {
  id: string;
  name: string;
  variant: string;
  spec: ModelSpecDto;
  huggingFaceUrl: string;
  supportedLanguages: string[];
  supportsToolCalling: boolean;
}

export interface BenchmarkDto {
  modelId: string;
  benchmarkName: string;
  metric: string;
  shot: number;
  value: number;
  category: string;
}

export interface ServingPresetDto {
  modelId: string;
  backend: string;
  commandTemplate: string;
  sglangCommandTemplate: string;
  reasoningParser: string;
  toolCallParser: string;
  minGpuMemoryMb: number;
}

export interface CompleteRequestDto {
  modelId?: string;
  message: string;
}

export interface CompleteResponseDto {
  content: string;
}
