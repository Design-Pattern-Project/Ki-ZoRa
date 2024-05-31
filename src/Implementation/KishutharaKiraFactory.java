package Implementation;


import OnlineShopping.KiraFactory;
import OnlineShopping.Item;


public class KishutharaKiraFactory implements KiraFactory {
    @Override
    public Item createKira() {
        return new KishutharaKira();
    }
}