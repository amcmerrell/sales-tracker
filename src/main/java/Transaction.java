import org.sql2o.*;
import java.util.List;

public class Transaction {
  int id;
  String date;
  int productId;
  int customerId;

  public Transaction(int productId, int customerId) {
    this.productId = productId;
    this.customerId = customerId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public int getProductId() {
    return productId;
  }

  public int getId() {
    return id;
  }

  public String getDate() {
    return date;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO transactions (customerId, productId, date) VALUES (:customerId, :productId, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("customerId", this.customerId)
        .addParameter("productId", this.productId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List <Transaction> all() {
    String sql = "SELECT * FROM transactions";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Transaction.class);
    }
  }

  // public static Transaction find(int id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM transactions WHERE id = :id";
  //     Transaction transaction = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(Transaction.class);
  //     return transaction;
  //   }
  // }
  //
  @Override
    public boolean equals(Object otherTransaction) {
      if (!(otherTransaction instanceof Transaction)) {
        return false;
      } else {
        Transaction newTransaction = (Transaction) otherTransaction;
        return this.getCustomerId() == newTransaction.getCustomerId() &&
               this.getProductId() == newTransaction.getProductId();
    }
  }
  //
  // public void delete() {
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "DELETE FROM customers WHERE id =:id";
  //     con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }
}
