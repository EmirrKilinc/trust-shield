import { useState, useEffect } from 'react';
import { dashboardAPI } from '../api';
import { useApp } from '../context/AppContext';
import SummaryCard from '../components/dashboard/SummaryCard';
import RecentEvents from '../components/dashboard/RecentEvents';
import OpenCases from '../components/dashboard/OpenCases';
import ActiveRules from '../components/dashboard/ActiveRules';
import Card from '../components/common/Card';
import { AlertTriangle, Shield, Activity, CheckCircle, RefreshCw } from 'lucide-react';

const Dashboard = () => {
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [dashboardData, setDashboardData] = useState(null);
  const { addNotification } = useApp();

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async (showNotification = false) => {
    try {
      if (showNotification) {
        setRefreshing(true);
      } else {
        setLoading(true);
      }

      const response = await dashboardAPI.getSummary();
      setDashboardData(response.data);

      if (showNotification) {
        addNotification({
          type: 'success',
          message: 'Dashboard güncellendi',
        });
      }
    } catch (error) {
      console.error('Dashboard verisi yüklenirken hata:', error);
      addNotification({
        type: 'error',
        title: 'Hata',
        message: 'Dashboard yüklenirken bir hata oluştu',
      });
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  const handleRefresh = () => {
    fetchDashboardData(true);
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (!dashboardData) {
    return (
      <div className="flex items-center justify-center h-64">
        <p className="text-gray-500">Dashboard verisi yüklenemedi</p>
      </div>
    );
  }

  // Backend'den gelen verileri güvenli şekilde al
  const totalEvents = dashboardData.totalEvents || 0;
  const totalOpenCases = dashboardData.totalOpenCases || 0;
  const totalActiveRules = dashboardData.totalActiveRules || 0;
  const recentEvents = dashboardData.recentEvents || [];
  const openCases = dashboardData.openCases || [];
  const activeRules = dashboardData.activeRules || [];
  const riskProfiles = dashboardData.riskProfiles || [];
  
  // Yüksek riskli kullanıcı sayısını hesapla
  const highRiskUsers = riskProfiles.filter(p => p.riskLevel === 'HIGH').length;

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Dashboard</h1>
          <p className="text-gray-600 mt-1">TrustShield Fraud Detection System</p>
        </div>
        <button
          onClick={handleRefresh}
          disabled={refreshing}
          className="flex items-center px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <RefreshCw className={`w-4 h-4 mr-2 ${refreshing ? 'animate-spin' : ''}`} />
          Yenile
        </button>
      </div>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <SummaryCard
          title="Toplam Olaylar"
          value={totalEvents}
          icon={Activity}
          color="bg-blue-500"
          subtitle="Tüm olaylar"
        />
        <SummaryCard
          title="Açık Caseler"
          value={totalOpenCases}
          icon={AlertTriangle}
          color="bg-red-500"
          subtitle="İnceleme bekliyor"
        />
        <SummaryCard
          title="Yüksek Riskli"
          value={highRiskUsers}
          icon={Shield}
          color="bg-orange-500"
          subtitle="Kullanıcı"
        />
        <SummaryCard
          title="Aktif Kurallar"
          value={totalActiveRules}
          icon={CheckCircle}
          color="bg-green-500"
          subtitle="Çalışan kural"
        />
      </div>

      {/* Recent Events & Open Cases */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card 
          title="Son Olaylar"
          action={
            <span className="text-xs text-gray-500">
              {recentEvents.length} olay
            </span>
          }
        >
          <RecentEvents events={recentEvents.slice(0, 5)} />
        </Card>

        <Card 
          title="Açık Fraud Caseler"
          action={
            <span className="text-xs text-gray-500">
              {openCases.length} case
            </span>
          }
        >
          <OpenCases cases={openCases.slice(0, 5)} />
        </Card>
      </div>

      {/* Active Rules */}
      <Card 
        title="Aktif Risk Kuralları"
        action={
          <span className="text-xs text-gray-500">
            {activeRules.length} aktif kural
          </span>
        }
      >
        <ActiveRules rules={activeRules} />
      </Card>

      {/* Info Card */}
      <Card>
        <div className="flex items-start space-x-3">
          <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center flex-shrink-0">
            <Shield className="w-6 h-6 text-blue-600" />
          </div>
          <div>
            <h3 className="font-semibold text-gray-900 mb-1">TrustShield Aktif</h3>
            <p className="text-sm text-gray-600">
              Sistem aktif olarak çalışıyor. Tüm olaylar gerçek zamanlı olarak işleniyor ve risk 
              değerlendirmeleri yapılıyor. Yeni bir risk kuralı eklemek veya mevcut kuralları 
              yönetmek için Risk Kuralları sayfasını ziyaret edin.
            </p>
          </div>
        </div>
      </Card>
    </div>
  );
};

export default Dashboard;