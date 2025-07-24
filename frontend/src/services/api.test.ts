import axios from 'axios';
import { getStates, getPoliticians } from './api';
import type { State } from '../types/State';
import type { Politician } from '../types/Politician';

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

describe('API Service', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  it('deve buscar todos os estados', async () => {
    const mockStates: State[] = [
      { id: 1, externalId: 'ext-1', name: 'Alabama', updatedAt: '2025-07-21T07:24:06.762557' },
      { id: 2, externalId: 'ext-2', name: 'Alaska', updatedAt: '2025-07-21T07:24:06.780924' }
    ];
    mockedAxios.get.mockResolvedValueOnce({ data: { data: mockStates } });
    const result = await getStates();
    expect(result).toEqual(mockStates);
    expect(mockedAxios.get).toHaveBeenCalledWith('http://localhost:8080/api/states/all');
  });

  it('deve buscar políticos de um estado', async () => {
    const mockPoliticians: Politician[] = [
      { id: 1, externalId: 'ext-p1', name: 'John Doe', party: 'Democratic', role: 'Senator', stateName: 'Alabama', photoUrl: 'url', updatedAt: '2025-07-21T07:24:06.762557' }
    ];
    mockedAxios.get.mockResolvedValueOnce({ data: { data: mockPoliticians } });
    const result = await getPoliticians(1);
    expect(result).toEqual(mockPoliticians);
    expect(mockedAxios.get).toHaveBeenCalledWith('http://localhost:8080/api/politicians', { params: { state: 1 } });
  });
}); 