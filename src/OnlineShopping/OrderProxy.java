package OnlineShopping;

import java.util.List;

//Interface for the Proxy pattern
//additional logic to be executed before or after the actual order placement, 
//such as logging, security checks, or validation.
interface OrderProxy {
 void placeOrder(User customer, List<Item> items);
}
