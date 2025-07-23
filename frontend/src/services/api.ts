import axios from "axios";
import type { State } from "../types/State";
import type { Politician } from "../types/Politician";

const API_BASE_URL = "http://localhost:8080/api";

export async function getStates(): Promise<State[]> {
  const res = await axios.get(`${API_BASE_URL}/states/all`);
  return res.data.data;
}

export async function getPoliticians(stateId: number, party?: string): Promise<Politician[]> {
  const params: any = {};
  if (party) params.party = party;
  const res = await axios.get(`${API_BASE_URL}/politicians`, {
    params: { state: stateId, ...params }
  });
  return res.data.data;  
} 