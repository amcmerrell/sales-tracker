import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/sales_tracker_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteProductQuery = "DELETE FROM products *;";
      String deleteCustomerQuery = "DELETE FROM customers *;";
      String deleteTransactionQuery = "DELETE FROM transactions *;";
      con.createQuery(deleteProductQuery).executeUpdate();
      con.createQuery(deleteCustomerQuery).executeUpdate();
      con.createQuery(deleteTransactionQuery).executeUpdate();
    }
  }
}
