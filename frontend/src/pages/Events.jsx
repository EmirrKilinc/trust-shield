import { useState, useEffect } from 'react';
import { eventsAPI } from '../api';
import { useApp } from '../context/AppContext';
import Card from '../components/common/Card';
import { EVENT_TYPES, SERVICES } from '../utils/constants';
import { formatDate, formatCurrency } from '../utils/formatters';
import { Plus, Filter } from 'lucide-react';

const Events = () => {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const { addNotification } = useApp();
  
  const [formData, setFormData] = useState({
    userId: '',
    service: 'Paycell',
    eventType: 'PAYMENT',
    amount: '',
    unit: 'TRY',
    meta: '',
  });

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const response = await eventsAPI.getAllEvents();
      setEvents(response.data);
    } catch (error) {
      console.error('Olaylar yüklenirken hata:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await eventsAPI.createEvent({
        ...formData,
        amount: parseFloat(formData.amount) || 0,
      });
      
      addNotification({
        type: 'success',
        title: 'Başarılı',
        message: 'Yeni olay başarıyla oluşturuldu',
      });
      
      setShowModal(false);
      setFormData({
        userId: '',
        service: 'Paycell',
        eventType: 'PAYMENT',
        amount: '',
        unit: 'TRY',
        meta: '',
      });
      fetchEvents();
    } catch (error) {
      console.error('Olay oluşturulurken hata:', error);
      addNotification({
        type: 'error',
        title: 'Hata',
        message: 'Olay oluşturulurken bir hata oluştu',
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
          <h1 className="text-3xl font-bold text-gray-900">Olaylar</h1>
          <p className="text-gray-600 mt-1">Tüm sistem olayları</p>
        </div>
        <div className="flex space-x-3">
          <button className="flex items-center px-4 py-2 bg-white border border-gray-300 rounded-lg hover:bg-gray-50">
            <Filter className="w-4 h-4 mr-2" />
            Filtrele
          </button>
          <button 
            onClick={() => setShowModal(true)}
            className="flex items-center px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
          >
            <Plus className="w-4 h-4 mr-2" />
            Yeni Olay
          </button>
        </div>
      </div>

      {/* Events Table */}
      <Card>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">ID</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Kullanıcı</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Servis</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Olay Tipi</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Tutar</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Meta</th>
                <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Zaman</th>
              </tr>
            </thead>
            <tbody>
              {events.map((event) => (
                <tr key={event.id} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-3 px-4 text-sm font-mono text-gray-600">{event.id}</td>
                  <td className="py-3 px-4 text-sm text-gray-900">{event.userId}</td>
                  <td className="py-3 px-4">
                    <span className={`inline-flex px-2 py-1 text-xs font-medium rounded ${SERVICES[event.service]?.color || 'bg-gray-500'} text-white`}>
                      {SERVICES[event.service]?.label || event.service}
                    </span>
                  </td>
                  <td className="py-3 px-4">
                    <span className="text-sm">
                      {EVENT_TYPES[event.eventType]?.icon} {EVENT_TYPES[event.eventType]?.label || event.eventType}
                    </span>
                  </td>
                  <td className="py-3 px-4 text-sm font-medium text-gray-900">
                    {event.amount ? formatCurrency(event.amount, event.unit) : '-'}
                  </td>
                  <td className="py-3 px-4 text-sm text-gray-600">{event.meta || '-'}</td>
                  <td className="py-3 px-4 text-sm text-gray-600">{formatDate(event.timestamp)}</td>
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
            <h2 className="text-xl font-bold text-gray-900 mb-4">Yeni Olay Oluştur</h2>
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
                  Servis
                </label>
                <select
                  value={formData.service}
                  onChange={(e) => setFormData({ ...formData, service: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                >
                  {Object.keys(SERVICES).map((key) => (
                    <option key={key} value={key}>
                      {SERVICES[key].label}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Olay Tipi
                </label>
                <select
                  value={formData.eventType}
                  onChange={(e) => setFormData({ ...formData, eventType: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  required
                >
                  {Object.keys(EVENT_TYPES).map((key) => (
                    <option key={key} value={key}>
                      {EVENT_TYPES[key].icon} {EVENT_TYPES[key].label}
                    </option>
                  ))}
                </select>
              </div>

              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Tutar
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    value={formData.amount}
                    onChange={(e) => setFormData({ ...formData, amount: e.target.value })}
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                    placeholder="0.00"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Birim
                  </label>
                  <select
                    value={formData.unit}
                    onChange={(e) => setFormData({ ...formData, unit: e.target.value })}
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="TRY">TRY</option>
                    <option value="USD">USD</option>
                    <option value="EUR">EUR</option>
                  </select>
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Meta (Opsiyonel)
                </label>
                <input
                  type="text"
                  value={formData.meta}
                  onChange={(e) => setFormData({ ...formData, meta: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Örn: merchant=CryptoExchange"
                />
              </div>

              <div className="flex justify-end space-x-3 mt-6">
                <button
                  type="button"
                  onClick={() => {
                    setShowModal(false);
                    setFormData({
                      userId: '',
                      service: 'Paycell',
                      eventType: 'PAYMENT',
                      amount: '',
                      unit: 'TRY',
                      meta: '',
                    });
                  }}
                  className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
                >
                  İptal
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                  Oluştur
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Events;