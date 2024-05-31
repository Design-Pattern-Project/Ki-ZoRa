package OnlineShopping;

//interface for Validation handler 
public interface ValidationHandler{
	void setNextHandler(ValidationHandler handler);
	void handleValidationRequest(User user) throws AppError;
}