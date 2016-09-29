import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Calendar;
import java.sql.Timestamp;

public class TransactionTest {
  //Timestamp myTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void constructor_transactionInstantiatesCorrectly_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionOne.save();
    assertTrue(transactionOne instanceof Transaction);
  }

  @Test
  public void getCustomerId_getsCorrectCustomerId_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionOne.save();
    assertEquals(customerOne.getId(), transactionOne.getCustomerId());
  }

  @Test
  public void getProductId_getsCorrectClothingId_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionOne.save();
    assertEquals(productOne.getId(), transactionOne.getProductId());
  }

  @Test
  public void getSalePrice_getsCorrectSalePrice_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionOne.save();
    assertEquals(productOne.getPrice(), transactionOne.getSalePrice());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionOne.save();
    assertEquals(true, Transaction.all().get(0).equals(transactionOne));
  }

  @Test
  public void all_returnsAllClothings_true () {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionOne.save();
    Transaction transactionTwo = new Transaction(20, 21, 15);
    transactionTwo.save();
    assertEquals(true, Transaction.all().get(0).equals(transactionOne));
    assertEquals(true, Transaction.all().get(1).equals(transactionTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Transaction transactionOne = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionOne.save();
    Transaction transactionTwo = new Transaction(productOne.getId(), customerOne.getId(), productOne.getPrice());
    transactionTwo.save();
    assertEquals(true, transactionOne.equals(transactionTwo));
  }

  @Test
  public void find_returnsClothingWithSameId_true() {
    Transaction transactionOne = new Transaction(1, 2, 15);
    transactionOne.save();
    Transaction transactionTwo = new Transaction(2, 1, 20);
    transactionTwo.save();
    assertEquals(Transaction.find(transactionTwo.getId()), transactionTwo);
  }

  @Test
  public void delete_deletesTransaction_null() {
    Transaction transactionOne = new Transaction(1, 2, 20);
    transactionOne.save();
    int deletedId = transactionOne.getId();
    transactionOne.delete();
    assertEquals(null, Transaction.find(deletedId));
  }

  @Test
  public void findMonthlyTransactions_returnsCorrectTransactions_true() {
    Transaction transactionOne = new Transaction(1, 2, 25);
    transactionOne.save();
    assertTrue(Transaction.findMonthlyTransactions().contains(transactionOne));
  }

  @Test
  public void sumMonthlySales_returnsTotalSalesForMonth_75() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Clothing productTwo = new Clothing("Expensive T-shirt", "fancy", 60);
    productTwo.save();
    Transaction transactionOne = new Transaction(productOne.getId(),2, productOne.getPrice());
    transactionOne.save();
    Transaction transactionTwo = new Transaction(productTwo.getId(),2, productTwo.getPrice());
    transactionTwo.save();
    assertEquals((Integer) 75, (Integer) Transaction.sumMonthlySales());
  }

}
