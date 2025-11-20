import { Download } from 'lucide-react';

const ViewDatabase = () => {
  const clients = [
    {
      id: 1,
      nome: 'Jacinto Pinto',
      telefone: '(49) 99916-7594',
      dataAniversario: '27/02/2005',
      qtdFestas: 2,
      ultimoTema: 'Pokémon',
      avaliacoes: 5,
    },
  ];

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h2 className="castello-title text-3xl">
          Painel do Administrador &gt; Ver base de dados
        </h2>
      </div>

      <div className="castello-card p-6 overflow-x-auto">
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-300">
              <th className="text-left py-3 px-4 font-semibold text-gray-900">Nome</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">Telefone</th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                Data de aniversário
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                Quantas festas fez
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                Qual foi o último tema e idade
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">
                Avaliações
              </th>
              <th className="text-left py-3 px-4 font-semibold text-gray-900">Ações</th>
            </tr>
          </thead>
          <tbody>
            {clients.map((client) => (
              <tr key={client.id} className="border-b border-gray-200">
                <td className="py-4 px-4">{client.nome}</td>
                <td className="py-4 px-4">{client.telefone}</td>
                <td className="py-4 px-4">{client.dataAniversario}</td>
                <td className="py-4 px-4">{client.qtdFestas}</td>
                <td className="py-4 px-4">{client.ultimoTema}</td>
                <td className="py-4 px-4">
                  <div className="flex space-x-1">
                    {[...Array(5)].map((_, i) => (
                      <span
                        key={i}
                        className={`text-xl ${
                          i < client.avaliacoes ? 'text-yellow-400' : 'text-gray-300'
                        }`}
                      >
                        ★
                      </span>
                    ))}
                  </div>
                </td>
                <td className="py-4 px-4">
                  <button className="px-4 py-2 bg-blue-400 text-white rounded-lg hover:bg-blue-500 transition text-sm">
                    Contatar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="flex justify-end">
        <button className="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition flex items-center space-x-2">
          <Download size={18} />
          <span>Gerar CSV</span>
        </button>
      </div>
    </div>
  );
};

export default ViewDatabase;

