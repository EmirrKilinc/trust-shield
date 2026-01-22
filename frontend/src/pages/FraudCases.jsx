import { useState, useEffect } from 'react';
import { fraudCasesAPI } from '../api';
import { useApp } from '../context/AppContext';
import Card from '../components/common/Card';
import { CASE_STATUS, CASE_PRIORITY } from '../utils/constants';
import { formatDate } from '../utils/formatters';
import { Plus, Eye, Filter } from 'lucide-react';

const FraudCases = () => {
  const [cases, setCases] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterStatus, setFilterStatus] = useState('ALL');
  const [showModal, setShowModal] = useState(false);
  const { addNotification } = useApp();

  const [formData, setFormData] = useState({
    userId: '',
    caseType: '',
    priority: 'MEDIUM',
    note: '',
  });

  useEffect(() => {
    fetchCases();
  }, []);

  const fetchCases = async () => {
    try {
      setLoading(true);
      const response = await fraudCasesAPI.getAllCases();
      setCases(response.data);
    } catch (error) {
      console.error('Caseler yüklenirken hata:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await fraudCasesAPI.openCase(formData);
      
      addNotification({
        type: 'success',
        title: 'Başarılı',
        message: 'Yeni fraud case başarıyla açıldı',
      });
      
      setShowModal(false);
      setFormData({
        userId: '',
        caseType: '',
        priority: 'MEDIUM',
        note: '',
      });
      fetchCases();
    } catch (error) {
      console.error('Case açılırken hata:', error);
      addNotification({
        type: 'error',
        title: 'Hata',
        message: 'Case açılırken bir hata oluştu',
      });
    }
  };

  const filteredCases = filterStatus === 'ALL' 
    ? cases 
    : cases.filter(c => c.status === filterStatus);

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
          <h1 className="text-3xl font-bold text-gray-900">Fraud Caseler</h1>
          <p className="text-gray-600 mt-1">İnceleme kayıtları</p>
        </div>
        <button 
          onClick={() => setShowModal(true)}
          className="flex items-center px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700"
        >
          <Plus className="w-4 h-4 mr-2" />
          Yeni Case Aç
        </button>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <Card>
          <div className="text-center">
            <p className="text-2xl font-bold text-gray-900">{cases.length}</p>
            <p className="text-sm text-gray-600">Toplam Case</p>
          </div>
        </Card>
        <Card>
          <div className="text-center">
            <p className="text-2xl font-bold text-blue-600">
              {cases.filter(c => c.status === 'OPEN').length}
            </p>
            <p className="text-sm text-gray-600">Açık</p>
          </div>
        </Card>
        <Card>
          <div className="text-center">
            <p className="text-2xl font-bold text-orange-600">
              {cases.filter(c => c.status === 'IN_PROGRESS').length}
            </p>
            <p className="text-sm text-gray-600">İşlemde</p>
          </div>
        </Card>
        <Card>
          <div className="text-center">
            <p className="text-2xl font-bold text-gray-600">
              {cases.filter(c => c.status === 'CLOSED').length}
            </p>
            <p className="text-sm text-gray-600">Kapalı</p>
          </div>
        </Card>
      </div>

      {/* Filter */}
      <Card>
        <div className="flex items-center space-x-2">
          <Filter className="w-4 h-4 text-gray-500" />
          <span className="text-sm font-medium text-gray-700">Filtre:</span>
          <div className="flex space-x-2">
            {['ALL', 'OPEN', 'IN_PROGRESS', 'CLOSED'].map((status) => (
              <button
                key={status}
                onClick={() => setFilterStatus(status)}
                className={`px-3 py-1 text-xs font-medium rounded ${
                  filterStatus === status
                    ? 'bg-blue-600 text-white'
                    : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                }`}
              >
                {status === 'ALL' ? 'Hepsi' : CASE_STATUS[status]?.label || status}
              </button>
            ))}
          </div>
        </div>
      </Card>

      {/* Cases Table */}
      <Card>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Case ID</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Kullanıcı</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Tip</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Öncelik</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Durum</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Açılma</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Açan</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">İşlemler</th>
              </tr>
            </thead>
            <tbody>
              {filteredCases.map((caseItem) => (
                <tr key={caseItem.caseId} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4 text-sm font-mono text-gray-900">{caseItem.caseId}</td>
                  <td className="py-3 px-4 text-sm text-gray-900">{caseItem.userId}</td>
                  <td className="py-3 px-4 text-sm text-gray-700">{caseItem.caseType}</td>
                  <td className="py-3 px-4">
                    <span className={`inline-flex px-2 py-1 text-xs font-medium rounded ${CASE_PRIORITY[caseItem.priority]?.color || 'bg-gray-500'} text-white`}>
                      {CASE_PRIORITY[caseItem.priority]?.label || caseItem.priority}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <span className={`inline-flex px-2 py-1 text-xs font-medium rounded ${CASE_STATUS[caseItem.status]?.color || 'bg-gray-500'} text-white`}>
                      {CASE_STATUS[caseItem.status]?.label || caseItem.status}
                    </span>
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-600">{formatDate(caseItem.openedAt)}</td>
                  <td className="py-3 px-4 text-sm text-gray-700">{caseItem.openedBy}</td>
                  <td className="py-3 px-4">
                    <button className="p-1 text-blue-600 hover:bg-blue-50 rounded" title="Detay">
                      <Eye className="w-4 h-4" />
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-lg">
            <h2 className="text-xl font-bold text-gray-900 mb-4">Yeni Fraud Case Aç</h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Kullanıcı ID
                </label>
                <input
                  type="text"
                  value={formData.userId}
                  onChange={(e) => setFormData({ ...formData, userId: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                  placeholder="Örn: U123"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Case Tipi
                </label>
                <input
                  type="text"
                  value={formData.caseType}
                  onChange={(e) => setFormData({ ...formData, caseType: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                  placeholder="Örn: Suspicious Payment Pattern"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Öncelik
                </label>
                <select
                  value={formData.priority}
                  onChange={(e) => setFormData({ ...formData, priority: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                >
                  {Object.keys(CASE_PRIORITY).map((key) => (
                    <option key={key} value={key}>
                      {CASE_PRIORITY[key].label}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Not
                </label>
                <textarea
                  value={formData.note}
                  onChange={(e) => setFormData({ ...formData, note: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  rows="3"
                  placeholder="Case hakkında notlar..."
                />
              </div>

              <div className="flex justify-end space-x-3 mt-6">
                <button
                  type="button"
                  onClick={() => {
                    setShowModal(false);
                    setFormData({
                      userId: '',
                      caseType: '',
                      priority: 'MEDIUM',
                      note: '',
                    });
                  }}
                  className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
                >
                  İptal
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700"
                >
                  Case Aç
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default FraudCases;