package pl.pollub.cleancode.domain.message.enums;

// RETURN celowo nie ma strategii - w formularzu jest "niedostępny", a próba wysłania kończy się wyjątkiem
public enum MessageType {
    SHIPPING, PAYMENT, RETURN
}
