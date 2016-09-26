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

  // @Test
  // public void find_returnsProductWithSameId_true() {
  //   Product productOne = new Product("T-shirt", "100% cotton blend", 15);
  //   productOne.save();
  //   Product productTwo = new Product("belt", "leather", 15);
  //   productTwo.save();
  //   assertEquals(Product.find(productTwo.getId()), productTwo);
  // }
  //
  // @Test
  // public void update_updatesProduct_true() {
  //   Product productTwo = new Product("belt", "leather", 15);
  //   productTwo.save();
  //   productTwo.update("Suit", "wool", 200);
  //   assertEquals("Suit", Product.find(productTwo.getId()).getName());
  //   assertEquals("wool", Product.find(productTwo.getId()).getDescription());
  //   assertEquals(200, Product.find(productTwo.getId()).getPrice());
  // }
  //
  // @Test
  // public void delete_deletesProduct_null() {
  //   Product productOne = new Product("T-shirt", "100% cotton blend", 15);
  //   productOne.save();
  //   int deletedId = productOne.getId();
  //   productOne.delete();
  //   assertEquals(null, Product.find(deletedId));
  // }

}
