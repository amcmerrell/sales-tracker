import org.sql2o.*;
import java.util.List;

public class Clothing extends Product {

  public static final String DATABASE_TYPE = "clothing";

  public Clothing (String name, String description, int price){
    this.name = name;
    this.description = description;
    this.price = price;
    // type = DATABASE_TYPE;
    inventory = MAX_INVENTORY/2;
  }

  public static List <Clothing> all() {
    String sql = "SELECT * From products";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Clothing.class);
    }
  }

  public static Clothing find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM products WHERE id = :id";
      Clothing testClothing = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false).executeAndFetchFirst(Clothing.class);
      return testClothing;
    }
  }
}
