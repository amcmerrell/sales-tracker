import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class HardwareTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void constructor_productInstantiatesCorrectly_true() {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    assertTrue(productOne instanceof Hardware);
  }

  @Test
  public void getName_getsCorrectHardwareName_true() {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    assertEquals("wrench", productOne.getName());
  }

  @Test
  public void getDescription_getsCorrectHardwareDescription_true() {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    assertEquals("steel", productOne.getDescription());
  }

  @Test
  public void getPrice_getsCorrectHardwarePrice_true() {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    assertEquals(15, productOne.getPrice());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    assertEquals(true, Hardware.all().get(0).equals(productOne));
  }

  @Test
  public void all_returnsAllHardwares_true () {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    Hardware productTwo = new Hardware("screwdriver", "steel", 10);
    productTwo.save();
    assertEquals(true, Hardware.all().get(0).equals(productOne));
    assertEquals(true, Hardware.all().get(1).equals(productTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    Hardware productTwo = new Hardware("wrench", "steel", 15);
    productTwo.save();
    assertEquals(true, productOne.equals(productTwo));
  }

  @Test
  public void find_returnsHardwareWithSameId_true() {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    Hardware productTwo = new Hardware("screwdriver", "steel", 10);
    productTwo.save();
    assertEquals(Hardware.find(productTwo.getId()), productTwo);
  }

  @Test
  public void update_updatesHardware_true() {
    Hardware productTwo = new Hardware("screwdriver", "steel", 10);
    productTwo.save();
    productTwo.update("Suit", "wool", 200);
    assertEquals("Suit", Hardware.find(productTwo.getId()).getName());
    assertEquals("wool", Hardware.find(productTwo.getId()).getDescription());
    assertEquals(200, Hardware.find(productTwo.getId()).getPrice());
  }

  @Test
  public void delete_deletesHardware_null() {
    Hardware productOne = new Hardware("wrench", "steel", 15);
    productOne.save();
    int deletedId = productOne.getId();
    productOne.delete();
    assertEquals(null, Hardware.find(deletedId));
  }

  @Test(expected = UnsupportedOperationException.class)
    public void inventory_throwsExceptionIfInventoryLevelIsAtMinValue(){
      Hardware testHardware = new Hardware("wrench", "steel", 15);
      for(int i = Hardware.MIN_INVENTORY; i <= (Hardware.MAX_INVENTORY); i++){
        testHardware.depleteInventory();
      }
    }

  @Test
  public void product_inventoryCannotGoBeneathMinValue(){
    Hardware testHardware = new Hardware("wrench", "steel", 15);
    for(int i = Hardware.MAX_INVENTORY; i >= (Hardware.MIN_INVENTORY); i--){
      try {
        testHardware.depleteInventory();
      } catch (UnsupportedOperationException exception){ }
    }
    assertEquals(testHardware.getInventory(), Hardware.MIN_INVENTORY);
  }

  @Test
  public void getType_getsType(){
    Hardware testHardware = new Hardware("wrench", "steel", 15);
    testHardware.save();
    String type = Product.getType(testHardware.getId());
    assertEquals("hardware",type);
  }
}
