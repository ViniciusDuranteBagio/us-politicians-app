import React from 'react';
import type { Politician } from '../types/Politician';

function getPartyColor(party: string) {
  if (party.toLowerCase().includes('democrat')) return 'bg-blue-100 text-blue-800';
  if (party.toLowerCase().includes('republican')) return 'bg-red-100 text-red-800';
  return 'bg-gray-200 text-gray-700';
}

interface PoliticianCardProps {
  politician: Politician;
}

const PoliticianCard: React.FC<PoliticianCardProps> = ({ politician }) => {
  return (
    <div className="bg-white rounded-2xl shadow-lg p-6 flex flex-col items-center text-center h-full transition-all duration-200">
      <img
        src={politician.photoUrl}
        alt={politician.name}
        className="w-24 h-24 rounded-full object-cover mb-4 border-4 border-blue-200 shadow"
        onError={(e) => (e.currentTarget.src = '/fallback.jpg')}
        loading="lazy"
      />
      <h2 className="font-bold text-xl mb-1 text-blue-900">{politician.name}</h2>
      <p className="text-sm text-gray-600 mb-1 font-medium">{politician.role}</p>
      <p className="text-sm text-gray-500 mb-1">{politician.stateName}</p>
      <span className={`inline-block mt-2 px-3 py-1 rounded-full text-xs font-semibold ${getPartyColor(politician.party)}`}>
        {politician.party}
      </span>
    </div>
  );
};

export default PoliticianCard; 