package product;

//Observer pattern for notifications
//receive updates about orders
interface OrderObserver {
 void update(Order order);
}
