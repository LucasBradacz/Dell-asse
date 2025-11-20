import { useState } from 'react';
import { Upload, Check, X, Eye } from 'lucide-react';

const AddParty = () => {
  const [title, setTitle] = useState('Festa da Masha');
  const [description, setDescription] = useState('');
  const [photos, setPhotos] = useState([]);

  return (
    <div className="space-y-6">
      <h2 className="castello-title text-3xl">
        Painel do Administrador &gt; Adicionar festa
      </h2>

      <div className="max-w-4xl space-y-6">
        {/* Title Input */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Insira um título
          </label>
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-castello-red"
          />
        </div>

        {/* Photos Upload */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Insira todas as fotos
          </label>
          <div className="flex space-x-2">
            <button className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition flex items-center space-x-2">
              <Upload size={18} />
              <span>Upload</span>
            </button>
            <button className="px-4 py-2 bg-green-400 text-white rounded-lg hover:bg-green-500 transition flex items-center space-x-2">
              <Check size={18} />
              <span>Confirmar</span>
            </button>
            <button className="px-4 py-2 bg-red-300 text-white rounded-lg hover:bg-red-400 transition flex items-center space-x-2">
              <X size={18} />
              <span>Descartar</span>
            </button>
            <button className="px-4 py-2 bg-blue-400 text-white rounded-lg hover:bg-blue-500 transition flex items-center space-x-2">
              <Eye size={18} />
              <span>Ver</span>
            </button>
          </div>
        </div>

        {/* Description */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Insira a descrição
          </label>
          <div className="relative">
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Escreva aqui..."
              maxLength={500}
              rows={8}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-castello-red resize-none"
            />
            <div className="absolute bottom-2 right-2 text-xs text-gray-500">
              {description.length}/500
            </div>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="flex justify-end space-x-4">
          <button className="px-8 py-3 bg-green-400 text-white rounded-lg hover:bg-green-500 transition flex items-center space-x-2">
            <Check size={18} />
            <span>Confirmar</span>
          </button>
          <button className="px-8 py-3 bg-red-300 text-white rounded-lg hover:bg-red-400 transition flex items-center space-x-2">
            <X size={18} />
            <span>Descartar</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddParty;

