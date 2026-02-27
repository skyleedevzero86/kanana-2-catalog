import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { Layout } from '@/components/Layout';
import HomePage from '@/pages/HomePage';
import ModelListPage from '@/pages/ModelListPage';
import ModelDetailPage from '@/pages/ModelDetailPage';
import CompletePage from '@/pages/CompletePage';

export default function App() {
  return (
    <BrowserRouter future={{ v7_startTransition: true, v7_relativeSplatPath: true }}>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<HomePage />} />
          <Route path="models" element={<ModelListPage />} />
          <Route path="models/:id" element={<ModelDetailPage />} />
          <Route path="complete" element={<CompletePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
