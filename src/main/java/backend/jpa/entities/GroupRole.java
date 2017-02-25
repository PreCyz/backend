package backend.jpa.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//@Entity
@Table(name="GROUP_ROLE")
@NamedQuery(name="GroupRole.findAll", query="SELECT g FROM GroupRole g")
public class GroupRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GROUP_ID")
	private java.math.BigDecimal groupId;

	@Column(name="ROLE_ID")
	private java.math.BigDecimal roleId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="GROUP_ID", insertable=false, updatable=false)
	private Group group;

	public GroupRole() {
	}

	public java.math.BigDecimal getGroupId() {
		return this.groupId;
	}

	public void setGroupId(java.math.BigDecimal groupId) {
		this.groupId = groupId;
	}

	public java.math.BigDecimal getRoleId() {
		return this.roleId;
	}

	public void setRoleId(java.math.BigDecimal roleId) {
		this.roleId = roleId;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}