// Risk Levels
export const RISK_LEVELS = {
  LOW: { label: 'Düşük', color: 'bg-green-500', textColor: 'text-green-700', bgLight: 'bg-green-50' },
  MEDIUM: { label: 'Orta', color: 'bg-yellow-500', textColor: 'text-yellow-700', bgLight: 'bg-yellow-50' },
  HIGH: { label: 'Yüksek', color: 'bg-red-500', textColor: 'text-red-700', bgLight: 'bg-red-50' },
};

// Case Status
export const CASE_STATUS = {
  OPEN: { label: 'Açık', color: 'bg-blue-500', textColor: 'text-blue-700' },
  IN_PROGRESS: { label: 'İşlemde', color: 'bg-orange-500', textColor: 'text-orange-700' },
  CLOSED: { label: 'Kapalı', color: 'bg-gray-500', textColor: 'text-gray-700' },
};

// Case Priority
export const CASE_PRIORITY = {
  LOW: { label: 'Düşük', color: 'bg-green-500' },
  MEDIUM: { label: 'Orta', color: 'bg-yellow-500' },
  HIGH: { label: 'Yüksek', color: 'bg-orange-500' },
  CRITICAL: { label: 'Kritik', color: 'bg-red-500' },
};

// Event Types
export const EVENT_TYPES = {
  PAYMENT: { label: 'Ödeme', icon: '💳' },
  LOGIN: { label: 'Giriş', icon: '🔐' },
  USAGE: { label: 'Kullanım', icon: '📊' },
  SUBSCRIPTION: { label: 'Abonelik', icon: '📝' },
  CHARGEBACK: { label: 'İade', icon: '↩️' },
};

// Actions
export const ACTIONS = {
  FORCE_2FA: { label: 'Ek Doğrulama Zorla', color: 'bg-blue-500' },
  PAYMENT_REVIEW: { label: 'Ödeme İnceleme', color: 'bg-yellow-500' },
  TEMPORARY_BLOCK: { label: 'Geçici Blok', color: 'bg-orange-500' },
  OPEN_FRAUD_CASE: { label: 'Fraud Case Aç', color: 'bg-red-500' },
  ANOMALY_ALERT: { label: 'Anomali Uyarısı', color: 'bg-purple-500' },
  NO_ACTION: { label: 'Aksiyon Yok', color: 'bg-gray-500' },
};

// Services
export const SERVICES = {
  PAYCELL: { label: 'Paycell', color: 'bg-purple-500' },
  BIP: { label: 'BiP', color: 'bg-blue-500' },
  SUPERONLINE: { label: 'Superonline', color: 'bg-green-500' },
  'TV+': { label: 'TV+', color: 'bg-red-500' },
};