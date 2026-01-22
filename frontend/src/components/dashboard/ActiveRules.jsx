import { ACTIONS } from '../../utils/constants';
import { CheckCircle } from 'lucide-react';

const ActiveRules = ({ rules }) => {
  if (!rules || rules.length === 0) {
    return (
      <div className="text-center py-8 text-gray-500">
        Aktif kural bulunmuyor
      </div>
    );
  }

  return (
    <div className="space-y-3">
      {rules.slice(0, 5).map((rule) => (
        <div key={rule.id} className="flex items-start space-x-3 p-4 bg-gray-50 rounded-lg border border-gray-200">
          <CheckCircle className="w-5 h-5 text-green-500 mt-0.5 flex-shrink-0" />
          <div className="flex-1 min-w-0">
            <div className="flex items-center justify-between mb-1">
              <span className="text-xs font-mono text-gray-500">{rule.id}</span>
              <span className={`inline-flex px-2 py-1 text-xs font-medium rounded ${ACTIONS[rule.action]?.color || 'bg-gray-500'} text-white`}>
                {ACTIONS[rule.action]?.label || rule.action}
              </span>
            </div>
            <p className="text-sm text-gray-700">{rule.condition}</p>
            <p className="text-xs text-gray-500 mt-1">Öncelik: {rule.priority}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default ActiveRules;