import java.util.ArrayList;
import java.util.List;


class TransactionManager {
    public static List<PaymentProcessor> getSampleTransactions() {
        List<PaymentProcessor> transactions = new ArrayList<>();
        
        transactions.add(new CreditCardPayment("TXN001", 1000000, "1234567890123456", "123", "12/28"));
        
        transactions.add(new CreditCardPayment("TXN002", 500000, "123456789012", "456", "10/26"));
        
        transactions.add(new EWalletPayment("TXN003", 200000, "0987654321", 300000));
        
        transactions.add(new EWalletPayment("TXN004", 500000, "0912345678", 100000));
        
        transactions.add(new BankTransferPayment("TXN005", 1500000, "987654321012", "Vietcombank"));
        
        transactions.add(new BankTransferPayment("TXN006", 800000, "111222333", ""));
        return transactions;
    }
}

public class App {
    public static void main(String[] args) {
        
        List<PaymentProcessor> transactions = TransactionManager.getSampleTransactions();

        
        System.out.println("=== BẮT ĐẦU CHẠY KỊCH BẢN KIỂM THỬ HỆ THỐNG THANH TOÁN ===");
        for (PaymentProcessor tx : transactions) {
            tx.processPayment(); 
        }
        System.out.println("\n=== HOÀN TẤT TẤT CẢ GIAO DỊCH ===");
    }
}