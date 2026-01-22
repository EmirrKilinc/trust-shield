import { useState, useEffect } from 'react';
import { dashboardAPI } from '../api';
import Card from '../components/common/Card';
import { formatDate } from '../utils/formatters';
import { FileText, Search, Filter } from 'lucide-react';

const Decisions = () => {
  const [decisions, setDecisions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchDecisions();
  }, []);

  const fetchDecisions = async () => {
    try {
      setLoading(true);
      const response = await dashboardAPI.getDecisions();
      setDecisions(response.data);
    } catch (error) {
      console.error('Kararlar yüklenirken hata:', error);
    } finally {
      setLoading(false);
    }
  };

  // Arama filtresi
  const filteredDecisions = decisions.filter((decision) =>
    decision.userId?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    decision.decisionId?.toLowerCase().includes(searchTerm.toLowerCase()) ||
    decision.selectedAction?.toLowerCase().includes(searchTerm.toLowerCase())
  );

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
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Karar Logları</h1>
          <p className="text-gray-600 mt-1">Sistem kararları ve audit trail</p>
        </div>
        <div className="flex items-center px-4 py-2 bg-blue-100 rounded-lg">
          <FileText className="w-5 h-5 text-blue-600 mr-2" />
          <span className="text-lg font-bold text-blue-600">{decisions.length}</span>
        </div>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <div className="text-center">
            <p className="text-2xl font-bold text-gray-900">{decisions.length}</p>
            <p className="text-sm text-gray-600">Toplam Karar</p>
          </div>
        </Card>
        <Card>
          <div className="text-center">
            <p className="text-2xl font-bold text-blue-600">
              {decisions.reduce((acc, d) => acc + (d.triggeredRules?.length || 0), 0)}
            </p>
            <p className="text-sm text-gray-600">Tetiklenen Kural</p>
          </div>
        </Card>
        <Card>
          <div className="text-center">
            <p className="text-2xl font-bold text-orange-600">
              {decisions.reduce((acc, d) => acc + (d.suppressedActions?.length || 0), 0)}
            </p>
            <p className="text-sm text-gray-600">Bastırılan Aksiyon</p>
          </div>
        </Card>
      </div>

      {/* Search */}
      <Card>
        <div className="relative">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-5 h-5" />
          <input
            type="text"
            placeholder="Karar ara (kullanıcı ID, karar ID, aksiyon)..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
      </Card>

      {/* Decisions Table */}
      <Card>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Karar ID</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Kullanıcı</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Tetiklenen Kurallar</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Seçilen Aksiyon</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Bastırılan</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Zaman</th>
              </tr>
            </thead>
            <tbody>
              {filteredDecisions.length > 0 ? (
                filteredDecisions.map((decision) => (
                  <tr key={decision.decisionId} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="py-3 px-4 text-sm font-mono text-gray-600">{decision.decisionId}</td>
                    <td className="py-3 px-4 text-sm text-gray-900">{decision.userId}</td>
                    <td className="py-3 px-4">
                      <div className="flex flex-wrap gap-1">
                        {decision.triggeredRules && decision.triggeredRules.length > 0 ? (
                          decision.triggeredRules.map((rule, idx) => (
                            <span key={idx} className="inline-flex px-2 py-1 text-xs bg-blue-100 text-blue-700 rounded">
                              {rule}
                            </span>
                          ))
                        ) : (
                          <span className="text-sm text-gray-500">-</span>
                        )}
                      </div>
                    </td>
                    <td className="py-3 px-4">
                      <span className="inline-flex px-2 py-1 text-xs font-medium bg-green-500 text-white rounded">
                        {decision.selectedAction}
                      </span>
                    </td>
                    <td className="py-3 px-4">
                      <div className="flex flex-wrap gap-1">
                        {decision.suppressedActions && decision.suppressedActions.length > 0 ? (
                          decision.suppressedActions.map((action, idx) => (
                            <span key={idx} className="inline-flex px-2 py-1 text-xs bg-gray-100 text-gray-600 rounded">
                              {action}
                            </span>
                          ))
                        ) : (
                          <span className="text-sm text-gray-500">-</span>
                        )}
                      </div>
                    </td>
                    <td className="py-3 px-4 text-sm text-gray-600">{formatDate(decision.timestamp)}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" className="py-8 text-center text-gray-500">
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

export default Decisions;