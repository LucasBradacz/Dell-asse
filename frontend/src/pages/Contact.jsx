import { Phone, Mail, Instagram, Facebook, MapPin, ChevronDown } from 'lucide-react';

const Contact = () => {
  return (
    <div className="space-y-6">
      {/* Page Title */}
      <h1 className="castello-title text-4xl text-center mb-8">Contato</h1>

      {/* Contact Information */}
      <div className="max-w-2xl mx-auto space-y-6">
        <div className="castello-card p-6 space-y-4">
          {/* Phone */}
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 bg-green-500 rounded-full flex items-center justify-center">
              <Phone size={20} className="text-white" />
            </div>
            <a
              href="tel:+5549991995065"
              className="text-gray-700 hover:text-castello-red transition"
            >
              (49) 9 9199-5065
            </a>
          </div>

          {/* Email */}
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 bg-red-500 rounded-full flex items-center justify-center">
              <Mail size={20} className="text-white" />
            </div>
            <a
              href="mailto:castellodelasse@gmail.com"
              className="text-gray-700 hover:text-castello-red transition underline"
            >
              castellodelasse@gmail.com
            </a>
          </div>

          {/* Instagram */}
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 bg-gradient-to-br from-purple-500 via-pink-500 to-orange-500 rounded-full flex items-center justify-center">
              <Instagram size={20} className="text-white" />
            </div>
            <a
              href="https://instagram.com/castellodelasse"
              target="_blank"
              rel="noopener noreferrer"
              className="text-gray-700 hover:text-castello-red transition"
            >
              @castellodelasse
            </a>
          </div>

          {/* Facebook */}
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 bg-blue-600 rounded-full flex items-center justify-center">
              <Facebook size={20} className="text-white" />
            </div>
            <a
              href="https://facebook.com/castellodelasse"
              target="_blank"
              rel="noopener noreferrer"
              className="text-gray-700 hover:text-castello-red transition"
            >
              Castello Del'asse
            </a>
          </div>

          {/* Operating Hours */}
          <div className="pt-4 border-t border-gray-200">
            <p className="text-gray-700 font-medium">
              Atendemos das 08:00 - 11:30 | 13:30 - 17:30
            </p>
            <p className="text-gray-700 font-medium">Segunda a Domingo</p>
          </div>

          {/* Call to Action */}
          <div className="pt-4">
            <p className="text-lg font-bold text-castello-dark">
              Agende já uma visita!
            </p>
          </div>
        </div>

        {/* Map */}
        <div className="castello-card p-4">
          <div className="w-full h-64 bg-gray-200 rounded-lg flex items-center justify-center relative">
            <MapPin size={48} className="text-red-500" />
            <div className="absolute bottom-4 left-1/2 -translate-x-1/2">
              <ChevronDown size={24} className="text-gray-600 animate-bounce" />
            </div>
            <div className="absolute bottom-2 left-2 text-xs text-gray-500">
              Google Maps
            </div>
          </div>
        </div>

        {/* Building Image */}
        <div className="castello-card p-4">
          <div className="w-full h-64 bg-gradient-to-br from-gray-300 to-gray-400 rounded-lg flex items-center justify-center">
            <div className="text-center">
              <div className="text-6xl mb-2">🏰</div>
              <div className="text-gray-700 font-semibold">Castello Del'asse</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Contact;

