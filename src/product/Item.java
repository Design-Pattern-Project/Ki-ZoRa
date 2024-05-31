package product;

import java.awt.image.BufferedImage;

//Item class representing a product
class Item {
 private String name;
 private double price;
 private String description;
 private int quantity; // New attribute for product quantity
 private BufferedImage image;
 
 public Item(String name, double price, String description, int quantity, BufferedImage image) {
     this.name = name;
     this.price = price;
     this.description = description;
     this.quantity = quantity;
     this.image = image;
 }

 // Getter and setter methods for each attribute
 
 
 public String getName() {
     return name;
 }

 public void setName(String name) {
     this.name = name;
 }

 public double getPrice() {
     return price;
 }

 public void setPrice(double price) {
     this.price = price;
 }

 public String getDescription() {
     return description;
 }

 public void setDescription(String description) {
     this.description = description;
 }

 public int getQuantity() {
     return quantity;
 }

 public void setQuantity(int quantity) {
     this.quantity = quantity;
 }
 
 public BufferedImage getImage() {
     return image;
 }

 public void setImage(BufferedImage image) {
     this.image = image;
 }
 
 public void applyDiscount(double discountPercentage) {
     double discountedPrice = price * (1 - discountPercentage / 100);
     setPrice(discountedPrice);
 }
}