import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { StatesProvider } from './contexts/StatesContext';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <StatesProvider>
      <App />
    </StatesProvider>
  </StrictMode>,
)
