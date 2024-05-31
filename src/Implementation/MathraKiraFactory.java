package Implementation;

import OnlineShopping.KiraFactory;
import OnlineShopping.Item;


public class MathraKiraFactory implements KiraFactory {
    @Override
    public Item createKira() {
        return new MathraKira();
    }
}