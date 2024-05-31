package product;

//Observer pattern for notifications
public interface UserObserver {
void getMessage();
}
//Observer pattern Subject
interface AdminSubject {
	 void registeredUser(User user);
	 void notRegistered(User user);
	 void sendMessage(String message);
}
