import org.sql2o.*;
import java.util.List;

public class Customer {
  int id;
  String name;
  String email;
  String address;

  public Customer(String name, String email, String address) {
    this.name = name;
    this.email = email;
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getAddress() {
    return address;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO customers (name, email, address) VALUES (:name, :email, :address)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("email", this.email)
        .addParameter("address", this.address)
        .executeUpdate()
        .getKey();
    }
  }

  public static List <Customer> all() {
    String sql = "SELECT * From customers";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Customer.class);
    }
  }

  public static Customer find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM customers WHERE id = :id";
      Customer customer = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Customer.class);
      return customer;
    }
  }

  public static Customer findName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM customers WHERE name = :name";
      Customer customer = con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetchFirst(Customer.class);
      return customer;
    }
  }

  public void update(String name, String email, String address) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE customers SET name = :name, email=:email, address=:address WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("email", email)
        .addParameter("address", address)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
    public boolean equals(Object otherCustomer) {
      if (!(otherCustomer instanceof Customer)) {
        return false;
      } else {
        Customer newCustomer = (Customer) otherCustomer;
        return this.getName().equals(newCustomer.getName()) &&
               this.getEmail().equals(newCustomer.getEmail()) &&
               this.getAddress().equals(newCustomer.getAddress());
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM customers WHERE id =:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
