import org.sql2o.*;
import java.util.List;

public abstract class Product {
  public int id;
  public String name;
  public String description;
  public int price;
  public int inventory;
  public String type;

  public static final int MAX_INVENTORY = 50;
  public static final int MIN_INVENTORY = 0;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getPrice() {
    return price;
  }

  public int getId() {
    return id;
  }

  public int getInventory() {
    return inventory;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO products (name, description, price, type) VALUES (:name, :description, :price, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .addParameter("price", this.price)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void update(String name, String description, int price) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE products SET name = :name, description=:description, price=:price WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("description", description)
        .addParameter("price", price)
        .addParameter("id", this.id)
        .throwOnMappingFailure(false)
        .executeUpdate();
    }
  }

  @Override
    public boolean equals(Object otherProduct) {
      if (!(otherProduct instanceof Product)) {
        return false;
      } else {
        Product newProduct = (Product) otherProduct;
        return this.getName().equals(newProduct.getName()) &&
               this.getDescription().equals(newProduct.getDescription()) &&
               this.getPrice() == newProduct.getPrice();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM products WHERE id =:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void depleteInventory(){
    if (!(inStock())){
       throw new UnsupportedOperationException("We are out of stock");
     } else {
    inventory--;
    }
  }

  public boolean inStock() {
    if (inventory <= MIN_INVENTORY) {
      return false;
    } else{
    return true;
    }
  }
}
