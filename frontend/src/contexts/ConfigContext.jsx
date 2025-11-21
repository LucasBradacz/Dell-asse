import { createContext, useContext, useState, useEffect } from 'react';

const ConfigContext = createContext(null);

export const useConfig = () => {
  const context = useContext(ConfigContext);
  if (!context) {
    throw new Error('useConfig deve ser usado dentro de ConfigProvider');
  }
  return context;
};

export const ConfigProvider = ({ children }) => {
  // Inicia o estado buscando do localStorage se existir
  const [logo, setLogo] = useState(() => {
    const savedLogo = localStorage.getItem('appLogo');
    return savedLogo || null;
  });

  // Função para atualizar a logo
  const updateLogo = (newLogo) => {
    setLogo(newLogo);
    if (newLogo) {
      localStorage.setItem('appLogo', newLogo);
    } else {
      localStorage.removeItem('appLogo');
    }
  };

  const value = {
    logo,
    updateLogo
  };

  return <ConfigContext.Provider value={value}>{children}</ConfigContext.Provider>;
};