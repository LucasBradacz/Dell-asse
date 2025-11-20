import { useState } from 'react';
import { Upload, Check, X, Eye } from 'lucide-react';

const UploadLogo = () => {
  const [logo, setLogo] = useState(null);

  return (
    <div className="space-y-6">
      <h2 className="castello-title text-3xl">
        Painel do Administrador &gt; Upload de imagem da logo
      </h2>

      <div className="max-w-2xl space-y-6">
        <div className="castello-card p-6">
          <div className="space-y-4">
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

            {logo && (
              <div className="mt-4 p-4 bg-gray-50 rounded-lg border-2 border-dashed border-gray-300">
                <img
                  src={logo}
                  alt="Logo preview"
                  className="max-w-xs mx-auto"
                />
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default UploadLogo;

