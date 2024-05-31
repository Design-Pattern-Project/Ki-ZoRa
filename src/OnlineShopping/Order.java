package OnlineShopping;

import java.util.List;

//Order class
public class Order {
 private User customer;
 private List<Item> items;
 private OrderState orderState;

 public Order(User customer, List<Item> items) {
     this.customer = customer;
     this.items = items;
 }

 // Getter and setter methods for each attribute
 public User getCustomer() {
     return customer;
 }

 public void setCustomer(User customer) {
     this.customer = customer;
 }

 public List<Item> getItems() {
     return items;
 }

 public void setItems(List<Item> items) {
     this.items = items;
 }
 
 public OrderState getOrderState() {
     return orderState;
 }

 public void setOrderState(OrderState orderState) {
     this.orderState = orderState;
 }
}
