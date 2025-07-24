import React from 'react';
import type { State } from '../types/State';

interface FilterBarProps {
  states: State[];
  selectedState: number | null;
  onStateChange: (id: number) => void;
  selectedParty: string | null;
  onPartyChange: (party: string) => void;
  onSearch: () => void;
  loading?: boolean;
}

const FIXED_PARTIES = [
  'Democratic',
  'Republican',
  'Libertarian',
  'Green',
  'Independent',
  'Constitution',
  'Working Families',
];

const FilterBar: React.FC<FilterBarProps> = ({
  states,
  selectedState,
  onStateChange,
  selectedParty,
  onPartyChange,
  onSearch,
  loading,
}) => {
  return (
    <form
      className="flex flex-col gap-4 w-full"
      onSubmit={e => {
        e.preventDefault();
        onSearch();
      }}
    >
      <div className="flex flex-col md:flex-row gap-4 w-full">
        <div className="relative w-full md:w-1/2">
          <span className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 pointer-events-none">🌎</span>
          <select
            className="appearance-none w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-300 text-gray-700"
            value={selectedState ?? ''}
            onChange={e => onStateChange(Number(e.target.value))}
          >
            <option value="" >Selecione um estado</option>
            {states.map(state => (
              <option key={state.id} value={state.id}>{state.name}</option>
            ))}
          </select>
        </div>
        <div className="relative w-full md:w-1/2 flex items-center">
          <span className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400 pointer-events-none">🏛️</span>
          <select
            className="appearance-none w-full pl-10 pr-10 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-blue-300 text-gray-700"
            value={selectedParty ?? ''}
            onChange={e => onPartyChange(e.target.value)}
          >
            <option value="" >Selecione um partido</option>
            {FIXED_PARTIES.map(party => (
              <option key={party} value={party}>{party}</option>
            ))}
          </select>
          {selectedParty && (
            <button
              type="button"
              className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-red-500 focus:outline-none"
              onClick={() => onPartyChange('')}
              aria-label="Limpar filtro de partido"
            >
              ×
            </button>
          )}
        </div>
      </div>
      <button
        type="submit"
        className="mt-2 w-full md:w-auto px-6 py-2 bg-blue-700 text-white rounded-lg font-semibold shadow hover:bg-blue-800 transition disabled:opacity-60"
        disabled={loading}
      >
        {loading ? 'Pesquisando...' : 'Pesquisar'}
      </button>
    </form>
  );
};

export default FilterBar; 