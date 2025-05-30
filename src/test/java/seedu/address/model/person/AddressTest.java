package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {

        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));


        assertFalse(Address.isValidAddress(""));
        assertFalse(Address.isValidAddress(" "));


        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-"));
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");


        assertTrue(address.equals(new Address("Valid Address")));


        assertTrue(address.equals(address));


        assertFalse(address.equals(null));


        assertFalse(address.equals(5.0f));


        assertFalse(address.equals(new Address("Other Valid Address")));
    }
}
