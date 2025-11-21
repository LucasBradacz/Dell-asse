import { useState } from 'react';
import { ChevronLeft, ChevronRight, Edit, Heart } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

const Home = () => {
  const { hasRole } = useAuth(); 
  const [currentImage, setCurrentImage] = useState(0);

  const carouselImages = [
    { id: 1, title: 'Valentine', theme: 'valentine' },
    { id: 2, title: 'Paw Patrol', theme: 'paw-patrol' },
    { id: 3, title: 'Spider-Man', theme: 'spider-man' },
  ];

  const nextImage = () => {
    setCurrentImage((prev) => (prev + 1) % carouselImages.length);
  };

  const prevImage = () => {
    setCurrentImage((prev) => (prev - 1 + carouselImages.length) % carouselImages.length);
  };

  return (
    <div className="space-y-6">
      {/* Page Title */}
      <h1 className="castello-title text-4xl mb-6">Início</h1>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Left Column - Sobre nós e Carrossel */}
        <div className="lg:col-span-2 space-y-6">
          {/* Sobre nós Section */}
          <div className="castello-card p-6">
            <h2 className="castello-title text-3xl mb-4">Sobre nós</h2>
            <p className="text-gray-700 leading-relaxed">
              No Castello Del'asse, a história encontra os eventos. Somos uma plataforma
              que combina a sofisticação de um castelo histórico com a tecnologia moderna
              para criar eventos inesquecíveis. Nossa missão é tornar o processo de
              planejamento de festas transparente, simples e acessível. Seja para um
              casamento elegante, uma festa de aniversário animada ou um debutante
              memorável, oferecemos uma experiência única onde você pode visualizar
              orçamentos em tempo real, personalizar cada detalhe e ter controle total
              sobre o seu evento. Acreditamos que planejar uma festa deve ser tranquilo e
              permitir mais tempo para sonhar. No Castello Del'asse, transformamos seus
              sonhos em realidade.
            </p>
          </div>

          {/* Image Carousel */}
          <div className="castello-card p-4 relative">
            <div className="relative h-96 bg-gray-200 rounded-lg overflow-hidden">
              {/* Placeholder para imagens - você pode substituir por imagens reais */}
              <div className="absolute inset-0 flex items-center justify-center bg-gradient-to-br from-pink-200 via-blue-200 to-red-200">
                <div className="text-center">
                  <div className="text-6xl mb-4">🎉</div>
                  <div className="text-2xl font-bold text-gray-700">
                    {carouselImages[currentImage].title}
                  </div>
                </div>
              </div>

              {/* Navigation Arrows */}
              <button
                onClick={prevImage}
                className="absolute left-4 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-2 shadow-lg transition"
              >
                <ChevronLeft size={24} className="text-gray-700" />
              </button>
              <button
                onClick={nextImage}
                className="absolute right-4 top-1/2 -translate-y-1/2 bg-white/80 hover:bg-white rounded-full p-2 shadow-lg transition"
              >
                <ChevronRight size={24} className="text-gray-700" />
              </button>

              {/* Dots Indicator */}
              <div className="absolute bottom-4 left-1/2 -translate-x-1/2 flex space-x-2">
                {carouselImages.map((_, index) => (
                  <button
                    key={index}
                    onClick={() => setCurrentImage(index)}
                    className={`w-2 h-2 rounded-full transition ${
                      index === currentImage ? 'bg-white' : 'bg-white/50'
                    }`}
                  />
                ))}
              </div>
            </div>
          </div>
        </div>

        {/* Right Column - Festa da Masha Card */}
        <div className="lg:col-span-1">
          <div className="castello-card p-6 h-full">
            <h3 className="text-xl font-bold text-gray-900 mb-2">Festa da Masha</h3>
            <p className="text-sm text-gray-600 mb-4">
              Festa de 8 anos cedida no castelo
            </p>

            {/* Items List */}
            <ul className="space-y-2 mb-6 text-sm text-gray-700">
              <li>• 4 mesas</li>
              <li>• 16 cadeiras</li>
              <li>• Comida</li>
              <li>• Painel</li>
              <li>• Blablabla</li>
            </ul>

            {/* Image Placeholder */}
            <div className="w-full h-48 bg-gradient-to-br from-pink-100 to-purple-100 rounded-lg mb-4 flex items-center justify-center">
              <div className="text-center">
                <div className="text-4xl mb-2">🎂</div>
                <div className="text-sm text-gray-600">Masha Party</div>
              </div>
            </div>

            {/* Action Buttons */}
            <div className="space-y-3">
              {hasRole('ADMIN') && (
                <button className="w-full px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition flex items-center justify-center space-x-2">
                  <Edit size={18} />
                  <span>Editar</span>
                </button>
              )}
              <button className="w-full px-4 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition flex items-center justify-center space-x-2">
                <Heart size={18} />
                <span>Tenho interesse</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
