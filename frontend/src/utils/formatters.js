import { format, formatDistance, parseISO } from 'date-fns';
import { tr } from 'date-fns/locale';

// Tarih formatı
export const formatDate = (date) => {
  if (!date) return '-';
  try {
    return format(parseISO(date), 'dd MMM yyyy HH:mm', { locale: tr });
  } catch {
    return date;
  }
};

// Tarih - relatif (2 saat önce)
export const formatRelativeDate = (date) => {
  if (!date) return '-';
  try {
    return formatDistance(parseISO(date), new Date(), { 
      addSuffix: true,
      locale: tr 
    });
  } catch {
    return date;
  }
};

// Para formatı
export const formatCurrency = (amount, currency = 'TRY') => {
  if (amount == null) return '-';
  return new Intl.NumberFormat('tr-TR', {
    style: 'currency',
    currency: currency,
  }).format(amount);
};

// Sayı formatı
export const formatNumber = (number) => {
  if (number == null) return '-';
  return new Intl.NumberFormat('tr-TR').format(number);
};

// Risk skoru formatı
export const formatRiskScore = (score) => {
  if (score == null) return '-';
  return `${Math.round(score)}/100`;
};

// Kısa ID formatı
export const formatShortId = (id) => {
  if (!id) return '-';
  return id.length > 10 ? `${id.substring(0, 10)}...` : id;
};