import { useState, useRef, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { LogOut, User, ChevronDown } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const UserProfile = () => {
  const { user, logout, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  if (!isAuthenticated()) {
    return (
      <div className="flex items-center space-x-4">
        <button
          onClick={() => navigate('/login')}
          className="px-4 py-2 text-castello-dark hover:text-castello-red transition"
        >
          Entrar
        </button>
      </div>
    );
  }

  return (
    <div className="relative" ref={dropdownRef}>
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="flex items-center space-x-2 bg-white px-4 py-2 rounded-lg shadow-sm hover:shadow-md transition"
      >
        <span className="text-sm text-gray-700">v Perfil</span>
        <ChevronDown size={16} className="text-gray-700" />
        <div className="w-8 h-8 rounded-full bg-gray-300 flex items-center justify-center">
          <User size={16} className="text-gray-600" />
        </div>
      </button>

      {isOpen && (
        <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-dashed border-gray-300 py-2 z-50">
          <button
            onClick={handleLogout}
            className="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 flex items-center space-x-2"
          >
            <LogOut size={16} />
            <span>Sair</span>
          </button>
          <button
            onClick={() => {
              setIsOpen(false);
              // navigate('/perfil');
            }}
            className="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 flex items-center space-x-2"
          >
            <User size={16} />
            <span>Editar perfil</span>
          </button>
        </div>
      )}
    </div>
  );
};

export default UserProfile;

