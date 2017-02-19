package backend.helper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import backend.helper.BankAccountHelper;

public class BankAccountHelperUnitTest {

	private static final String EMPTY_STRING = "";
	
	@Test
	public void givenNullOrEmptyAccountWhenCreateAccountFormatThenReturnEmptyString() {
		assertThat(BankAccountHelper.createAccountFormat(null), is( equalTo(EMPTY_STRING) ));
		assertThat(BankAccountHelper.createAccountFormat(EMPTY_STRING), is( equalTo(EMPTY_STRING) ));
	}
	
	@Test
	public void givenAccountShorterThan26WhenCreateAccountFormatThenReturnEmptyString() {
		assertThat(BankAccountHelper.createAccountFormat(" "), is( equalTo(EMPTY_STRING) ));
		assertThat(BankAccountHelper.createAccountFormat("1"), is( equalTo(EMPTY_STRING) ));
		assertThat(BankAccountHelper.createAccountFormat("1234567890  1234567890"), is( equalTo(EMPTY_STRING) ));
	}
	
	@Test
	public void givenAccountNumberAs26SpacesWhenCreateAccountFormatThenReturnEmptyString() {
		final String spaces_26 = "                          ";
		assertThat(BankAccountHelper.createAccountFormat(spaces_26), is( equalTo(EMPTY_STRING) ));
	}
	
	@Test
	public void givenAccountNumberLength26WithSpacesWhenCreateAccountFormatThenReturnEmptyString() {
		String accountWithSpaces = "1234567890  12345678903456";
		assertThat(BankAccountHelper.createAccountFormat(accountWithSpaces), is( equalTo(EMPTY_STRING) ));
		
		accountWithSpaces = "123456789012345678903456  ";
		assertThat(BankAccountHelper.createAccountFormat(accountWithSpaces), is( equalTo(EMPTY_STRING) ));
		
		accountWithSpaces = "  123456789012345678903456";
		assertThat(BankAccountHelper.createAccountFormat(accountWithSpaces), is( equalTo(EMPTY_STRING) ));
	}
	
	@Test
	public void givenAccountNumberLength26WithWhenCreateAccountFormatThenReturnAccountFormat() {
		String accountNumber = "12345678901234567890123456";
		String expectedResult = "12 3456 7890 1234 5678 9012 3456";
		assertThat(BankAccountHelper.createAccountFormat(accountNumber), is( equalTo(expectedResult) ));
	}
}
