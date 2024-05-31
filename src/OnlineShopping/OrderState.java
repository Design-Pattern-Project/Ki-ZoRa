package OnlineShopping;

//State pattern for order status
//states an order can be in, such as processing or shipped
interface OrderState {
 void processOrder(Order order);
}