import { EVENT_TYPES, SERVICES } from '../../utils/constants';
import { formatDate, formatCurrency } from '../../utils/formatters';

const RecentEvents = ({ events }) => {
  if (!events || events.length === 0) {
    return (
      <div className="text-center py-8 text-gray-500">
        Henüz olay bulunmuyor
      </div>
    );
  }

  return (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead>
          <tr className="border-b border-gray-200">
            <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Kullanıcı</th>
            <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Servis</th>
            <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Olay Tipi</th>
            <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Tutar</th>
            <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700">Zaman</th>
          </tr>
        </thead>
        <tbody>
          {events.map((event) => (
            <tr key={event.id} className="border-b border-gray-100 hover:bg-gray-50">
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
              <td className="py-3 px-4 text-sm text-gray-600">{formatDate(event.timestamp)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default RecentEvents;