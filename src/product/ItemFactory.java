package product;

import java.awt.image.BufferedImage;

//factory Method
interface ItemFactory {

	Item createItem(String name, double price, String description, int quantity, BufferedImage image);
}