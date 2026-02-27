import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
} from 'recharts';
import type { BenchmarkDto } from '@/types/model';

interface BenchmarkChartProps {
  data: BenchmarkDto[];
  title?: string;
}

export function BenchmarkChart({ data, title = '벤치마크 점수' }: BenchmarkChartProps) {
  const chartData = data.map((b) => ({
    name: b.benchmarkName,
    value: b.value,
    category: b.category,
  }));

  if (chartData.length === 0) {
    return (
      <div className="chart-empty">
        <p>표시할 벤치마크가 없습니다.</p>
      </div>
    );
  }

  return (
    <div className="benchmark-chart">
      {title && <h3 className="chart-title">{title}</h3>}
      <ResponsiveContainer width="100%" height={320}>
        <BarChart data={chartData} margin={{ top: 8, right: 16, left: 8, bottom: 24 }}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" tick={{ fontSize: 11 }} angle={-20} textAnchor="end" />
          <YAxis domain={[0, 100]} tick={{ fontSize: 12 }} />
          <Tooltip
            formatter={(value: number) => [value.toFixed(2), '점수']}
            labelFormatter={(label, payload) =>
              payload[0]?.payload?.category ? `${label} (${payload[0].payload.category})` : label
            }
          />
          <Bar dataKey="value" fill="var(--color-primary)" radius={[4, 4, 0, 0]} name="점수" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
}
