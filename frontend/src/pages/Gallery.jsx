import { useState, useEffect } from 'react';
import { Filter, Edit, Heart, ChevronLeft, ChevronRight, LogIn } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';
import { partyService } from '../services/partyService'; 
import { useNavigate } from 'react-router-dom';

const Gallery = () => {
  const navigate = useNavigate();
  const { isAuthenticated, hasRole } = useAuth();
  const [currentImageIndex, setCurrentImageIndex] = useState({});
  
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchParties = async () => {
      if (!isAuthenticated()) {
        setLoading(false);
        return;
      }

      try {
        const data = await partyService.getAll(); 
        console.log("Festas carregadas:", data);
        setItems(data);
      } catch (error) {
        console.error("Erro ao buscar festas:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchParties();
  }, [isAuthenticated]); 

  // Funções de manipulação de imagem
  const getImageCount = (item) => {
    if (Array.isArray(item.imageUrl)) return item.imageUrl.length;
    return 1; 
  };

  const nextImage = (itemId, totalImages) => {
    if (totalImages <= 1) return;
    setCurrentImageIndex((prev) => ({
      ...prev,
      [itemId]: ((prev[itemId] || 0) + 1) % totalImages,
    }));
  };

  const prevImage = (itemId, totalImages) => {
    if (totalImages <= 1) return;
    setCurrentImageIndex((prev) => ({
      ...prev,
      [itemId]: ((prev[itemId] || 0) - 1 + totalImages) % totalImages,
    }));
  };

  const getItemImage = (item, index = 0) => {
    if (item.imgExample) return item.imgExample;
    if (Array.isArray(item.imageUrl) && item.imageUrl.length > 0) {
      const validIndex = index % item.imageUrl.length;
      return item.imageUrl[validIndex].url || item.imageUrl[validIndex];
    }
    if (typeof item.imageUrl === 'string') return item.imageUrl;
    return null;
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="text-castello-red font-bold animate-pulse">Carregando festas...</div>
      </div>
    );
  }

  if (!isAuthenticated()) {
    return (
      <div className="flex flex-col items-center justify-center py-20 text-center space-y-4">
        <div className="bg-gray-100 p-4 rounded-full">
          <LogIn size={48} className="text-gray-400" />
        </div>
        <h2 className="text-2xl font-bold text-gray-800">Acesso Restrito</h2>
        <p className="text-gray-600 max-w-md">
          Para visualizar as festas registradas (Galeria), você precisa estar logado em sua conta.
        </p>
        <button 
          onClick={() => navigate('/login')}
          className="px-6 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition font-bold"
        >
          Fazer Login
        </button>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <h1 className="castello-title text-4xl">
           {hasRole('ADMIN') ? 'Todas as Festas' : 'Minhas Festas'}
        </h1>
        <button className="flex items-center space-x-2 px-4 py-2 bg-gray-200 border border-gray-300 rounded-lg hover:bg-gray-300 transition">
          <Filter size={18} />
          <span>Filtro</span>
        </button>
      </div>

      {/* Estado Vazio (Logado mas sem festas) */}
      {items.length === 0 ? (
        <div className="text-center py-16 bg-gray-50 rounded-lg border border-dashed border-gray-300">
          <p className="text-gray-500 text-lg">Nenhuma festa encontrada.</p>
          {!hasRole('ADMIN') && (
             <button 
                onClick={() => navigate('/criar-festa')}
                className="mt-4 text-castello-red hover:underline font-bold"
             >
                Solicitar minha primeira festa
             </button>
          )}
        </div>
      ) : (
        /* Grid de Cards */
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {items.map((item) => {
            const itemId = item.id || item.title || item.name;
            const currentIndex = currentImageIndex[itemId] || 0;
            const totalImages = getImageCount(item);
            const currentImageUrl = getItemImage(item, currentIndex);
            const displayTitle = item.title || item.name || "Sem Título";

            return (
              <div key={itemId} className="castello-card overflow-hidden flex flex-col h-full animate-in fade-in duration-500">
                
                <div className="grid grid-cols-2 h-full">
                  {/* Área da Imagem */}
                  <div className="relative h-full min-h-[16rem] bg-gray-100 flex items-center justify-center group overflow-hidden">
                    {currentImageUrl ? (
                       <img 
                         src={currentImageUrl} 
                         alt={displayTitle} 
                         className="absolute inset-0 w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
                         onError={(e) => {
                           e.target.onerror = null;
                           e.target.src = "https://placehold.co/400x300?text=Sem+Imagem";
                         }}
                       />
                    ) : (
                      <div className="text-center z-10 p-4">
                        <span className="text-4xl block mb-2">🎉</span>
                        <span className="text-xs text-gray-500 font-medium">Sem prévia</span>
                      </div>
                    )}

                    {/* Controles de Carrossel */}
                    {totalImages > 1 && (
                      <>
                        <button onClick={() => prevImage(itemId, totalImages)} className="absolute left-2 top-1/2 -translate-y-1/2 bg-white/90 hover:bg-white rounded-full p-1.5 shadow-sm opacity-0 group-hover:opacity-100 transition-opacity">
                          <ChevronLeft size={18} />
                        </button>
                        <button onClick={() => nextImage(itemId, totalImages)} className="absolute right-2 top-1/2 -translate-y-1/2 bg-white/90 hover:bg-white rounded-full p-1.5 shadow-sm opacity-0 group-hover:opacity-100 transition-opacity">
                          <ChevronRight size={18} />
                        </button>
                        <div className="absolute bottom-2 bg-black/60 px-2 py-0.5 rounded text-white text-[10px]">
                          {currentIndex + 1}/{totalImages}
                        </div>
                      </>
                    )}
                    
                    {/* Status Badge (Adicional para contexto de 'Meus Pedidos') */}
                    {item.status && (
                        <div className="absolute top-2 left-2 bg-white/90 px-2 py-1 rounded text-xs font-bold shadow-sm uppercase tracking-wide text-gray-700">
                            {item.status}
                        </div>
                    )}
                  </div>

                  {/* Conteúdo */}
                  <div className="p-4 flex flex-col justify-between h-full bg-white">
                    <div>
                      <h3 className="font-bold text-lg text-gray-900 leading-tight mb-2">
                        {displayTitle}
                      </h3>
                      <p className="text-sm text-gray-600 mb-3 line-clamp-3">
                        {item.description || "Sem descrição definida."}
                      </p>
                      
                      {item.products && item.products.length > 0 && (
                          <div className="mb-3">
                              <span className="text-xs font-bold text-gray-400 uppercase">Inclui:</span>
                              <ul className="text-xs text-gray-500 list-disc list-inside">
                                  {item.products.slice(0, 2).map((p, i) => (
                                      <li key={i} className="truncate">{p.name}</li>
                                  ))}
                                  {item.products.length > 2 && <li>+{item.products.length - 2} itens</li>}
                              </ul>
                          </div>
                      )}
                    </div>

                    <div className="space-y-2 mt-2">
                      {isAuthenticated() && (
                        <button 
                        onClick={() => navigate('/criar-festa', { state: { partyData: item } })}
                          className="w-full px-3 py-2 bg-white border border-castello-red text-castello-red rounded-lg hover:bg-red-50 transition text-sm font-bold">
                          {hasRole('ADMIN') ? 'Ver Detalhes' : 'Editar/Refazer'}
                        </button>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default Gallery;