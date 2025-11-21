import { useState, useRef } from 'react';
import { Upload, Check, X, Eye } from 'lucide-react';
import { useNavigate } from 'react-router-dom';
import { partyService } from '../../services/partyService';

const AddParty = () => {
  const navigate = useNavigate();
  const fileInputRef = useRef(null);
  
  const [loading, setLoading] = useState(false);
  const [preview, setPreview] = useState(null);

  // Estado do formulário combinando com o que o backend espera (PartyCreateRequest)
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    generateBudget: '', // Obrigatório no backend
    observations: '',
    imageURL: '', // Vamos mandar a imagem em Base64 aqui
    products: []  // Obrigatório ser uma lista (mesmo que vazia) para não dar erro no Java
  });

  // Atualiza os campos de texto
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  // Lógica de Upload de Imagem (Base64)
  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result);
        setFormData(prev => ({ ...prev, imageURL: reader.result }));
      };
      reader.readAsDataURL(file);
    }
  };

  const handleUploadClick = () => {
    fileInputRef.current.click();
  };

  // Enviar para o Backend
  const handleSubmit = async () => {
    // Validação simples
    if (!formData.title || !formData.description || !formData.generateBudget) {
      alert("Por favor, preencha os campos obrigatórios (Título, Descrição e Orçamento).");
      return;
    }

    setLoading(true);

    try {
      const payload = {
        ...formData,
        // Converte orçamento para número (o input devolve string)
        generateBudget: parseFloat(formData.generateBudget),
        // Garante que products seja um array (vazio por enquanto)
        products: [] 
      };

      await partyService.create(payload);
      
      alert('Festa criada com sucesso!');
      navigate('/galeria'); // Redireciona para a galeria para ver o resultado
    } catch (error) {
      console.error(error);
      alert('Erro ao criar festa. Verifique o console ou se o orçamento é válido.');
    } finally {
      setLoading(false);
    }
  };

  const handleDiscard = () => {
    if (window.confirm("Deseja limpar o formulário?")) {
        setFormData({
            title: '',
            description: '',
            generateBudget: '',
            observations: '',
            imageURL: '',
            products: []
        });
        setPreview(null);
    }
  };

  return (
    <div className="space-y-6">
      <h2 className="castello-title text-3xl">
        Painel do Administrador &gt; Adicionar festa
      </h2>

      <div className="max-w-4xl space-y-6">
        
        {/* Input invisível para o upload */}
        <input
            type="file"
            ref={fileInputRef}
            onChange={handleFileChange}
            accept="image/*"
            className="hidden"
        />

        {/* Título */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Insira um título *
          </label>
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-castello-red"
            placeholder="Ex: Festa do Homem Aranha"
          />
        </div>

        {/* Orçamento (Novo campo obrigatório) */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Orçamento Base (R$) *
          </label>
          <input
            type="number"
            name="generateBudget"
            value={formData.generateBudget}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-castello-red"
            placeholder="Ex: 1500.00"
          />
        </div>

        {/* Imagem / Fotos */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Imagem de Capa
          </label>
          <div className="flex items-start space-x-4">
            <div className="flex space-x-2">
                <button 
                    onClick={handleUploadClick}
                    className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition flex items-center space-x-2"
                >
                <Upload size={18} />
                <span>Upload</span>
                </button>
                {/* Botão Ver (Preview) */}
                {preview && (
                    <button className="px-4 py-2 bg-blue-400 text-white rounded-lg hover:bg-blue-500 transition flex items-center space-x-2 cursor-default">
                        <Eye size={18} />
                        <span>Imagem carregada</span>
                    </button>
                )}
            </div>
            
            {/* Preview da Imagem */}
            {preview && (
                <div className="h-24 w-24 border rounded-lg overflow-hidden bg-gray-100">
                    <img src={preview} alt="Preview" className="w-full h-full object-cover" />
                </div>
            )}
          </div>
        </div>

        {/* Descrição */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Insira a descrição *
          </label>
          <div className="relative">
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Descreva os detalhes da festa..."
              maxLength={500}
              rows={6}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-castello-red resize-none"
            />
            <div className="absolute bottom-2 right-2 text-xs text-gray-500">
              {formData.description.length}/500
            </div>
          </div>
        </div>

        {/* Observações (Opcional) */}
        <div>
          <label className="block font-semibold text-gray-900 mb-2">
            Observações
          </label>
          <textarea
            name="observations"
            value={formData.observations}
            onChange={handleChange}
            placeholder="Observações internas..."
            rows={3}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-50 focus:outline-none focus:ring-2 focus:ring-castello-red resize-none"
          />
        </div>

        {/* Action Buttons */}
        <div className="flex justify-end space-x-4">
          <button 
            onClick={handleSubmit}
            disabled={loading}
            className="px-8 py-3 bg-green-400 text-white rounded-lg hover:bg-green-500 transition flex items-center space-x-2 disabled:opacity-50"
          >
            {loading ? (
                <span>Salvando...</span>
            ) : (
                <>
                    <Check size={18} />
                    <span>Confirmar e Salvar</span>
                </>
            )}
          </button>
          
          <button 
            onClick={handleDiscard}
            className="px-8 py-3 bg-red-300 text-white rounded-lg hover:bg-red-400 transition flex items-center space-x-2"
          >
            <X size={18} />
            <span>Descartar</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddParty;