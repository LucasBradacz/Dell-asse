import { useAuth } from '../contexts/AuthContext';
import { Calendar, Package, Users, TrendingUp } from 'lucide-react';
import { Link } from 'react-router-dom';

const Dashboard = () => {
  const { user } = useAuth();

  const stats = [
    {
      name: 'Festas Agendadas',
      value: '0',
      icon: Calendar,
      color: 'bg-blue-500',
      link: '/festas',
    },
    {
      name: 'Produtos Cadastrados',
      value: '0',
      icon: Package,
      color: 'bg-green-500',
      link: '/produtos',
    },
    {
      name: 'Clientes',
      value: '0',
      icon: Users,
      color: 'bg-purple-500',
      link: '#',
    },
    {
      name: 'Receita Total',
      value: 'R$ 0,00',
      icon: TrendingUp,
      color: 'bg-yellow-500',
      link: '#',
    },
  ];

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">
          Bem-vindo, {user?.username}!
        </h1>
        <p className="text-gray-600 mt-2">
          Aqui está um resumo das suas atividades
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {stats.map((stat) => {
          const Icon = stat.icon;
          return (
            <Link
              key={stat.name}
              to={stat.link}
              className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition"
            >
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm font-medium text-gray-600">
                    {stat.name}
                  </p>
                  <p className="text-2xl font-bold text-gray-900 mt-2">
                    {stat.value}
                  </p>
                </div>
                <div className={`${stat.color} p-3 rounded-lg`}>
                  <Icon className="w-6 h-6 text-white" />
                </div>
              </div>
            </Link>
          );
        })}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold mb-4">Ações Rápidas</h2>
          <div className="space-y-3">
            <Link
              to="/produtos/create"
              className="block w-full px-4 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition text-center"
            >
              Criar Novo Produto
            </Link>
            <Link
              to="/festas/create"
              className="block w-full px-4 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition text-center"
            >
              Criar Nova Festa
            </Link>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-xl font-semibold mb-4">Atividades Recentes</h2>
          <div className="text-gray-500 text-center py-8">
            <p>Nenhuma atividade recente</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;

