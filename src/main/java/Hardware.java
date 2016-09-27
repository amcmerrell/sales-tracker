import org.sql2o.*;
import java.util.List;

public class Hardware extends Product {

  public static final String DATABASE_TYPE = "hardware";

    public Hardware (String name, String description, int price){
      this.name = name;
      this.description = description;
      this.price = price;
      // type = DATABASE_TYPE;
      inventory = MAX_INVENTORY/2;
    }
}
