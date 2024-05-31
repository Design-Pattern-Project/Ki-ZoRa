package OnlineShopping;

import java.util.ArrayList;
import java.util.List;

//Subject for order notifications
interface OrderSubject {
 void attach(OrderObserver observer);
 void detach(OrderObserver observer);
 void notifyObservers(Order order);
}

//Concrete subject class for order notifications
class OrderNotification implements OrderSubject {
 private List<OrderObserver> observers = new ArrayList<>();

 @Override
 public void attach(OrderObserver observer) {
     observers.add(observer);
 }

 @Override
 public void detach(OrderObserver observer) {
     observers.remove(observer);
 }

 @Override
 public void notifyObservers(Order order) {
     for (OrderObserver observer : observers) {
         observer.update(order);
     }
 }
}