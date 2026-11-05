abstract class PaymentProcessor {
    protected String transactionId;
    protected double amount;

    public PaymentProcessor(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }


    public final void processPayment() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("[BƯỚC 1] Khởi tạo giao dịch: " + transactionId + " | Số tiền: " + amount + " VNĐ");

        if (!validatePaymentInfo()) {
            System.out.println("[LỖI] Thông tin thanh toán không hợp lệ! Hủy bỏ giao dịch ngay lập tức.");
            return;
        }
        System.out.println("[THÀNH CÔNG] Thông tin thanh toán hợp lệ.");

        boolean success = executePayment();
        if (success) {
            printReceipt();
        } else {
            System.out.println("[THẤT BẠI] Thanh toán không thành công. Hoàn tất quy trình.");
        }
    }

    protected abstract boolean validatePaymentInfo();
    protected abstract boolean executePayment();

    protected void printReceipt() {
        System.out.println("========== HÓA ĐƠN GIAO DỊCH ==========");
        System.out.println("Mã giao dịch : " + transactionId);
        System.out.println("Trạng thái   : THÀNH CÔNG");
        System.out.println("=======================================");
    }
}


class CreditCardPayment extends PaymentProcessor {
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public CreditCardPayment(String transactionId, double amount, String cardNumber, String cvv, String expiryDate) {
        super(transactionId, amount);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    protected boolean validatePaymentInfo() {
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            System.out.println(" -> Chi tiết lỗi: Số thẻ tín dụng phải có đúng 16 chữ số.");
            return false;
        }
        if (cvv == null || !cvv.matches("\\d{3}")) {
            System.out.println(" -> Chi tiết lỗi: Mã CVV phải có đúng 3 chữ số.");
            return false;
        }
        return true;
    }

    @Override
    protected boolean executePayment() {
        double fee = amount * 0.02;
        double totalToCharge = amount + fee;
        System.out.println("[Xử lý thẻ tín dụng] Phí giao dịch (2%): " + fee + " VNĐ. Tổng thu: " + totalToCharge + " VNĐ.");
        System.out.println(" -> Trừ tiền thành công từ tài khoản thẻ tín dụng.");
        return true;
    }
}


class EWalletPayment extends PaymentProcessor {
    private String phoneNumber;
    private double walletBalance;

    public EWalletPayment(String transactionId, double amount, String phoneNumber, double walletBalance) {
        super(transactionId, amount);
        this.phoneNumber = phoneNumber;
        this.walletBalance = walletBalance;
    }

    @Override
    protected boolean validatePaymentInfo() {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            System.out.println(" -> Chi tiết lỗi: Số điện thoại ví phải đủ 10 chữ số.");
            return false;
        }
        double totalNeeded = amount + 5000;
        if (walletBalance < totalNeeded) {
            System.out.println(" -> Chi tiết lỗi: Số dư trong ví (" + walletBalance + ") không đủ để thanh toán tổng tiền (" + totalNeeded + ").");
            return false;
        }
        return true;
    }

    @Override
    protected boolean executePayment() {
        double fee = 5000;
        double totalToCharge = amount + fee;
        walletBalance -= totalToCharge;
        System.out.println("[Xử lý Ví điện tử] Phí cố định: " + fee + " VNĐ. Tổng trừ: " + totalToCharge + " VNĐ.");
        System.out.println(" -> Số dư ví còn lại sau khi cập nhật: " + walletBalance + " VNĐ.");
        return true;
    }
}

 
class BankTransferPayment extends PaymentProcessor {
    private String accountNumber;
    private String bankName;

    public BankTransferPayment(String transactionId, double amount, String accountNumber, String bankName) {
        super(transactionId, amount);
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }

    @Override
    protected boolean validatePaymentInfo() {
        if (accountNumber == null || accountNumber.trim().isEmpty() || 
            bankName == null || bankName.trim().isEmpty()) {
            System.out.println(" -> Chi tiết lỗi: Số tài khoản và tên ngân hàng không được để trống.");
            return false;
        }
        return true;
    }

    @Override
    protected boolean executePayment() {
        String transferCode = "PAY_" + transactionId;
        System.out.println("[Xử lý Chuyển khoản] Miễn phí giao dịch (0 VNĐ).");
        System.out.println("-> HƯỚNG DẪN KHÁCH HÀNG:");
        System.out.println("   - Ngân hàng: " + bankName);
        System.out.println("   - Số tài khoản: " + accountNumber);
        System.out.println("   - Số tiền: " + amount + " VNĐ");
        System.out.println("   - Nội dung chuyển khoản chính xác: " + transferCode);
        return true;
    }
}