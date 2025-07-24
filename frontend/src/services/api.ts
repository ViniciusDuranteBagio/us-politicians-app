import axios from "axios";
import type { State } from "../types/State";
import type { Politician } from "../types/Politician";
import type { PaginationData } from "../types/Pagination";

const API_BASE_URL = "http://localhost:8080/api";

export async function getStates(): Promise<State[]> {
  const res = await axios.get(`${API_BASE_URL}/states/all`);
  return res.data.data;
}

export interface PoliticiansResponse {
  data: Politician[];
  pagination: PaginationData;
}

export async function getPoliticians(stateId?: number, party?: string, page?: number): Promise<PoliticiansResponse> {
  const params: any = {};
  if (stateId) params.state = stateId;
  if (party) params.party = party;
  if (page) params.currentPage = page;
  const res = await axios.get(`${API_BASE_URL}/politicians`, {
    params
  });
  return res.data;
} 