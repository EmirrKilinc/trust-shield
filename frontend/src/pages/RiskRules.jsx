import { useState, useEffect } from 'react';
import { riskRulesAPI } from '../api';
import Card from '../components/common/Card';
import { ACTIONS } from '../utils/constants';
import { Plus, Edit, Power, Trash2 } from 'lucide-react';
import { useApp } from '../context/AppContext';

const RiskRules = () => {
  const [rules, setRules] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingRule, setEditingRule] = useState(null);
  const [formData, setFormData] = useState({
    condition: '',
    action: 'NO_ACTION',
    priority: 1,
  });

  const { addNotification } = useApp();

  useEffect(() => {
    fetchRules();
  }, []);

  const fetchRules = async () => {
    try {
      setLoading(true);
      const response = await riskRulesAPI.getAllRules();
      setRules(response.data);
    } catch (error) {
      console.error('Kurallar yüklenirken hata:', error);
    } finally {
      setLoading(false);
    }
  };

 const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    if (editingRule) {
      await riskRulesAPI.updateRule(editingRule.id, formData);
      addNotification({
        type: 'success',
        title: 'Başarılı',
        message: 'Kural başarıyla güncellendi',
      });
    } else {
      await riskRulesAPI.createRule(formData);
      addNotification({
        type: 'success',
        title: 'Başarılı',
        message: 'Yeni kural başarıyla eklendi',
      });
    }
    setShowModal(false);
    setEditingRule(null);
    setFormData({ condition: '', action: 'NO_ACTION', priority: 1 });
    fetchRules();
  } catch (error) {
    console.error('Kural kaydedilirken hata:', error);
    addNotification({
      type: 'error',
      title: 'Hata',
      message: 'Kural kaydedilirken bir hata oluştu',
    });
  }
};

  const handleEdit = (rule) => {
    setEditingRule(rule);
    setFormData({
      condition: rule.condition,
      action: rule.action,
      priority: rule.priority,
    });
    setShowModal(true);
  };

const handleToggleActive = async (rule) => {
  try {
    await riskRulesAPI.activateRule(rule.id, !rule.active);
    addNotification({
      type: 'success',
      message: `Kural ${!rule.active ? 'aktif' : 'pasif'} hale getirildi`,
    });
    fetchRules();
  } catch (error) {
    console.error('Kural durumu değiştirilirken hata:', error);
    addNotification({
      type: 'error',
      message: 'Kural durumu değiştirilemedi',
    });
  }
};
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
          <h1 className="text-3xl font-bold text-gray-900">Risk Kuralları</h1>
          <p className="text-gray-600 mt-1">Otomatik risk değerlendirme kuralları</p>
        </div>
        <button
          onClick={() => setShowModal(true)}
          className="flex items-center px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
        >
          <Plus className="w-4 h-4 mr-2" />
          Yeni Kural
        </button>
      </div>

      {/* Rules Table */}
      <Card>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">ID</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Koşul</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Aksiyon</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Öncelik</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Durum</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">İşlemler</th>
              </tr>
            </thead>
            <tbody>
              {rules.map((rule) => (
                <tr key={rule.id} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4 text-sm font-mono text-gray-600">{rule.id}</td>
                  <td className="py-3 px-4 text-sm text-gray-900">{rule.condition}</td>
                  <td className="py-3 px-4">
                    <span className={`inline-flex px-2 py-1 text-xs font-medium rounded ${ACTIONS[rule.action]?.color || 'bg-gray-500'} text-white`}>
                      {ACTIONS[rule.action]?.label || rule.action}
                    </span>
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-900">{rule.priority}</td>
                  <td className="py-3 px-4">
                    <span className={`inline-flex px-2 py-1 text-xs font-medium rounded ${rule.active ? 'bg-green-500' : 'bg-gray-400'} text-white`}>
                      {rule.active ? 'Aktif' : 'Pasif'}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <div className="flex items-center space-x-2">
                      <button
                        onClick={() => handleEdit(rule)}
                        className="p-1 text-blue-600 hover:bg-blue-50 rounded"
                        title="Düzenle"
                      >
                        <Edit className="w-4 h-4" />
                      </button>
                      <button
                        onClick={() => handleToggleActive(rule)}
                        className={`p-1 ${rule.active ? 'text-gray-600' : 'text-green-600'} hover:bg-gray-100 rounded`}
                        title={rule.active ? 'Pasif Yap' : 'Aktif Yap'}
                      >
                        <Power className="w-4 h-4" />
                      </button>
                    </div>
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
            <h2 className="text-xl font-bold text-gray-900 mb-4">
              {editingRule ? 'Kuralı Düzenle' : 'Yeni Kural Ekle'}
            </h2>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Koşul
                </label>
                <textarea
                  value={formData.condition}
                  onChange={(e) => setFormData({ ...formData, condition: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  rows="3"
                  required
                  placeholder="Örn: BiP yeni cihaz + ip_risk=high"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Aksiyon
                </label>
                <select
                  value={formData.action}
                  onChange={(e) => setFormData({ ...formData, action: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                >
                  {Object.keys(ACTIONS).map((key) => (
                    <option key={key} value={key}>
                      {ACTIONS[key].label}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Öncelik
                </label>
                <input
                  type="number"
                  value={formData.priority}
                  onChange={(e) => setFormData({ ...formData, priority: parseInt(e.target.value) })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  min="1"
                  required
                />
              </div>

              <div className="flex justify-end space-x-3 mt-6">
                <button
                  type="button"
                  onClick={() => {
                    setShowModal(false);
                    setEditingRule(null);
                    setFormData({ condition: '', action: 'NO_ACTION', priority: 1 });
                  }}
                  className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
                >
                  İptal
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                  {editingRule ? 'Güncelle' : 'Ekle'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default RiskRules;