package OnlineShopping;

public class AppError extends RuntimeException {
    private final int statusCode;
    private final String status;

    public AppError(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.status = String.valueOf(statusCode).startsWith("4") ? "fail" : "error";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatus() {
        return status;
    }
}


