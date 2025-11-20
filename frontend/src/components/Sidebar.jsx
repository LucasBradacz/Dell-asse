import { Link, useLocation } from 'react-router-dom';
import { Home, Image, Mail, ShoppingCart, Settings } from 'lucide-react';

const Sidebar = () => {
  const location = useLocation();

  const menuItems = [
    { path: '/', label: 'Início', icon: Home },
    { path: '/galeria', label: 'Galeria', icon: Image },
    { path: '/contato', label: 'Contato', icon: Mail },
    { path: '/carrinho', label: 'Carrinho', icon: ShoppingCart },
    { path: '/painel-adm', label: 'Painel do ADM', icon: Settings },
  ];

  const isActive = (path) => {
    if (path === '/') {
      return location.pathname === '/';
    }
    return location.pathname.startsWith(path);
  };

  return (
    <div className="castello-sidebar w-64 min-h-screen fixed left-0 top-0 flex flex-col">
      {/* Logo */}
      <div className="bg-castello-darker px-6 py-4 flex items-center space-x-3">
        <div className="text-white text-2xl">🏰</div>
        <div className="text-white">
          <div className="font-bold text-lg">Castello</div>
          <div className="text-sm">Del'asse</div>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 px-4 py-6 space-y-2">
        {menuItems.map((item) => {
          const Icon = item.icon;
          const active = isActive(item.path);
          return (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center space-x-3 px-4 py-3 rounded-lg transition-colors ${
                active
                  ? 'bg-castello-darker text-white'
                  : 'text-white hover:bg-castello-darker/50'
              }`}
            >
              <Icon size={20} />
              <span className="font-medium">{item.label}</span>
            </Link>
          );
        })}
      </nav>
    </div>
  );
};

export default Sidebar;

