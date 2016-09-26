import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class TransactionTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void constructor_transactionInstantiatesCorrectly_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId());
    transactionOne.save();
    assertTrue(transactionOne instanceof Transaction);
  }

  @Test
  public void getCustomerId_getsCorrectCustomerId_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId());
    transactionOne.save();
    assertEquals(customerOne.getId(), transactionOne.getCustomerId());
  }

  @Test
  public void getProductId_getsCorrectProductId_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId());
    transactionOne.save();
    assertEquals(productOne.getId(), transactionOne.getProductId());
  }

  // @Test
  // public void getSalePrice_getsCorrectSalePrice_true() {
  //   Product productOne = new Product("T-shirt", "100% cotton blend", 15);
  //   productOne.save();
  //   Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
  //   customerOne.save();
  //   Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId());
  //   transactionOne.save();
  //   assertEquals(productOne.getPrice(), transactionOne.getSalePrice());
  // }

  @Test
  public void save_savesIntoDatabase_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId());
    transactionOne.save();
    assertEquals(true, Transaction.all().get(0).equals(transactionOne));
  }

  @Test
  public void all_returnsAllProducts_true () {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId());
    transactionOne.save();
    Transaction transactionTwo = new Transaction(20, 21);
    transactionTwo.save();
    assertEquals(true, Transaction.all().get(0).equals(transactionOne));
    assertEquals(true, Transaction.all().get(1).equals(transactionTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId());
    transactionOne.save();
    Transaction transactionTwo = new Transaction(productOne.getId(), customerOne.getId());
    transactionTwo.save();
    assertEquals(true, transactionOne.equals(transactionTwo));
  }

  @Test
  public void find_returnsProductWithSameId_true() {
    Transaction transactionOne = new Transaction(1,2);
    transactionOne.save();
    Transaction transactionTwo = new Transaction(2,1);
    transactionTwo.save();
    assertEquals(Transaction.find(transactionTwo.getId()), transactionTwo);
  }

  @Test
  public void delete_deletesTransaction_null() {
    Transaction transactionOne = new Transaction(1,2);
    transactionOne.save();
    int deletedId = transactionOne.getId();
    transactionOne.delete();
    assertEquals(null, Transaction.find(deletedId));
  }

  @Test
  public void findMonthlyTransactions_returnsCorrectTransactions_true() {
    Transaction transactionOne = new Transaction(1,2);
    transactionOne.save();
    assertTrue(Transaction.findMonthlyTransactions().contains(transactionOne));
  }

  // @Test
  // public void sumMonthlySales_returnsTotalSalesForMonth_75() {
  //   Product productOne = new Product("T-shirt", "100% cotton blend", 15);
  //   productOne.save();
  //   Product productTwo = new Product("Expensive T-shirt", "fancy", 60);
  //   productTwo.save();
  //   Transaction transactionOne = new Transaction(productOne.getId(),2);
  //   transactionOne.save();
  //   Transaction transactionTwo = new Transaction(productTwo.getId(),2);
  //   transactionTwo.save();
  //   assertEquals(75, Transaction)
  // }
}
