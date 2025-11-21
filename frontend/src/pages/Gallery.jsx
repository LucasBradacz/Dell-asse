import { useState, useEffect } from 'react'; 
import { Filter, Edit, Heart, ChevronLeft, ChevronRight } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';
import { partyService } from '../services/partyService';

const Gallery = () => {
  const { isAuthenticated, hasRole } = useAuth();
  const [currentImageIndex, setCurrentImageIndex] = useState({});
  
  const [parties, setParties] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchParties = async () => {
      try {
        const data = await partyService.getAll();
        setParties(data);
      } catch (error) {
        console.error("Erro ao buscar festas:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchParties();
  }, []);


  const nextImage = (partyId) => {
    setCurrentImageIndex((prev) => ({
      ...prev,
      [partyId]: ((prev[partyId] || 0) + 1) % 3,
    }));
  };

  const prevImage = (partyId) => {
    setCurrentImageIndex((prev) => ({
      ...prev,
      [partyId]: ((prev[partyId] || 0) - 1 + 3) % 3,
    }));
  };  

  if (loading) {
    return <div className="text-center py-10">Carregando festas...</div>;
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

      {/* Gallery Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {parties.map((party) => (
          <div key={party.id} className="castello-card overflow-hidden flex flex-col h-full">
            
            <div className="grid grid-cols-2 h-full">
              
              {/* Image Area */}
              <div className="relative h-full min-h-[16rem] bg-gradient-to-br from-blue-200 via-red-200 to-yellow-200 flex items-center justify-center">
                {/* Se o backend tiver imagem (imgExample), mostra ela, senão mostra o ícone padrão */}
                {party.imgExample ? (
                   <img 
                     src={party.imgExample} 
                     alt={party.title} 
                     className="absolute inset-0 w-full h-full object-cover"
                   />
                ) : (
                  <div className="text-center z-10">
                    <div className="text-5xl mb-2">🎉</div>
                    {/* Exibe o título vindo do banco */}
                    <div className="text-sm font-semibold text-gray-700 px-2">
                      {party.title}
                    </div>
                  </div>
                )}

                {/* Controles do carrossel (opcional se tiver só 1 imagem) */}
                <button
                  onClick={() => prevImage(party.id)}
                  className="absolute left-2 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-1 z-20"
                >
                  <ChevronLeft size={20} />
                </button>
                <button
                  onClick={() => nextImage(party.id)}
                  className="absolute right-2 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-1 z-20"
                >
                  <ChevronRight size={20} />
                </button>
              </div>

              {/* Content */}
              <div className="p-4 flex flex-col justify-between h-full">
                <div>
                  <h3 className="font-bold text-lg text-gray-900 mb-1">
                    {party.title}
                  </h3>
                  {/* Descrição vinda do backend */}
                  <p className="text-sm text-gray-600 mb-3 line-clamp-3">
                    {party.description}
                  </p>
                  
                  {/* Lista de produtos vinda do backend */}
                  <ul className="text-xs text-gray-700 space-y-1 mb-4">
                    {party.products && party.products.length > 0 ? (
                      party.products.slice(0, 5).map((prod) => (
                        <li key={prod.id}>• {prod.name}</li>
                      ))
                    ) : (
                      <li>• Nenhum item listado</li>
                    )}
                  </ul>
                </div>

                {/* Buttons */}
                <div className="space-y-2">
                  
                  {isAuthenticated() && (
                    <button className="w-full px-3 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition text-sm">
                      Quero editar
                    </button>
                  )}

                  <div className="flex space-x-2">
                    {hasRole('ADMIN') && (
                        <button className="flex-1 px-3 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition text-sm flex items-center justify-center space-x-1">
                        <Edit size={14} />
                        <span>Editar</span>
                        </button>
                    )}
                    
                    <button className="flex-1 px-3 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition text-sm flex items-center justify-center space-x-1">
                      <Heart size={14} />
                      <span>Tenho interesse</span>
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Gallery;