package com.turkcell.demo.entity;

public enum ActionType {
    FORCE_2FA("Ek doğrulama zorunlu"),
    PAYMENT_REVIEW("İşlem incelemeye alındı"),
    TEMPORARY_BLOCK("Hesap geçici olarak bloke edildi"),
    OPEN_FRAUD_CASE("Fraud inceleme kaydı açıldı"),
    ANOMALY_ALERT("Anomali tespiti bildirimi"),
    NO_ACTION("Aksiyon alınmadı");

    private final String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}