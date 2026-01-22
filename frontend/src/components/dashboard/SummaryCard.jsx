const SummaryCard = ({ title, value, icon: Icon, color, subtitle }) => {
  // Value'yu güvenli şekilde render et
  const renderValue = () => {
    if (value === null || value === undefined) return '0';
    if (typeof value === 'object') return '0';
    return value.toString();
  };

  return (
    <div className="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm text-gray-600 mb-1">{title}</p>
          <p className="text-3xl font-bold text-gray-900">{renderValue()}</p>
          {subtitle && <p className="text-xs text-gray-500 mt-1">{subtitle}</p>}
        </div>
        <div className={`w-12 h-12 ${color} rounded-lg flex items-center justify-center`}>
          <Icon className="w-6 h-6 text-white" />
        </div>
      </div>
    </div>
  );
};

export default SummaryCard;