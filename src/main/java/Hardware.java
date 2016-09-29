import org.sql2o.*;
import java.util.List;

public class Hardware extends Product {

  public static final String DATABASE_TYPE = "hardware";

  public Hardware (String name, String description, int price){
    this.name = name;
    this.description = description;
    this.price = price;
    type = DATABASE_TYPE;
    inventory = MAX_INVENTORY/2;
  }

  public static List <Hardware> all() {
    String sql = "SELECT * FROM products WHERE type = 'hardware'";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Hardware.class);
    }
  }

  public static Hardware find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM products WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false).executeAndFetchFirst(Hardware.class);
    }
  }

  public static Hardware findProduct(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM products WHERE name = :name";
      return con.createQuery(sql)
        .addParameter("name", name)
        .throwOnMappingFailure(false).executeAndFetchFirst(Hardware.class);
    }
  }
}
