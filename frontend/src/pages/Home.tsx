import React, { useState } from 'react';
import FilterBar from '../components/FilterBar';
import PoliticianCard from '../components/PoliticianCard';
import PageTitle from '../components/PageTitle';
import PageDescription from '../components/PageDescription';
import PaginationControls from '../components/PaginationControls';
import type { PaginationData } from '../types/Pagination';
import type { Politician } from '../types/Politician';
import { getPoliticians } from '../services/api';
import { useStates } from '../contexts/StatesContext';

const Loader = () => (
  <div className="flex justify-center items-center py-10">
    <span className="w-8 h-8 border-4 border-blue-400 border-t-transparent rounded-full animate-spin"></span>
  </div>
);

const Home: React.FC = () => {
  const { states, loading: loadingStates, error: errorStates } = useStates();
  const [selectedState, setSelectedState] = useState<number | null>(null);
  const [selectedParty, setSelectedParty] = useState<string | null>(null);
  const [politicians, setPoliticians] = useState<Politician[]>([]);
  const [pagination, setPagination] = useState<PaginationData | null>(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [loadingPoliticians, setLoadingPoliticians] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSearch = async (page = 1) => {
    setError(null);
    setLoadingPoliticians(true);
    try {
      const res = await getPoliticians(selectedState ?? undefined, selectedParty ?? undefined, page);
      setPoliticians(res.data);
      setPagination(res.pagination);
      setCurrentPage(res.pagination.currentPage);
    } catch (err: any) {
      // Tenta ler a mensagem de erro da API
      let errorMsg = 'Erro ao buscar políticos.';
      if (err?.response?.status === 400 && err.response.data?.errorMessage) {
        errorMsg = err.response.data.errorMessage;
      }
      setError(errorMsg);
      setPoliticians([]);
      setPagination(null);
    } finally {
      setLoadingPoliticians(false);
    }
  };

  // Limpa a listagem ao limpar os filtros
  const handleStateChange = (id: number) => {
    setSelectedState(id || null);
    setPoliticians([]);
    setPagination(null);
    setCurrentPage(1);
  };
  const handlePartyChange = (party: string) => {
    setSelectedParty(party || null);
    setPoliticians([]);
    setPagination(null);
    setCurrentPage(1);
  };

  const handleNextPage = () => {
    if (pagination?.hasNext) {
      handleSearch((pagination.nextPage));
    }
  };
  const handlePreviousPage = () => {
    if (pagination?.hasPrevious) {
      handleSearch((pagination.previousPage));
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-100 to-blue-300 flex flex-col items-center py-8 px-2">
      <header className="w-full max-w-5xl mb-8">
        <div className="text-center">
          <PageTitle />
          <PageDescription />
        </div>
        <div className="w-full max-w-3xl mx-auto bg-white/90 rounded-2xl shadow-lg p-4 md:p-6 -mt-4 relative z-10">
          <FilterBar
            states={states}
            selectedState={selectedState}
            onStateChange={handleStateChange}
            selectedParty={selectedParty}
            onPartyChange={handlePartyChange}
            onSearch={() => handleSearch(1)}
            loading={loadingPoliticians}
          />
        </div>
        {(errorStates || error) && (
          <div className="mt-4 text-red-600 text-center font-semibold">{errorStates || error}</div>
        )}
      </header>
      {(loadingStates || loadingPoliticians) ? (
        <Loader />
      ) : (
        <main className="w-full max-w-5xl">
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8 mt-2">
            {politicians.length > 0 ? (
              politicians.map(politician => (
                <div key={politician.id} className="transition-transform hover:-translate-y-2 hover:shadow-2xl">
                  <PoliticianCard politician={politician} />
                </div>
              ))
            ) : (
              <div className="col-span-full text-center text-gray-500 py-10 text-lg">
                Selecione os filtros e clique em "Pesquisar" para ver os políticos.
              </div>
            )}
          </div>
          {pagination && (
            <PaginationControls
              pagination={pagination}
              loading={loadingPoliticians}
              onNext={handleNextPage}
              onPrevious={handlePreviousPage}
            />
          )}
        </main>
      )}
    </div>
  );
};

export default Home; 