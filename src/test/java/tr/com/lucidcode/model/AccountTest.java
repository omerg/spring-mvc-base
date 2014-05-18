package tr.com.lucidcode.model;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import tr.com.lucidcode.model.Account;

public class AccountTest {
	
	private static final String INCORRECT_PHONE_NUMBER = "111-222-33-3";
	private static final String CORRECT_PHONE_NUMBER = "111-222-33-34";
	private static final String USERNAME = "TEST_USER";
	
	private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void usernameIsNull() {
        Account account = new Account(null, null, null, CORRECT_PHONE_NUMBER);

        Set<ConstraintViolation<Account>> constraintViolations = 
            validator.validate(account);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
    }
    
    @Test
    public void phoneNumberIsNull() {
        Account account = new Account(USERNAME, null, null, null);

        Set<ConstraintViolation<Account>> constraintViolations = 
            validator.validate(account);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
    }
    
    @Test
    public void phoneNumberInvalidLength() {
        Account account = new Account(USERNAME, null, null, INCORRECT_PHONE_NUMBER);

        Set<ConstraintViolation<Account>> constraintViolations = 
            validator.validate(account);

        assertEquals(1, constraintViolations.size());
        assertEquals("must match \"(^$|[0-9]{3}-[0-9]{3}-[0-9]{2}-[0-9]{2})\"", constraintViolations.iterator().next().getMessage());
    }


    @Test
    public void accountIsValid() {
        Account account = new Account(USERNAME, null, null, CORRECT_PHONE_NUMBER);

        Set<ConstraintViolation<Account>> constraintViolations =
            validator.validate(account);

        assertEquals(0, constraintViolations.size());
    }

}
