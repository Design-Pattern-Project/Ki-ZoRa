package product;

//Command pattern for order operations
//commands for updating order status
interface OrderCommand {
 void execute();
 void undo();
}

//Concrete command class for updating order status
class UpdateOrderStatus implements OrderCommand {
 private Order order;
 private OrderState newState;
 private OrderState oldState;

 public UpdateOrderStatus(Order order, OrderState newState) {
     this.order = order;
     this.newState = newState;
     this.oldState = order.getOrderState();
 }

 @Override
 public void execute() {
     order.setOrderState(newState);
 }

 @Override
 public void undo() {
     order.setOrderState(oldState);
 }
}