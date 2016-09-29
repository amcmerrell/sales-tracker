import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class ClothingTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void constructor_productInstantiatesCorrectly_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertTrue(productOne instanceof Clothing);
  }

  @Test
  public void getName_getsCorrectClothingName_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals("T-shirt", productOne.getName());
  }

  @Test
  public void getDescription_getsCorrectClothingDescription_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals("100% cotton blend", productOne.getDescription());
  }

  @Test
  public void getPrice_getsCorrectClothingPrice_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals(15, productOne.getPrice());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    assertEquals(true, Clothing.all().get(0).equals(productOne));
  }

  @Test
  public void all_returnsAllClothings_true () {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Clothing productTwo = new Clothing("belt", "leather", 15);
    productTwo.save();
    assertEquals(true, Clothing.all().get(0).equals(productOne));
    assertEquals(true, Clothing.all().get(1).equals(productTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Clothing productTwo = new Clothing("T-shirt", "100% cotton blend", 15);
    productTwo.save();
    assertEquals(true, productOne.equals(productTwo));
  }

  @Test
  public void find_returnsClothingWithSameId_true() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    Clothing productTwo = new Clothing("belt", "leather", 15);
    productTwo.save();
    assertEquals(Clothing.find(productTwo.getId()), productTwo);
  }

  @Test
  public void update_updatesClothing_true() {
    Clothing productTwo = new Clothing("belt", "leather", 15);
    productTwo.save();
    productTwo.update("Suit", "wool", 200);
    assertEquals("Suit", Clothing.find(productTwo.getId()).getName());
    assertEquals("wool", Clothing.find(productTwo.getId()).getDescription());
    assertEquals(200, Clothing.find(productTwo.getId()).getPrice());
  }

  @Test
  public void delete_deletesClothing_null() {
    Clothing productOne = new Clothing("T-shirt", "100% cotton blend", 15);
    productOne.save();
    int deletedId = productOne.getId();
    productOne.delete();
    assertEquals(null, Clothing.find(deletedId));
  }

  // @Test(expected = UnsupportedOperationException.class)
  //   public void inventory_throwsExceptionIfInventoryLevelIsAtMinValue(){
  //     Clothing testClothing = new Clothing("T-shirt", "100% cotton blend", 15);
  //     for(int i = Clothing.MIN_INVENTORY; i <= (Clothing.MAX_INVENTORY); i++){
  //       testClothing.depleteInventory();
  //     }
  //   }
  //
  // @Test
  // public void product_inventoryCannotGoBeneathMinValue(){
  //   Clothing testClothing = new Clothing("T-shirt", "100% cotton blend", 15);
  //   for(int i = Clothing.MAX_INVENTORY; i >= (Clothing.MIN_INVENTORY); i--){
  //     try {
  //       testClothing.depleteInventory();
  //     } catch (UnsupportedOperationException exception){ }
  //   }
  //   assertEquals(testClothing.getInventory(), Clothing.MIN_INVENTORY);
  // }
}
