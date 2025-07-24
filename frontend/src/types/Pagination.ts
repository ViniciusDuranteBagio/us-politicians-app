export interface PaginationData {
  currentPage: number;
  totalPages: number;
  hasNext: boolean;
  hasPrevious: boolean;
  perPage?: number;
  totalItems?: number;
  nextPage?: number;
  previousPage?: number;
  from?: number;
  to?: number;
} 