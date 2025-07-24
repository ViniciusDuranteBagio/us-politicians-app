import React from 'react';
import PaginationButton from './PaginationButton';
import type { PaginationData } from '../types/Pagination';

interface PaginationControlsProps {
  pagination: PaginationData;
  loading?: boolean;
  onNext: () => void;
  onPrevious: () => void;
}

const PaginationControls: React.FC<PaginationControlsProps> = ({
  pagination,
  loading,
  onNext,
  onPrevious,
}) => (
  <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mt-8">
    <PaginationButton
      onClick={onPrevious}
      disabled={!pagination.hasPrevious || loading}
    >
      Página anterior
    </PaginationButton>
    <span className="text-blue-900 font-bold">
      Página {pagination.currentPage} de {pagination.totalPages}
    </span>
    <PaginationButton
      onClick={onNext}
      disabled={!pagination.hasNext || loading}
    >
      Próxima página
    </PaginationButton>
  </div>
);

export default PaginationControls; 