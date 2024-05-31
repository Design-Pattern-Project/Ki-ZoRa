package product;

//Strategy pattern for payment
//multiple payment methods to be defined and selected dynamically
abstract class PaymentStrategy {
 abstract void pay(double amount);
}

//Concrete payment strategy class
class Payment extends PaymentStrategy {
 @Override
 void pay(double amount) {
     // Payment implementation using  currency
 }
}

