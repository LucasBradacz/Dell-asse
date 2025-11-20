import { useState } from 'react';
import { Plus, Minus, Send, Image as ImageIcon, DollarSign } from 'lucide-react';

const Cart = () => {
  const [observations, setObservations] = useState('');

  const activeOrders = [
    {
      id: 1,
      title: 'Festa 1',
      description: 'Festa da Hello Kitty',
      lastUpdate: '10/11/2025',
      status: 'APROVADO',
      statusColor: 'green',
    },
    {
      id: 2,
      title: 'Festa 2',
      description: 'Festa da Hello Kitty',
      lastUpdate: '10/11/2025',
      status: 'Em andamento',
      statusColor: 'yellow',
    },
    {
      id: 3,
      title: 'Festa 3',
      description: 'Festa da Hello Kitty',
      lastUpdate: '10/11/2025',
      status: 'Negado',
      statusColor: 'red',
    },
  ];

  const pastOrders = [
    {
      id: 0,
      title: 'Festa 0',
      description: 'Festa da Princesa Sophia',
      lastUpdate: '10/04/2025',
      status: 'Negado',
      statusColor: 'red',
    },
  ];

  const items = [
    { name: 'Mesas', quantity: 8 },
    { name: 'Cadeiras', quantity: 16 },
    { name: 'Comida', quantity: 10, unit: 'kg' },
    { name: 'Painel', quantity: 1 },
    { name: 'Blablabla', quantity: 16 },
  ];

  const getStatusStyles = (color) => {
    const styles = {
      green: 'bg-green-100 border-green-400 text-green-700',
      yellow: 'bg-yellow-100 border-yellow-400 text-yellow-700',
      red: 'bg-red-100 border-red-400 text-red-700',
    };
    return styles[color] || styles.green;
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <h1 className="castello-title text-4xl">Carrinho</h1>
        <button className="px-6 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition">
          Fazer pedido
        </button>
      </div>

      {/* Pedidos em andamento */}
      <div>
        <h2 className="text-xl font-bold text-gray-900 mb-4">Pedidos em andamento</h2>
        <div className="space-y-4">
          {activeOrders.map((order) => (
            <div
              key={order.id}
              className={`castello-card p-6 ${getStatusStyles(order.statusColor)}`}
            >
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h3 className="font-bold text-lg mb-1">{order.title}</h3>
                  <p className="text-sm opacity-80">{order.description}</p>
                </div>
                <div className="text-right">
                  <p className="text-sm opacity-80">
                    Última atualização: {order.lastUpdate}
                  </p>
                  <p className="text-sm font-semibold mt-1">
                    Status: {order.status}
                  </p>
                </div>
              </div>
              {order.id === 1 && (
                <button className="mt-4 px-4 py-2 bg-castello-red text-white rounded-lg hover:bg-red-800 transition text-sm">
                  Acessar Gestão de Convite
                </button>
              )}
            </div>
          ))}
        </div>
      </div>

      {/* Pedidos passados */}
      <div>
        <h2 className="text-xl font-bold text-gray-900 mb-4">Pedidos passados</h2>
        <div className="space-y-4">
          {pastOrders.map((order) => (
            <div key={order.id} className="castello-card p-6 border border-gray-300">
              <div className="flex justify-between items-start">
                <div>
                  <h3 className="font-bold text-lg mb-1">{order.title}</h3>
                  <p className="text-sm text-gray-600">{order.description}</p>
                </div>
                <div className="text-right">
                  <p className="text-sm text-gray-600">
                    Última atualização: {order.lastUpdate}
                  </p>
                  <p className="text-sm font-semibold mt-1 text-gray-900">
                    Status: {order.status}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Festa 8 - Detailed Order */}
      <div className="castello-card p-6">
        <h2 className="castello-title text-2xl mb-6">Carrinho &gt; Festa 8</h2>
        <h3 className="text-xl font-bold mb-4">Pedidos em andamento</h3>

        <div className="mb-6">
          <h4 className="font-bold text-lg mb-4">Festa 8</h4>

          {/* Items List */}
          <div className="space-y-3 mb-6">
            {items.map((item, index) => (
              <div
                key={index}
                className="flex items-center justify-between p-3 bg-gray-50 rounded-lg"
              >
                <span className="text-gray-700">
                  {item.quantity} {item.name}
                  {item.unit && ` (${item.quantity}${item.unit})`}
                </span>
                <div className="flex items-center space-x-2">
                  <button className="w-8 h-8 bg-gray-200 hover:bg-gray-300 rounded flex items-center justify-center">
                    <Minus size={16} />
                  </button>
                  <span className="w-8 text-center">{item.quantity}</span>
                  <button className="w-8 h-8 bg-gray-200 hover:bg-gray-300 rounded flex items-center justify-center">
                    <Plus size={16} />
                  </button>
                </div>
              </div>
            ))}

            {/* Add Products */}
            <div className="flex items-center space-x-2 p-3 bg-gray-50 rounded-lg">
              <button className="w-8 h-8 bg-gray-200 hover:bg-gray-300 rounded flex items-center justify-center">
                <Minus size={16} />
              </button>
              <button className="flex-1 px-4 py-2 bg-gray-200 hover:bg-gray-300 rounded text-gray-700">
                Adicione produtos
              </button>
              <button className="w-8 h-8 bg-gray-200 hover:bg-gray-300 rounded flex items-center justify-center">
                <Plus size={16} />
              </button>
            </div>
          </div>

          {/* Observations */}
          <div className="mb-6">
            <label className="block font-bold text-gray-900 mb-2">Observações:</label>
            <div className="relative">
              <textarea
                value={observations}
                onChange={(e) => setObservations(e.target.value)}
                placeholder="Escreva aqui..."
                maxLength={500}
                className="w-full h-32 p-3 border border-gray-300 rounded-lg resize-none"
              />
              <div className="absolute bottom-2 right-2 text-xs text-gray-500">
                {observations.length}/500
              </div>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex flex-col items-end space-y-3">
            <button className="px-6 py-3 bg-castello-red text-white rounded-lg hover:bg-red-800 transition flex items-center space-x-2">
              <Send size={18} />
              <span>Enviar Solicitação</span>
            </button>
            <button className="px-6 py-3 bg-castello-red text-white rounded-lg hover:bg-red-800 transition flex items-center space-x-2">
              <ImageIcon size={18} />
              <span>Adicionar imagem de exemplo</span>
            </button>
            <div className="flex items-center space-x-4">
              <button className="px-6 py-3 bg-castello-red text-white rounded-lg hover:bg-red-800 transition flex items-center space-x-2">
                <DollarSign size={18} />
                <span>Criar com orçamento</span>
              </button>
              <span className="text-lg font-bold text-gray-900">R$ 10.000,00</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;

