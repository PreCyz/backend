package backend.jpa.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeName;

import backend.jpa.Entry;
import backend.jpa.SequenceEntry;

@JsonRootName(value="boxMessage")
//@Entity
@Table(name="BOX_MESSAGE")
@NamedQueries({
	@NamedQuery(name="BoxMessage.findAll", query="SELECT b FROM BoxMessage b"),
	@NamedQuery(name="BoxMessage.findAllOrdered", query="SELECT b FROM BoxMessage b ORDER BY b.createDate DESC"),
	@NamedQuery(name="BoxMessage.findByContractNumberOrdered", query="SELECT b FROM BoxMessage b WHERE b.contractNumber = :contractNumber ORDER BY b.createDate DESC"),
	@NamedQuery(name="BoxMessage.findByContractNumber", query="SELECT b FROM BoxMessage b WHERE b.contractNumber = :contractNumber")
})
@Access(AccessType.FIELD)
@JsonTypeName("boxMessage")
public class BoxMessage extends SequenceEntry {

	private static final long serialVersionUID = -1768687556516601247L;

	@Column(name="CASE_ID")
	private String caseId;
	
	@Column(name="CONTRACTNO")
	private String contractNumber;
	
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="MAJOR_TYPE")
	private String majorType;
	
	@Column(name="MESS_TYPE")
	private String type;

	@Column(name="CLIENT_CODE")
	private String clientCode;
	
	@Column(name="CLIENT_NAME")
	private String clientName;
	
	@Column(name="EVENT_NUMBER")
	private String eventNumber;
	
	@Column(name="TRANSACTION_NUMBER")
	private String transactionNumber;
	
	@Column(name="DOCUMENT_ID")
	private String documentId;
	
	//bi-directional one-to-one association to BoxMessageResponse
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DOCUMENT_ID", referencedColumnName="DOCUMENT_ID", insertable=false, updatable=false)
	private BoxMessageResponse boxMessageResponse;
	
	@Transient
	private String connectionId;
	@Transient
	private boolean connectionInitiator = true;
	@Transient
	private List<BoxMessage> group;
	@Transient
	private int groupUnreadCount;
	
	@Transient
	private boolean readed;
	@Transient
	private Boolean readedBySecondary;
	
	@Transient
	private boolean detailsApplied = false;

	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getMajorType() {
		return majorType;
	}
	public void setMajorType(String majorType) {
		this.majorType = majorType;
		if (caseId == null) {
			this.caseId = majorType + "_" + UUID.randomUUID().toString().replaceAll("-", "");
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public String getEventNumber() {
		return eventNumber;
	}
	public void setEventNumber(String eventNumber) {
		this.eventNumber = eventNumber;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getConnectionId() {
		if (connectionId == null) {
			return caseId;
		}
		return connectionId;
	}
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	public boolean isConnectionInitiator() {
		return connectionInitiator;
	}
	public void setConnectionInitiator(boolean connectionInitiator) {
		this.connectionInitiator = connectionInitiator;
	}
	public List<BoxMessage> getGroup() {
		return group;
	}
	public void setGroup(List<BoxMessage> group) {
		this.group = group;
	}
	public int getGroupUnreadCount() {
		return groupUnreadCount;
	}
	public void recalculateUnreaded() {
		int count = 0;
		if (!readed) {
			++count;
		}
		if (this.group != null) {
			for(BoxMessage message : this.group) {
				if (!message.isReaded()) {
					++count;
				}
			}
		}
		groupUnreadCount = count;
	}

	public boolean isReaded() {
		return readed;
	}
	public void setReaded(boolean readed) {
		this.readed = readed;
	}
	public Boolean getReadedBySecondary() {
		return readedBySecondary;
	}
	public void setReadedBySecondary(Boolean readedBySecondary) {
		this.readedBySecondary = readedBySecondary;
	}
	
	public boolean isDetailsApplied() {
		return detailsApplied;
	}
	public void setDetailsApplied() {
		this.detailsApplied = true;
	}
	public BoxMessageResponse getBoxMessageResponse() {
		return boxMessageResponse;
	}
	public void setBoxMessageResponse(BoxMessageResponse boxMessageResponse) {
		this.boxMessageResponse = boxMessageResponse;
	}

	@Override
	public String toString() {
		return "BoxMessage [caseId=" + caseId + ", contractNumber="
				+ contractNumber + ", createDate=" + createDate
				+ ", majorType=" + majorType + ", type=" + type
				+ ", clientCode=" + clientCode + ", clientName=" + clientName
				+ ", eventNumber=" + eventNumber + ", transactionNumber="
				+ transactionNumber + ", documentId=" + documentId 
				+ ", connectionId=" + connectionId
				+ ", connectionInitiator=" + connectionInitiator + ", group="
				+ group + ", groupUnreadCount=" + groupUnreadCount
				+ ", readed=" + readed + ", readedBySecondary="
				+ readedBySecondary + ", detailsApplied=" + detailsApplied
				+ "]";
	}
	@Override
	public void update(SequenceEntry  entry) {
		super.update(entry);
		BoxMessage newEntry = (BoxMessage) entry;
		this.caseId = newEntry.getCaseId();
		this.contractNumber = newEntry.getContractNumber();
		this.createDate = newEntry.getCreateDate();
		this.majorType = newEntry.getMajorType();
		this.type = newEntry.getType();
		this.clientCode = newEntry.getClientCode();
		this.clientName = newEntry.getClientName();
		this.eventNumber = newEntry.getEventNumber();
		this.transactionNumber = newEntry.getTransactionNumber();
		this.documentId = newEntry.getDocumentId();
	}
	@Override
	public void update(Entry entry) {
		// TODO Auto-generated method stub
		
	}
	
}
