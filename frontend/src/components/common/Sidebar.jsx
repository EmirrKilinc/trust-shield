import { Link, useLocation } from 'react-router-dom';
import { 
  LayoutDashboard, 
  Activity, 
  Shield, 
  AlertTriangle, 
  FileText,
  Users,
  Settings
} from 'lucide-react';

const Sidebar = () => {
  const location = useLocation();

  const menuItems = [
    { path: '/', label: 'Dashboard', icon: LayoutDashboard },
    { path: '/events', label: 'Olaylar', icon: Activity },
    { path: '/risk-profiles', label: 'Risk Profilleri', icon: Shield },
    { path: '/risk-rules', label: 'Risk Kuralları', icon: Settings },
    { path: '/fraud-cases', label: 'Fraud Caselar', icon: AlertTriangle },
    { path: '/decisions', label: 'Kararlar', icon: FileText },
    { path: '/users', label: 'Kullanıcılar', icon: Users },
  ];

  return (
    <div className="w-64 bg-gray-900 text-white min-h-screen fixed left-0 top-0">
      <div className="p-6">
        <h1 className="text-2xl font-bold text-blue-400">TrustShield</h1>
        <p className="text-sm text-gray-400 mt-1">Fraud Detection</p>
      </div>
      
      <nav className="mt-6">
        {menuItems.map((item) => {
          const Icon = item.icon;
          const isActive = location.pathname === item.path;
          
          return (
            <Link
              key={item.path}
              to={item.path}
              className={`flex items-center px-6 py-3 text-sm transition-colors ${
                isActive
                  ? 'bg-blue-600 text-white border-l-4 border-blue-400'
                  : 'text-gray-300 hover:bg-gray-800'
              }`}
            >
              <Icon className="w-5 h-5 mr-3" />
              {item.label}
            </Link>
          );
        })}
      </nav>
    </div>
  );
};

export default Sidebar;