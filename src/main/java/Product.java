import org.sql2o.*;
import java.util.List;

public class Product {
  int id;
  String name;
  String description;
  int price;

  public Product(String name, String description, int price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

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

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO products (name, description, price) VALUES (:name, :description, :price)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("description", this.description)
        .addParameter("price", this.price)
        .executeUpdate()
        .getKey();
    }
  }

  public static List <Product> all() {
    String sql = "SELECT * From products";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Product.class);
    }
  }

  public static Product find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM products WHERE id = :id";
      Product product = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Product.class);
      return product;
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
}
