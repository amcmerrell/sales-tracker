import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Transaction {
  int id;
  Timestamp date;
  int salePrice;
  int productId;
  int customerId;
  String productName;

  public static final int MONTHLY_SALES_GOAL = 10;
  public static final int QRTRLY_SALES_GOAL = 25;
  public static final long QUARTER = 5616000000l;
  public static final long MONTH = 2592000000l;


  public Transaction(int productId, int customerId) {
    this.productId = productId;
    String type = Product.getType(productId);
    Product product = null;
    if(type.equals("hardware")){
      product = Hardware.find(productId);
      this.productName = product.getName();
      this.salePrice = product.getPrice();
    }else{
      product = Clothing.find(productId);
      this.productName = product.getName();
      this.salePrice = product.getPrice();
    }
    this.customerId = customerId;
  }

  public int getSalePrice() {
    return salePrice;
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

  public Timestamp getDate() {
    return date;
  }

  public String getProductName(){
    return productName;
  }

  public String getFormattedDate(){
    SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd yyyy hh:mm a");
    return formatter.format(date);
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO transactions (customerId, productId, date, salePrice, productName) VALUES (:customerId, :productId, now(), :salePrice, :productName)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("customerId", this.customerId)
        .addParameter("productId", this.productId)
        .addParameter("salePrice", this.salePrice)
        .addParameter("productName", this.productName)
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

  public static Transaction find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM transactions WHERE id = :id";
      Transaction transaction = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Transaction.class);
      return transaction;
    }
  }

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

  public void delete() {
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM transactions WHERE id =:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static List<Transaction> findMonthlyTransactions() {
    try(Connection con = DB.sql2o.open()) {
      Timestamp rightNow = new Timestamp(new Date().getTime());
      Timestamp prior30 = new Timestamp(rightNow.getTime() - (MONTH));
      String sql = "SELECT * FROM transactions WHERE date BETWEEN '" + prior30 + "' and '" + rightNow + "'";
      return con.createQuery(sql)
        .executeAndFetch(Transaction.class);
    }
  }

  public static List<Transaction> findQuarterlyTransactions() {
    try(Connection con = DB.sql2o.open()) {
      Timestamp rightNow = new Timestamp(new Date().getTime());
      Timestamp priorQ = new Timestamp(rightNow.getTime() - (QUARTER));
      String sql = "SELECT * FROM transactions WHERE date BETWEEN '" + priorQ + "' and '" + rightNow + "'";
      return con.createQuery(sql)
        .executeAndFetch(Transaction.class);
    }
  }

  public static Integer sumMonthlySales() {
    try(Connection con = DB.sql2o.open()) {
      Timestamp rightNow = new Timestamp(new Date().getTime());
      Timestamp prior30 = new Timestamp(rightNow.getTime() - (MONTH));
      String sql = "SELECT SUM(salePrice) FROM transactions WHERE date BETWEEN '" + prior30 + "' and '" + rightNow + "'";
      return con.createQuery(sql).executeAndFetchFirst(Integer.class);
    }
  }

  public static Integer sumQuarterlySales() {
    try(Connection con = DB.sql2o.open()) {
      Timestamp rightNow = new Timestamp(new Date().getTime());
      Timestamp priorQ = new Timestamp(rightNow.getTime() - (QUARTER));
      String sql = "SELECT SUM(salePrice) FROM transactions WHERE date BETWEEN '" + priorQ + "' and '" + rightNow + "'";
      return con.createQuery(sql).executeAndFetchFirst(Integer.class);
    }
  }
}
