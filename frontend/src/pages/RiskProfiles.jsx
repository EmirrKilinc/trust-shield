import { useState, useEffect } from 'react';
import { riskProfilesAPI } from '../api';
import Card from '../components/common/Card';
import { RISK_LEVELS } from '../utils/constants';
import { formatRiskScore } from '../utils/formatters';
import { Shield, TrendingUp, AlertCircle, Search } from 'lucide-react';

const RiskProfiles = () => {
  const [profiles, setProfiles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterLevel, setFilterLevel] = useState('ALL');

  useEffect(() => {
    fetchProfiles();
  }, []);

  const fetchProfiles = async () => {
    try {
      setLoading(true);
      const response = await riskProfilesAPI.getAllProfiles();
      setProfiles(response.data);
    } catch (error) {
      console.error('Risk profilleri yüklenirken hata:', error);
    } finally {
      setLoading(false);
    }
  };

  // Filtreleme
  const filteredProfiles = profiles.filter((profile) => {
    const matchesSearch = profile.userId.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesLevel = filterLevel === 'ALL' || profile.riskLevel === filterLevel;
    return matchesSearch && matchesLevel;
  });

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold text-gray-900">Risk Profilleri</h1>
        <p className="text-gray-600 mt-1">Kullanıcı risk değerlendirmeleri</p>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <div className="flex items-center space-x-4">
            <div className="w-12 h-12 bg-red-100 rounded-lg flex items-center justify-center">
              <AlertCircle className="w-6 h-6 text-red-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Yüksek Risk</p>
              <p className="text-2xl font-bold text-gray-900">
                {profiles.filter(p => p.riskLevel === 'HIGH').length}
              </p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center space-x-4">
            <div className="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
              <TrendingUp className="w-6 h-6 text-yellow-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Orta Risk</p>
              <p className="text-2xl font-bold text-gray-900">
                {profiles.filter(p => p.riskLevel === 'MEDIUM').length}
              </p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center space-x-4">
            <div className="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
              <Shield className="w-6 h-6 text-green-600" />
            </div>
            <div>
              <p className="text-sm text-gray-600">Düşük Risk</p>
              <p className="text-2xl font-bold text-gray-900">
                {profiles.filter(p => p.riskLevel === 'LOW').length}
              </p>
            </div>
          </div>
        </Card>
      </div>

      {/* Search and Filter */}
      <Card>
        <div className="flex flex-col md:flex-row gap-4">
          {/* Search */}
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
            <input
              type="text"
              placeholder="Kullanıcı ID ara..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {/* Filter by Risk Level */}
          <div className="flex items-center space-x-2">
            <span className="text-sm font-medium text-gray-700">Risk Seviyesi:</span>
            <div className="flex space-x-2">
              {['ALL', 'HIGH', 'MEDIUM', 'LOW'].map((level) => (
                <button
                  key={level}
                  onClick={() => setFilterLevel(level)}
                  className={`px-3 py-1 text-xs font-medium rounded ${
                    filterLevel === level
                      ? 'bg-blue-600 text-white'
                      : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                  }`}
                >
                  {level === 'ALL' ? 'Hepsi' : RISK_LEVELS[level]?.label || level}
                </button>
              ))}
            </div>
          </div>
        </div>
      </Card>

      {/* Profiles Table */}
      <Card>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Kullanıcı ID</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Risk Skoru</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Risk Seviyesi</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Sinyaller</th>
              </tr>
            </thead>
            <tbody>
              {filteredProfiles.length > 0 ? (
                filteredProfiles.map((profile) => (
                  <tr key={profile.userId} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="py-3 px-4 text-sm font-medium text-gray-900">{profile.userId}</td>
                    <td className="py-3 px-4">
                      <div className="flex items-center space-x-2">
                        <div className="flex-1 bg-gray-200 rounded-full h-2 max-w-xs">
                          <div
                            className={`h-2 rounded-full ${
                              profile.riskScore >= 70 ? 'bg-red-500' :
                              profile.riskScore >= 40 ? 'bg-yellow-500' :
                              'bg-green-500'
                            }`}
                            style={{ width: `${profile.riskScore}%` }}
                          ></div>
                        </div>
                        <span className="text-sm font-medium text-gray-700">
                          {formatRiskScore(profile.riskScore)}
                        </span>
                      </div>
                    </td>
                    <td className="py-3 px-4">
                      <span className={`inline-flex px-3 py-1 text-xs font-medium rounded-full ${RISK_LEVELS[profile.riskLevel]?.color || 'bg-gray-500'} text-white`}>
                        {RISK_LEVELS[profile.riskLevel]?.label || profile.riskLevel}
                      </span>
                    </td>
                    <td className="py-3 px-4">
                      <div className="flex flex-wrap gap-1">
                        {profile.signals && profile.signals.length > 0 ? (
                          profile.signals.map((signal, idx) => (
                            <span key={idx} className="inline-flex px-2 py-1 text-xs bg-gray-100 text-gray-700 rounded">
                              {signal}
                            </span>
                          ))
                        ) : (
                          <span className="text-sm text-gray-500">-</span>
                        )}
                      </div>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="4" className="py-8 text-center text-gray-500">
                    Sonuç bulunamadı
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};

export default RiskProfiles;