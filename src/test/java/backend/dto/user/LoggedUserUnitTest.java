package backend.dto.user;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backend.dto.LoggedUser;

public class LoggedUserUnitTest {
	
	private final String FIRST_NAME = " Mock ";
	private final String SURNAME = " Surname ";
	private final String EMPTY_STRING = "";
	private LoggedUser user;

	@Before
	public void setUp() throws Exception {
		user = new LoggedUser();
	}

	@After
	public void tearDown() throws Exception {
		user = null;
	}
	
	@Test
	public void givenUserFirstNameAndSurnameWhenExtractNameAndSurnameThenReturnStringArrayWithNameAt0IdxAndSurnameAt1Idx() {
		user.setUserFirstName(FIRST_NAME);
		user.setUserSurname(SURNAME);
		
		String[] result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(FIRST_NAME, is( equalTo(result[0])));
		assertThat(SURNAME, is( equalTo(result[1])));
	}
	
	@Test
	public void givenUserNameWhenExtractNameAndSurnameThenReturnEmptyFirstNameAndSurname() {
		user = new LoggedUser();
		user.setUserName(SURNAME);
		
		String[] result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(result[0], is( equalTo(EMPTY_STRING)));
		assertThat(result[1], is( equalTo(SURNAME)));
	}
	
	@Test
	public void givenUserFullNameWhenExtractNameAndSurnameThenReturnStringArrayWithNameAt0IdxAndSurnameAt1Idx() {
		final String firstName = FIRST_NAME.replace(" ", "");
		final String surname = SURNAME.replace(" ", "");
		user = new LoggedUser();
		user.setUserFullName(String.format("%s %s", firstName, surname));
		
		String[] result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(result[0], is( equalTo(firstName)));
		assertThat(result[1], is( equalTo(surname)));
	}
	
	@Test
	public void givenEmptyOrOnlySpacesUserFullNameWhenExtractNameAndSurnameThenReturnStringArrayWithEmptyNameAt0IdxAndNullSurnameAt1Idx() {
		user = new LoggedUser();
		user.setUserFullName("");
		
		String[] result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(result[0], is( equalTo(EMPTY_STRING) ));
		assertThat(result[1], is( nullValue() ));
		
		user.setUserFullName(" ");
		
		result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(result[0], is( equalTo(EMPTY_STRING) ));
		assertThat(result[1], is( nullValue() ));
	}
	
	@Test
	public void givenUserFullNameWithSpacesWhenExtractNameAndSurnameThenReturnStringArrayWithNameAt0IdxAndSurnameAt1Idx() {
		final String firstName = FIRST_NAME.replace(" ", "");
		final String surname = SURNAME.replace(" ", "");
		user = new LoggedUser();
		user.setUserFullName(String.format("%s %s", FIRST_NAME, SURNAME));
		
		String[] result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(result[0], is( equalTo(firstName+ " "+" ")));
		assertThat(result[1], is( equalTo(surname)));
	}
	
	@Test
	public void givenUserFullNameWithoutSpacesWhenExtractNameAndSurnameThenReturnStringArrayWithEmptyNameAt0IdxAndSurnameEqualFullNameAt1Idx() {
		final String surname = SURNAME.replace(" ", "");
		user = new LoggedUser();
		user.setUserFullName(SURNAME);
		
		String[] result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(result[0], is( equalTo(EMPTY_STRING)));
		assertThat(result[1], is( equalTo(surname)));
	}
	
	@Test
	public void givenEmptyLoggedUserWhenExtractNameAndSurnameThenReturnStringArrayWithEmptyNameAt0IdxAndNullSurnameAt1Idx() {
		user = new LoggedUser();
		
		String[] result = user.extractNameAndSurname();
		
		assertThat(result, is(notNullValue()));
		assertThat(result.length, is( equalTo(2) ));
		assertThat(result[0], is( equalTo(EMPTY_STRING) ));
		assertThat(result[1], is( nullValue() ));
	}
}
