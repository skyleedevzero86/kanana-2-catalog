import { Link } from 'react-router-dom';
import type { ModelDto } from '@/types/model';

interface ModelCardProps {
  model: ModelDto;
}

export function ModelCard({ model }: ModelCardProps) {
  return (
    <article className="model-card">
      <div className="model-card-header">
        <h3>
          <Link to={`/models/${model.id}`}>{model.name}</Link>
        </h3>
        <span className={`variant variant-${model.variant.toLowerCase()}`}>{model.variant}</span>
      </div>
      <p className="model-spec-summary">
        {model.spec.totalParameters} params · Active {model.spec.activatedParameters} ·{' '}
        {model.spec.contextLength.toLocaleString()} context
      </p>
      {model.supportsToolCalling && (
        <span className="badge">Tool Calling</span>
      )}
      <a
        href={model.huggingFaceUrl}
        target="_blank"
        rel="noopener noreferrer"
        className="link-external"
      >
        Hugging Face →
      </a>
    </article>
  );
}
