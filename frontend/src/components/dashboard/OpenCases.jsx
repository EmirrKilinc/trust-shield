import { CASE_STATUS, CASE_PRIORITY } from '../../utils/constants';
import { formatDate } from '../../utils/formatters';
import { ExternalLink } from 'lucide-react';
import { Link } from 'react-router-dom';

const OpenCases = ({ cases }) => {
  if (!cases || cases.length === 0) {
    return (
      <div className="text-center py-8 text-gray-500">
        Açık case bulunmuyor
      </div>
    );
  }

  return (
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
            <th className="text-left py-3 px-4 text-sm font-semibold text-gray-700"></th>
          </tr>
        </thead>
        <tbody>
          {cases.map((caseItem) => (
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
              <td className="py-3 px-4">
                <Link to={`/fraud-cases/${caseItem.caseId}`} className="text-blue-600 hover:text-blue-800">
                  <ExternalLink className="w-4 h-4" />
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default OpenCases;