import { createContext, useContext, useState, useEffect, type ReactNode} from 'react';
import type { State } from '../types/State';
import { getStates } from '../services/api';

interface StatesContextType {
  states: State[];
  loading: boolean;
  error: string | null;
}

const StatesContext = createContext<StatesContextType>({
  states: [],
  loading: false,
  error: null,
});

export const useStates = () => useContext(StatesContext);

export const StatesProvider = ({ children }: { children: ReactNode }) => {
  const [states, setStates] = useState<State[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    setLoading(true);
    getStates()
      .then(data => setStates(data))
      .catch(() => setError('Erro ao buscar estados.'))
      .finally(() => setLoading(false));
  }, []);

  return (
    <StatesContext.Provider value={{ states, loading, error }}>
      {children}
    </StatesContext.Provider>
  );
}; 