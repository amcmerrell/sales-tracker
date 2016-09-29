import org.sql2o.*;
import java.util.List;

public abstract class Product {
  public int id;
  public String name;
  public String description;
  public int price;
  public int inventory;
  public String type;

  public static final int MAX_INVENTORY = 10;
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

  public static String getType(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT type FROM products WHERE id = :id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false).executeAndFetchFirst(String.class);
    }
  }

  @Override
  public String toString(){
    return name;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO products (name, description, price, type, inventory) VALUES (:name, :description, :price, :type, :inventory)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .addParameter("price", this.price)
        .addParameter("type", this.type)
        .addParameter("inventory", this.inventory)
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

  public void subtractInventory() {
    this.inventory--;
    updateInventory();
  }

  public void subtractInventory(int amount) {
    this.inventory -= amount;
    updateInventory();
  }

  public void updateInventory(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE products SET inventory = :inventory WHERE id = :id";
      con.createQuery(sql)
        .addParameter("inventory", this.inventory)
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

  public void depleteInventory(int amount){
    if (!(inStock())){
       throw new UnsupportedOperationException("We are out of stock");
     } else {
       subtractInventory();
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
