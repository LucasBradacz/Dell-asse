import { useState } from 'react';
import { ChevronDown, FileText, Mail, Download } from 'lucide-react';

const ViewRequests = () => {
  const [selectedStatus, setSelectedStatus] = useState({});

  const requests = [
    {
      id: 1,
      nome: 'Jacinto Pinto',
      telefone: '(49) 99916-7594',
      dataFesta: '27/02/2005',
      tema: 'Pokémon',
      itens: ['X Cadeiras', 'X Mesas', 'X Guardanapos', 'X Painel', 'X Cilindro'],
      qtdPessoas: 54,
    },
  ];

  const statusOptions = ['No aguardo', 'Aprovado', 'Cancelado', 'Negado'];

  const getStatusColor = (status) => {
    const colors = {
      'No aguardo': 'bg-yellow-500',
      Aprovado: 'bg-green-500',
      Cancelado: 'bg-gray-500',
      Negado: 'bg-red-500',
    };
    return colors[status] || 'bg-gray-500';
  };

  return (
    <div className="space-y-6">
      <h2 className="castello-title text-3xl">
        Painel do Administrador &gt; Ver solicitações
      </h2>

      <div className="castello-card p-6 overflow-x-auto">
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-300">
              <th className="text-left py-3 px-4 font-semibold text-gray-900">Nome</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">Telefone</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                Data da festa
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">Tema</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                O que vai na festa
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                Qtd de pessoas aproximadamente
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                Alterar status
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">Gerar</th>
            </tr>
          </thead>
          <tbody>
            {requests.map((request) => (
              <tr key={request.id} className="border-b border-gray-200">
                <td className="py-4 px-4">{request.nome}</td>
                <td className="py-4 px-4">{request.telefone}</td>
                <td className="py-4 px-4">{request.dataFesta}</td>
                <td className="py-4 px-4">{request.tema}</td>
                <td className="py-4 px-4">
                  <ul className="list-disc list-inside text-sm">
                    {request.itens.map((item, index) => (
                      <li key={index}>{item}</li>
                    ))}
                  </ul>
                </td>
                <td className="py-4 px-4">{request.qtdPessoas}</td>
                <td className="py-4 px-4">
                  <div className="space-y-2">
                    {statusOptions.map((status) => (
                      <button
                        key={status}
                        onClick={() =>
                          setSelectedStatus({ ...selectedStatus, [request.id]: status })
                        }
                        className={`w-full px-3 py-1 text-white rounded text-sm flex items-center justify-between ${
                          selectedStatus[request.id] === status
                            ? getStatusColor(status)
                            : 'bg-gray-300'
                        }`}
                      >
                        <span>{status}</span>
                        {status === 'No aguardo' && (
                          <ChevronDown size={14} />
                        )}
                      </button>
                    ))}
                  </div>
                </td>
                <td className="py-4 px-4">
                  <div className="space-y-2">
                    <button className="w-full px-3 py-1 bg-blue-500 text-white rounded text-sm hover:bg-blue-600 transition flex items-center justify-center space-x-1">
                      <FileText size={14} />
                      <span>Gerar contrato</span>
                    </button>
                    <button className="w-full px-3 py-1 bg-blue-500 text-white rounded text-sm hover:bg-blue-600 transition flex items-center justify-center space-x-1">
                      <Mail size={14} />
                      <span>Gerar convite</span>
                    </button>
                    <button className="w-full px-3 py-1 bg-blue-500 text-white rounded text-sm hover:bg-blue-600 transition flex items-center justify-center space-x-1">
                      <Download size={14} />
                      <span>Gerar</span>
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

