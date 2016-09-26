import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ProductTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void constructor_productInstantiatesCorrectly_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertTrue(productOne instanceof Product);
  }

  @Test
  public void getName_getsCorrectProductName_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals("T-shirt", productOne.getName());
  }

  @Test
  public void getDescription_getsCorrectProductDescription_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals("100% cotton blend", productOne.getDescription());
  }

  @Test
  public void getPrice_getsCorrectProductPrice_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals(15, productOne.getPrice());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals(true, Product.all().get(0).equals(productOne));
  }

  @Test
  public void all_returnsAllProducts_true () {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Product productTwo = new Product("belt", "leather", 15);
    productTwo.save();
    assertEquals(true, Product.all().get(0).equals(productOne));
    assertEquals(true, Product.all().get(1).equals(productTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Product productTwo = new Product("T-shirt", "100% cotton blend", 15);
    productTwo.save();
    assertEquals(true, productOne.equals(productTwo));
  }

  @Test
  public void find_returnsProductWithSameId_true() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Product productTwo = new Product("belt", "leather", 15);
    productTwo.save();
    assertEquals(Product.find(productTwo.getId()), productTwo);
  }

  @Test
  public void update_updatesProduct_true() {
    Product productTwo = new Product("belt", "leather", 15);
    productTwo.save();
    productTwo.update("Suit", "wool", 200);
    assertEquals("Suit", Product.find(productTwo.getId()).getName());
    assertEquals("wool", Product.find(productTwo.getId()).getDescription());
    assertEquals(200, Product.find(productTwo.getId()).getPrice());
  }

  @Test
  public void delete_deletesProduct_null() {
    Product productOne = new Product("T-shirt", "100% cotton blend", 15);
    productOne.save();
    int deletedId = productOne.getId();
    productOne.delete();
    assertEquals(null, Product.find(deletedId));
  }

}
