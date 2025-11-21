import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { Plus, Minus, Send, Image as ImageIcon, DollarSign, ArrowLeft, Trash2 } from 'lucide-react';
import { partyService } from '../services/partyService';

const CreateCustomParty = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [loading, setLoading] = useState(false);
  const [showUrlInput, setShowUrlInput] = useState(false);

  // Estado dos itens (agora começa vazio)
  const [items, setItems] = useState([]);
  
  const [formData, setFormData] = useState({
    title: 'Minha Festa Personalizada',
    description: '',
    generateBudget: '', // Começa vazio para facilitar edição
    observations: '',
    imageURL: '',
    products: []
  });

  // Carregar dados se vier da Galeria
  useEffect(() => {
    if (location.state && location.state.partyData) {
      const sourceParty = location.state.partyData;
      
      setFormData({
        title: `Personalizando: ${sourceParty.title || sourceParty.name}`,
        description: sourceParty.description || '',
        generateBudget: sourceParty.generateBudget || '',
        observations: '',
        imageURL: getImageUrl(sourceParty), 
        products: []
      });

      // Mapeia produtos existentes para itens editáveis
      if (sourceParty.products && sourceParty.products.length > 0) {
        const mappedItems = sourceParty.products.map(prod => ({
            id: Date.now() + Math.random(), // ID temporário único
            name: prod.name,
            quantity: 1, // Assume 1 se não tiver quantidade definida no backend
            isFixed: false // Permite editar nome se quiser
        }));
        setItems(mappedItems);
      }
    }
  }, [location]);

  const getImageUrl = (party) => {
    if (party.imgExample) return party.imgExample;
    if (Array.isArray(party.imageUrl) && party.imageUrl.length > 0) return party.imageUrl[0].url || party.imageUrl[0];
    if (typeof party.imageUrl === 'string') return party.imageUrl;
    return '';
  };

  // Adicionar novo item na lista
  const handleAddItem = () => {
    setItems([
        ...items, 
        { id: Date.now(), name: '', quantity: 1, isNew: true }
    ]);
  };

  // Remover item
  const handleRemoveItem = (id) => {
    setItems(items.filter(item => item.id !== id));
  };

  // Atualizar quantidade
  const handleQuantityChange = (id, delta) => {
    setItems(items.map(item => 
      item.id === id ? { ...item, quantity: Math.max(1, item.quantity + delta) } : item
    ));
  };

  // Atualizar nome do item
  const handleNameChange = (id, newName) => {
    setItems(items.map(item => 
      item.id === id ? { ...item, name: newName } : item
    ));
  };

  const handleSubmit = async () => {
    setLoading(true);
    try {
      // Filtra itens vazios
      const validItems = items.filter(i => i.name.trim() !== '');
      
      let itemsDescription = "Nenhum item específico listado.";
      if (validItems.length > 0) {
        itemsDescription = validItems.map(i => `${i.quantity}x ${i.name}`).join(', ');
      }

      const payload = {
        ...formData,
        // Garante que a descrição contenha os itens
        description: `[Lista de Itens: ${itemsDescription}] \n\nDetalhes: ${formData.description}`,
        // Converte o orçamento para número (se vazio vira 0)
        generateBudget: formData.generateBudget ? parseFloat(formData.generateBudget) : 0,
        products: [] 
      };

      await partyService.create(payload);
      alert('Solicitação enviada com sucesso!');
      navigate('/carrinho');
    } catch (error) {
      console.error(error);
      alert('Erro ao enviar solicitação.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center space-x-4 mb-6">
        <button onClick={() => navigate('/carrinho')} className="p-2 hover:bg-gray-200 rounded-full transition">
            <ArrowLeft size={24} />
        </button>
        <h1 className="castello-title text-3xl">
            {location.state?.partyData ? 'Editar Festa' : 'Solicitar Festa Personalizada'}
        </h1>
      </div>

      <div className="castello-card p-8 max-w-5xl mx-auto">
        <div className="flex flex-col lg:flex-row gap-8">
            
          {/* Coluna Esquerda: Formulário Principal */}
          <div className="flex-1 space-y-6">
            
            {/* Título */}
            <div>
                <label className="block font-bold text-gray-900 mb-2">Nome da Festa</label>
                <input 
                    type="text" 
                    value={formData.title}
                    onChange={(e) => setFormData({...formData, title: e.target.value})}
                    className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-castello-red outline-none"
                    placeholder="Ex: Aniversário de 15 anos"
                />
            </div>

            {/* Lista de Itens */}
            <div className="bg-gray-50 p-4 rounded-lg border border-gray-200">
                <h3 className="font-bold mb-4 text-gray-700">Itens da Festa</h3>
                
                {items.length === 0 ? (
                    <p className="text-sm text-gray-500 text-center py-4 italic">
                        Nenhum item adicionado. Adicione produtos abaixo.
                    </p>
                ) : (
                    <div className="space-y-3">
                        {items.map((item) => (
                            <div key={item.id} className="flex items-center gap-3 bg-white p-2 rounded border border-gray-100 shadow-sm">
                                {/* Campo de Nome Editável */}
                                <input 
                                    type="text" 
                                    value={item.name}
                                    onChange={(e) => handleNameChange(item.id, e.target.value)}
                                    placeholder="Nome do item (ex: Mesas)"
                                    className="flex-1 px-2 py-1 border-b border-transparent focus:border-castello-red outline-none text-gray-700"
                                />
                                
                                {/* Controles de Quantidade */}
                                <div className="flex items-center space-x-2">
                                    <button 
                                        onClick={() => handleQuantityChange(item.id, -1)}
                                        className="w-6 h-6 bg-gray-100 rounded flex items-center justify-center hover:bg-gray-200 text-gray-600"
                                    >
                                        <Minus size={12} />
                                    </button>
                                    <span className="w-6 text-center font-bold text-sm">{item.quantity}</span>
                                    <button 
                                        onClick={() => handleQuantityChange(item.id, 1)}
                                        className="w-6 h-6 bg-gray-100 rounded flex items-center justify-center hover:bg-gray-200 text-gray-600"
                                    >
                                        <Plus size={12} />
                                    </button>
                                </div>

                                {/* Botão Remover */}
                                <button 
                                    onClick={() => handleRemoveItem(item.id)}
                                    className="p-1 text-red-400 hover:text-red-600 hover:bg-red-50 rounded"
                                >
                                    <Trash2 size={16} />
                                </button>
                            </div>
                        ))}
                    </div>
                )}

                <div className="pt-4 flex justify-center">
                    <button 
                        onClick={handleAddItem}
                        className="text-sm text-castello-dark font-semibold hover:text-castello-red flex items-center gap-1 border border-dashed border-gray-400 px-4 py-2 rounded hover:border-castello-red transition"
                    >
                        <Plus size={14} /> Adicionar produto
                    </button>
                </div>
            </div>
          </div>

          {/* Coluna Direita: Orçamento e Ações */}
          <div className="lg:w-72 flex flex-col space-y-4">
            
            <button 
                onClick={handleSubmit}
                disabled={loading}
                className="w-full py-3 bg-castello-red text-white rounded-lg hover:bg-red-800 transition flex items-center justify-center space-x-2 font-bold shadow-md"
            >
                {loading ? <span>Enviando...</span> : (
                    <>
                        <Send size={18} />
                        <span>Enviar Solicitação</span>
                    </>
                )}
            </button>
            
            {/* Botão para adicionar URL de imagem */}
            {!showUrlInput && !formData.imageURL && (
                <button 
                    onClick={() => setShowUrlInput(true)}
                    className="w-full py-3 bg-white border-2 border-castello-red text-castello-red rounded-lg hover:bg-red-50 transition flex items-center justify-center space-x-2 font-bold shadow-sm"
                >
                    <ImageIcon size={18} />
                    <span>Adicionar imagem</span>
                </button>
            )}

            {/* Campo de Orçamento (Simplificado e Editável) */}
            <div className="bg-white border-2 border-castello-red rounded-lg p-3 shadow-sm">
                <div className="flex items-center justify-between mb-1">
                    <label className="text-xs font-bold text-castello-red uppercase flex items-center gap-1">
                        <DollarSign size={14} />
                        Orçamento Estimado
                    </label>
                </div>
                <input 
                    type="number" 
                    value={formData.generateBudget}
                    onChange={(e) => setFormData({...formData, generateBudget: e.target.value})}
                    className="w-full text-2xl font-bold text-gray-800 border-b border-gray-300 focus:border-castello-red outline-none py-1"
                    placeholder="0,00"
                />
                <p className="text-[10px] text-gray-500 mt-1">
                    Informe quanto pretende investir (opcional)
                </p>
            </div>

            {/* Input de URL da imagem */}
            {showUrlInput && (
                <div className="bg-white p-2 border border-gray-300 rounded-lg animate-in fade-in slide-in-from-top-2">
                    <label className="text-xs text-gray-500 mb-1 block">Cole o link da imagem:</label>
                    <div className="flex gap-2">
                        <input 
                            type="text"
                            placeholder="https://..."
                            className="flex-1 border border-gray-300 rounded px-2 py-1 text-sm focus:outline-none focus:ring-1 focus:ring-castello-red"
                            onBlur={(e) => {
                                if(e.target.value) {
                                    setFormData({...formData, imageURL: e.target.value});
                                    setShowUrlInput(false);
                                }
                            }}
                        />
                        <button onClick={() => setShowUrlInput(false)} className="text-gray-400 hover:text-red-500">
                            <X size={16} />
                        </button>
                    </div>
                </div>
            )}

            {/* Preview da Imagem */}
            {formData.imageURL && (
                <div className="relative w-full h-40 rounded-lg overflow-hidden border border-gray-200 group">
                    <img src={formData.imageURL} alt="Inspiração" className="w-full h-full object-cover" />
                    <button 
                        onClick={() => setFormData({...formData, imageURL: ''})}
                        className="absolute top-2 right-2 bg-white text-red-500 p-1 rounded-full shadow-md hover:bg-gray-100"
                        title="Remover imagem"
                    >
                        <Trash2 size={14} />
                    </button>
                </div>
            )}
          </div>
        </div>

        {/* Observações */}
        <div className="mt-8">
            <label className="block font-bold text-gray-900 mb-2">Observações:</label>
            <textarea
                value={formData.observations}
                onChange={(e) => setFormData({...formData, observations: e.target.value})}
                placeholder="Descreva aqui detalhes adicionais, se deseja trocar algum item, cores de preferência..."
                maxLength={500}
                className="w-full h-32 p-4 border border-gray-300 rounded-lg resize-none focus:outline-none focus:ring-2 focus:ring-castello-red bg-white"
            />
            <div className="text-right text-xs text-gray-500 mt-1">
                {formData.observations.length}/500
            </div>
        </div>
      </div>
    </div>
  );
};

export default CreateCustomParty;