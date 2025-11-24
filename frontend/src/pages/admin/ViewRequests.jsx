import { useState, useEffect } from 'react';
import { ChevronDown, FileText, Mail, Download, RefreshCw } from 'lucide-react';
import { partyService } from '../../services/partyService';

const ViewRequests = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [statusMenuOpen, setStatusMenuOpen] = useState(null); 

  // Mapeamento visual dos status
  const statusMap = {
    'PENDING': { label: 'No aguardo', color: 'bg-yellow-500' },
    'APPROVED': { label: 'Aprovado', color: 'bg-green-500' },
    'REJECTED': { label: 'Negado', color: 'bg-red-500' },
    'COMPLETED': { label: 'Concluído', color: 'bg-blue-600' },
  };

  const availableStatus = ['PENDING', 'APPROVED', 'REJECTED', 'COMPLETED'];

  // Carregar dados do backend
  const fetchRequests = async () => {
    try {
      setLoading(true);
      const data = await partyService.getAll();
      setRequests(data);
    } catch (error) {
      console.error("Erro ao buscar solicitações:", error);
      alert("Erro ao carregar solicitações.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRequests();
  }, []);

  // Função para alterar status
  const handleStatusChange = async (id, newStatus) => {
    try {
      await partyService.updateStatus(id, newStatus);
      
      // Atualiza a lista localmente para refletir a mudança instantaneamente
      setRequests(requests.map(req => 
        req.id === id ? { ...req, status: newStatus } : req
      ));
      setStatusMenuOpen(null); // Fecha o menu
    } catch (error) {
      console.error("Erro ao atualizar status:", error);
      alert("Não foi possível atualizar o status.");
    }
  };

  // Formata data (assumindo formato ISO ou array do Java)
  const formatDate = (dateStr) => {
    if (!dateStr) return "A combinar";
    const date = new Date(dateStr);
    return date.toLocaleDateString('pt-BR');
  };

  if (loading) {
    return <div className="p-8 text-center">Carregando solicitações...</div>;
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="castello-title text-3xl">
          Painel do Administrador &gt; Ver solicitações
        </h2>
        <button onClick={fetchRequests} className="p-2 bg-gray-200 rounded hover:bg-gray-300" title="Atualizar">
          <RefreshCw size={20} />
        </button>
      </div>

      <div className="castello-card p-6 overflow-x-auto min-h-[400px]">
        <table className="w-full min-w-[1000px]">
          <thead>
            <tr className="border-b border-gray-300 text-sm uppercase tracking-wider">
              <th className="text-left py-3 px-4 font-semibold text-gray-700">ID / Titulo</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-700">Cliente</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-700">Data Solicitação</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-700">Orçamento</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-700">Itens</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-700">Status</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-700">Ações</th>
            </tr>
          </thead>
          <tbody className="text-sm">
            {requests.map((request) => {
               const currentStatus = statusMap[request.status] || { label: request.status, color: 'bg-gray-500' };

               return (
              <tr key={request.id} className="border-b border-gray-100 hover:bg-gray-50">
                <td className="py-4 px-4 align-top">
                    <span className="font-bold block">#{request.id}</span>
                    <span className="text-gray-600">{request.title}</span>
                </td>
                
                <td className="py-4 px-4 align-top">
                    {/* Se o backend ainda não mandar userName, usamos placeholders ou dados do objeto User se tiver */}
                    <div className="flex flex-col">
                        <span className="font-semibold">{request.userName || "Usuário"}</span> 
                        <span className="text-xs text-gray-500">{request.userPhone || "-"}</span>
                    </div>
                </td>

                <td className="py-4 px-4 align-top">
                    {formatDate(request.createdAt || request.lastAtualization)}
                </td>
                
                <td className="py-4 px-4 align-top text-green-700 font-bold">
                    R$ {request.generateBudget?.toFixed(2)}
                </td>

                <td className="py-4 px-4 align-top">
                  {request.products && request.products.length > 0 ? (
                    <ul className="list-disc list-inside text-xs text-gray-600">
                      {request.products.slice(0, 3).map((item, index) => (
                        <li key={index} className="truncate max-w-[150px]">{item.name}</li>
                      ))}
                      {request.products.length > 3 && <li>+{request.products.length - 3} itens</li>}
                    </ul>
                  ) : (
                    <span className="text-xs text-gray-400 italic">Sem itens</span>
                  )}
                </td>
                
                {/* Coluna de Status com Dropdown */}
                <td className="py-4 px-4 align-top relative">
                  <div className="relative">
                      <button
                        onClick={() => setStatusMenuOpen(statusMenuOpen === request.id ? null : request.id)}
                        className={`w-full px-3 py-1.5 text-white rounded text-xs font-bold flex items-center justify-between gap-2 transition-all ${currentStatus.color}`}
                      >
                        <span>{currentStatus.label}</span>
                        <ChevronDown size={14} />
                      </button>

                      {statusMenuOpen === request.id && (
                        <div className="absolute top-full left-0 mt-1 w-40 bg-white border border-gray-200 rounded shadow-xl z-50 overflow-hidden">
                            {availableStatus.map(statusKey => (
                                <button
                                    key={statusKey}
                                    onClick={() => handleStatusChange(request.id, statusKey)}
                                    className="w-full text-left px-4 py-2 text-xs hover:bg-gray-100 text-gray-700 flex items-center gap-2"
                                >
                                    <div className={`w-2 h-2 rounded-full ${statusMap[statusKey].color}`}></div>
                                    {statusMap[statusKey].label}
                                </button>
                            ))}
                        </div>
                      )}
                  </div>
                </td>

                <td className="py-4 px-4 align-top">
                  <div className="flex gap-2">
                    <button title="Gerar Contrato" className="p-1.5 bg-blue-50 text-blue-600 rounded hover:bg-blue-100 transition">
                      <FileText size={16} />
                    </button>
                    <button title="Enviar Email" className="p-1.5 bg-blue-50 text-blue-600 rounded hover:bg-blue-100 transition">
                      <Mail size={16} />
                    </button>
                  </div>
                </td>
              </tr>
            )})}
          </tbody>
        </table>

        {requests.length === 0 && (
            <div className="text-center py-10 text-gray-500">Nenhuma solicitação encontrada.</div>
        )}
      </div>
    </div>
  );
};

export default ViewRequests;