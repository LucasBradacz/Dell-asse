import { useState } from 'react';
import { Filter, Edit, Heart, ChevronLeft, ChevronRight } from 'lucide-react';

const Gallery = () => {
  const [currentImageIndex, setCurrentImageIndex] = useState({});

  const parties = [
    {
      id: 1,
      title: 'Festa da Patrulha Canina',
      age: 4,
      items: ['4 mesas', '16 cadeiras', 'Comida', 'Painel', 'Blablabla'],
      theme: 'paw-patrol',
    },
    {
      id: 2,
      title: 'Festa da Masha',
      age: 4,
      items: ['4 mesas', '16 cadeiras', 'Comida', 'Painel', 'Blablabla'],
      theme: 'masha',
    },
    {
      id: 3,
      title: 'Festa do Homem Aranha',
      age: 10,
      items: ['4 mesas', '16 cadeiras', 'Comida', 'Painel', 'Blablabla'],
      theme: 'spider-man',
    },
    {
      id: 4,
      title: 'Festa da Moranguinho',
      age: 4,
      items: ['4 mesas', '16 cadeiras', 'Comida', 'Painel', 'Blablabla'],
      theme: 'strawberry',
    },
  ];

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
          <div key={party.id} className="castello-card overflow-hidden">
            <div className="grid grid-cols-2">
              {/* Image Carousel */}
              <div className="relative h-64 bg-gradient-to-br from-blue-200 via-red-200 to-yellow-200">
                <div className="absolute inset-0 flex items-center justify-center">
                  <div className="text-center">
                    <div className="text-5xl mb-2">🎉</div>
                    <div className="text-sm font-semibold text-gray-700">
                      {party.title}
                    </div>
                  </div>
                </div>

                {/* Carousel Controls */}
                <button
                  onClick={() => prevImage(party.id)}
                  className="absolute left-2 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-1"
                >
                  <ChevronLeft size={20} />
                </button>
                <button
                  onClick={() => nextImage(party.id)}
                  className="absolute right-2 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-1"
                >
                  <ChevronRight size={20} />
                </button>
              </div>

              {/* Content */}
              <div className="p-4 flex flex-col justify-between">
                <div>
                  <h3 className="font-bold text-lg text-gray-900 mb-1">
                    {party.title}
                  </h3>
                  <p className="text-sm text-gray-600 mb-3">
                    Festa de {party.age} anos cedida no castelo
                  </p>
                  <ul className="text-xs text-gray-700 space-y-1 mb-4">
                    {party.items.map((item, index) => (
                      <li key={index}>• {item}</li>
                    ))}
                  </ul>
                </div>

                {/* Buttons */}
                <div className="space-y-2">
                  <button className="w-full px-3 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition text-sm">
                    Quero editar
                  </button>
                  <div className="flex space-x-2">
                    <button className="flex-1 px-3 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition text-sm flex items-center justify-center space-x-1">
                      <Edit size={14} />
                      <span>Editar</span>
                    </button>
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

