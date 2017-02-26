package backend.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


//@Entity
@Table(name="\"GROUPS\"")
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="\"GROUPS\"_ID_GENERATOR", sequenceName="HIBERNATE_SEQUENCE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="\"GROUPS\"_ID_GENERATOR")
	private long id;

	private String description;

	private String name;

	//bi-directional many-to-many association to CompanyRight
	@ManyToMany
	@JoinTable(name="COMPANY_GROUP", joinColumns={@JoinColumn(name="GROUP_ID")}, inverseJoinColumns={@JoinColumn(name="COMPANY_ID")})
	private List<CompanyRight> companyRights;

	//bi-directional many-to-one association to GroupRole
	@JoinTable(name="GROUP_ROLE", joinColumns={@JoinColumn(name="GROUP_ID")}, inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
	private List<GroupRole> groupRoles;

	public Group() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CompanyRight> getCompanyRights() {
		return this.companyRights;
	}

	public void setCompanyRights(List<CompanyRight> companyRights) {
		this.companyRights = companyRights;
	}

	public List<GroupRole> getGroupRoles() {
		return this.groupRoles;
	}

	public void setGroupRoles(List<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}

	public GroupRole addGroupRole(GroupRole groupRole) {
		getGroupRoles().add(groupRole);
		groupRole.setGroup(this);

		return groupRole;
	}

	public GroupRole removeGroupRole(GroupRole groupRole) {
		getGroupRoles().remove(groupRole);
		groupRole.setGroup(null);

		return groupRole;
	}

}