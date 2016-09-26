import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class CustomerTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void constructor_customerInstantiatesCorrectly_true() {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    assertTrue(customerOne instanceof Customer);
  }

  @Test
  public void getName_getsCorrectCustomerName_true() {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    assertEquals("Andrew", customerOne.getName());
  }

  @Test
  public void getEmail_getsCorrectCustomerEmail_true() {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    assertEquals("andrew@email.com", customerOne.getEmail());
  }

  @Test
  public void getAddress_getsCorrectCustomerAddress_true() {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    assertEquals("2270 Portland Pl. Portland, OR 97210", customerOne.getAddress());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    assertEquals(true, Customer.all().get(0).equals(customerOne));
  }

  @Test
  public void all_returnsAllCustomers_true () {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Customer customerTwo = new Customer("Brian", "brian@email.com", "1111 Pearl Ave. Portland, OR 97209");
    customerTwo.save();
    assertEquals(true, Customer.all().get(0).equals(customerOne));
    assertEquals(true, Customer.all().get(1).equals(customerTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Customer customerTwo = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerTwo.save();
    assertEquals(true, customerOne.equals(customerTwo));
  }

  @Test
  public void find_returnsCustomerWithSameId_true() {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Customer customerTwo = new Customer("Brian", "brian@email.com", "1111 Pearl Ave. Portland, OR 97209");
    customerTwo.save();
    assertEquals(Customer.find(customerTwo.getId()), customerTwo);
  }

  @Test
  public void update_updatesCustomer_true() {
    Customer customerTwo = new Customer("Brian", "brian@email.com", "1111 Pearl Ave. Portland, OR 97209");
    customerTwo.save();
    customerTwo.update("Sarah", "sarah@email.com", "2222 Another St. Seattle, WA 97210");
    assertEquals("Sarah", Customer.find(customerTwo.getId()).getName());
    assertEquals("sarah@email.com", Customer.find(customerTwo.getId()).getEmail());
    assertEquals("2222 Another St. Seattle, WA 97210", Customer.find(customerTwo.getId()).getAddress());
  }

  @Test
  public void delete_deletesCustomer_null() {
    Customer customerTwo = new Customer("Brian", "brian@email.com", "1111 Pearl Ave. Portland, OR 97209");
    customerTwo.save();
    int deletedId = customerTwo.getId();
    customerTwo.delete();
    assertEquals(null, Customer.find(deletedId));
  }

  @Test
  public void findName_findsCustomerByName_Andrew() {
    Customer customerOne = new Customer("Andrew", "andrew@email.com", "2270 Portland Pl. Portland, OR 97210");
    customerOne.save();
    Customer customerTwo = new Customer("Brian", "brian@email.com", "1111 Pearl Ave. Portland, OR 97209");
    customerTwo.save();
    assertEquals(customerOne, Customer.findName("Andrew"));
  }


}
