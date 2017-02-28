package backend.dto;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import backend.jpa.Entry;

@JsonInclude(value=Include.NON_EMPTY)
@Entity
@Table(name = "LOGGED_USER", schema = "springDB")
@TableGenerator(name = "loggedUserGenerator", initialValue = 0, schema = "springDB", allocationSize = 1)
@Access(AccessType.FIELD)
public class LoggedUser implements Entry, Serializable {
	
	private static final long serialVersionUID = 3830692857572798871L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "loggedUserGenerator")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FIRST_NAME", length = 20, nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", length = 20, nullable = false)
	private String lastName;
	
	@Column(name = "EMAIL", length = 20, nullable = false, unique = true)
	private String email;
	
	@Column(name = "PASSWORD", length = 500, nullable = false)
	private String password;
	
	@Column(name = "USER_LOGIN", length = 20, nullable = false, unique = true)
	private String userLogin;
	
	@Transient
	@JsonIgnore
	@XmlTransient
	private String sessionId;
	
	public LoggedUser() {
		//for JPA
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public boolean isNullUser() {
		return false;
	}

	@Override
	public void update(Entry entry) {
		LoggedUser loggedUser = (LoggedUser) entry;
		this.password = loggedUser.getPassword();
		this.userLogin = loggedUser.getUserLogin();
		this.firstName = loggedUser.getFirstName();
		this.lastName = loggedUser.getLastName();
		this.email = loggedUser.getEmail();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((userLogin == null) ? 0 : userLogin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoggedUser other = (LoggedUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (userLogin == null) {
			if (other.userLogin != null)
				return false;
		} else if (!userLogin.equals(other.userLogin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LoggedUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", userLogin=" + userLogin + ", sessionId=" + sessionId + "]";
	}
	
}
