import { useState, useEffect } from 'react';
import { ChevronDown, FileText, Mail, RefreshCw } from 'lucide-react';
import { partyService } from '../../services/partyService';

const ViewRequests = () => {
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [menuOpen, setMenuOpen] = useState(null);

  const statusOptions = [
    { value: 'PENDING', label: 'No aguardo', color: 'bg-yellow-500' },
    { value: 'APPROVED', label: 'Aprovado', color: 'bg-green-500' },
    { value: 'REJECTED', label: 'Negado', color: 'bg-red-500' },
    { value: 'COMPLETED', label: 'Concluído', color: 'bg-blue-600' }
  ];

  const fetchRequests = async () => {
    try {
      setLoading(true);
      const data = await partyService.getAll();
      setRequests(data);
    } catch (error) {
      console.error("Erro ao buscar solicitações:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRequests();
  }, []);

  const handleStatusChange = async (partyId, newStatus) => {
    try {
      setMenuOpen(null);
      setRequests(prev => prev.map(req => 
        req.id === partyId ? { ...req, status: newStatus } : req
      ));
      await partyService.updateStatus(partyId, `"${newStatus}"`);
      fetchRequests();
    } catch (error) {
      console.error("Erro ao atualizar status:", error);
      alert("Erro ao atualizar status.");
      fetchRequests();
    }
  };

  const getStatusColor = (statusVal) => {
    const found = statusOptions.find(s => s.value === statusVal);
    return found ? found.color : 'bg-gray-500';
  };

  const getStatusLabel = (statusVal) => {
    const found = statusOptions.find(s => s.value === statusVal);
    return found ? found.label : statusVal;
  };

  const formatDate = (dateInput) => {
    if (!dateInput) return "-";
    // Tenta converter string ISO ou array de data
    try {
        const date = new Date(dateInput);
        if (!isNaN(date)) return date.toLocaleDateString('pt-BR');
        
        // Fallback para array do Java [ano, mes, dia...]
        if (Array.isArray(dateInput)) {
             const [year, month, day] = dateInput;
             return `${day.toString().padStart(2, '0')}/${month.toString().padStart(2, '0')}/${year}`;
        }
    } catch (e) { return "-"; }
    return "-";
  };

  if (loading) return <div className="p-8 text-center">Carregando...</div>;

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="castello-title text-3xl">
          Painel do Administrador &gt; Ver solicitações
        </h2>
        <button onClick={fetchRequests} className="p-2 bg-gray-100 rounded hover:bg-gray-200">
            <RefreshCw size={20} />
        </button>
      </div>

      <div className="castello-card p-6 overflow-x-auto min-h-[400px]">
        <table className="w-full min-w-[1000px]">
          <thead>
            <tr className="border-b border-gray-300 text-left">
              <th className="py-3 px-4 font-semibold">ID / Título</th>
              <th className="py-3 px-4 font-semibold">Cliente</th>
              <th className="py-3 px-4 font-semibold">Data</th>
              <th className="py-3 px-4 font-semibold">Orçamento</th>
              <th className="py-3 px-4 font-semibold">Itens</th>
              <th className="py-3 px-4 font-semibold">Status</th>
              <th className="py-3 px-4 font-semibold">Ações</th>
            </tr>
          </thead>
          <tbody>
            {requests.length === 0 ? (
                <tr><td colSpan="7" className="p-4 text-center text-gray-500">Nenhuma solicitação encontrada.</td></tr>
            ) : requests.map((req) => (
              <tr key={req.id} className="border-b border-gray-200 hover:bg-gray-50">
                <td className="py-4 px-4 align-top">
                    <span className="font-bold block text-xs text-gray-400">#{req.id}</span>
                    <span>{req.title}</span>
                </td>
                
                {/* CORREÇÃO: Usa o novo campo userName */}
                <td className="py-4 px-4 align-top">
                    <div className="flex flex-col">
                        <span className="font-medium">{req.userName}</span>
                        <span className="text-xs text-gray-500">Cliente</span>
                    </div>
                </td>

                {/* CORREÇÃO: Usa o novo campo requestDate */}
                <td className="py-4 px-4 align-top">
                    {formatDate(req.requestDate)}
                </td>

                {/* CORREÇÃO: Usa o campo generateBudget corretamente tipado */}
                <td className="py-4 px-4 align-top text-green-700 font-bold">
                    {typeof req.generateBudget === 'number' 
                        ? `R$ ${req.generateBudget.toFixed(2)}` 
                        : "Sob Consulta"}
                </td>

                <td className="py-4 px-4 align-top text-sm">
                  {req.products && req.products.length > 0 ? (
                      <ul className="list-disc list-inside">
                          {req.products.slice(0, 3).map((p, i) => <li key={i} className="truncate max-w-[150px]">{p.name}</li>)}
                          {req.products.length > 3 && <li className="text-gray-400">+{req.products.length - 3} outros</li>}
                      </ul>
                  ) : <span className="italic text-gray-400">Sem itens</span>}
                </td>
                
                <td className="py-4 px-4 align-top relative">
                  <div className="relative">
                    <button
                        onClick={() => setMenuOpen(menuOpen === req.id ? null : req.id)}
                        className={`w-full px-3 py-1.5 text-white rounded text-xs font-bold flex items-center justify-between gap-2 ${getStatusColor(req.status)}`}
                    >
                        <span>{getStatusLabel(req.status)}</span>
                        <ChevronDown size={14} />
                    </button>

                    {menuOpen === req.id && (
                        <div className="absolute top-full left-0 mt-1 w-40 bg-white border border-gray-200 rounded shadow-xl z-50 overflow-hidden">
                            {statusOptions.map((opt) => (
                                <button
                                    key={opt.value}
                                    onClick={() => handleStatusChange(req.id, opt.value)}
                                    className="w-full text-left px-4 py-2 text-xs hover:bg-gray-100 flex items-center gap-2"
                                >
                                    <div className={`w-2 h-2 rounded-full ${opt.color}`}></div>
                                    {opt.label}
                                </button>
                            ))}
                        </div>
                    )}
                  </div>
                </td>

                <td className="py-4 px-4 align-top">
                  <div className="flex gap-2">
                    <button className="p-1.5 bg-blue-50 text-blue-600 rounded hover:bg-blue-100" title="Contrato">
                        <FileText size={16} />
                    </button>
                    <button className="p-1.5 bg-blue-50 text-blue-600 rounded hover:bg-blue-100" title="Email">
                        <Mail size={16} />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ViewRequests;