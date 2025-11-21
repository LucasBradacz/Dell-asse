import { useState, useEffect } from 'react';
import { Filter, Edit, Heart, ChevronLeft, ChevronRight } from 'lucide-react';
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
  }, []);

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
    return <div className="text-center py-10">Carregando galeria...</div>;
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <h1 className="castello-title text-4xl">Galeria</h1>
        <button className="flex items-center space-x-2 px-4 py-2 bg-gray-200 border border-gray-300 rounded-lg hover:bg-gray-300 transition">
          <Filter size={18} />
          <span>Filtro</span>
        </button>
      </div>

      {/* Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {items.map((item) => {
          // Identificador único (usa id ou nome se não tiver id)
          const itemId = item.id || item.title || item.name;
          const currentIndex = currentImageIndex[itemId] || 0;
          const totalImages = getImageCount(item);
          const currentImageUrl = getItemImage(item, currentIndex);
          
          // Título da festa ou nome do item
          const displayTitle = item.title || item.name || "Sem Título";

          return (
            <div key={itemId} className="castello-card overflow-hidden flex flex-col h-full">
              
              <div className="grid grid-cols-2 h-full">
                
                {/* Área da Imagem */}
                <div className="relative h-full min-h-[16rem] bg-gradient-to-br from-blue-200 via-red-200 to-yellow-200 flex items-center justify-center group">
                  
                  {currentImageUrl ? (
                     <img 
                       src={currentImageUrl} 
                       alt={displayTitle} 
                       className="absolute inset-0 w-full h-full object-cover transition-transform duration-500 group-hover:scale-105"
                       onError={(e) => {
                         e.target.onerror = null;
                         // Imagem de fallback caso o link esteja quebrado
                         e.target.src = "https://placehold.co/400x300?text=Imagem+Indisponível";
                       }}
                     />
                  ) : (
                    <div className="text-center z-10">
                      <div className="text-5xl mb-2">🎉</div>
                      <div className="text-sm font-semibold text-gray-700 px-2">
                        {displayTitle}
                      </div>
                    </div>
                  )}

                  {/* Setas (só aparecem se houver mais de 1 imagem) */}
                  {totalImages > 1 && (
                    <>
                      <button
                        onClick={() => prevImage(itemId, totalImages)}
                        className="absolute left-2 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-1 z-20 shadow-md opacity-0 group-hover:opacity-100 transition-opacity"
                      >
                        <ChevronLeft size={20} />
                      </button>
                      <button
                        onClick={() => nextImage(itemId, totalImages)}
                        className="absolute right-2 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-1 z-20 shadow-md opacity-0 group-hover:opacity-100 transition-opacity"
                      >
                        <ChevronRight size={20} />
                      </button>
                      
                      <div className="absolute bottom-2 left-1/2 -translate-x-1/2 bg-black/50 px-2 py-1 rounded text-white text-xs">
                        {currentIndex + 1} / {totalImages}
                      </div>
                    </>
                  )}
                </div>

                {/* Conteúdo */}
                <div className="p-4 flex flex-col justify-between h-full">
                  <div>
                    <h3 className="font-bold text-lg text-gray-900 mb-1">
                      {displayTitle}
                    </h3>
                    <p className="text-sm text-gray-600 mb-3 line-clamp-4">
                      {item.description}
                    </p>
                    
                    {/* Lista de itens (Opcional: mostra produtos se houver) */}
                    {item.products && item.products.length > 0 && (
                        <ul className="text-xs text-gray-500 list-disc list-inside mb-3">
                            {item.products.slice(0, 3).map((p, i) => (
                                <li key={i}>{p.name}</li>
                            ))}
                            {item.products.length > 3 && <li>...e mais</li>}
                        </ul>
                    )}
                  </div>

                  {/* Botões */}
                  <div className="space-y-2">
                    {isAuthenticated() && (
                      <button 
                      onClick={() => navigate('/criar-festa', { state: { partyData: item } })}
                        className="w-full px-3 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition text-sm shadow-sm">
                        Quero editar
                      </button>
                    )}

                    <div className="flex space-x-2">
                      {hasRole('ADMIN') && (
                          <button className="flex-1 px-3 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition text-sm flex items-center justify-center space-x-1 shadow-sm">
                          <Edit size={14} />
                          <span>Editar</span>
                          </button>
                      )}
                      
                      <button className="flex-1 px-3 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition text-sm flex items-center justify-center space-x-1 shadow-sm">
                        <Heart size={14} />
                        <span>Tenho interesse</span>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Gallery;