package backend.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import backend.exception.DAOException;

@XmlRootElement(name="conditions")
public class MarketingMessageConditions implements Serializable {

	private static final long serialVersionUID = 6226022532308700082L;
	
	private boolean showForAll;
	private List<String> modules;
	
	private List<String> brokerIds;
	private List<String> excBrokerIds;
	
	private String molRolesMode;//A-all,R-restrictTo(allow),[D-deny](unused)
	private List<String> molRoles;
	private String molProductsMode;//A-all,R-restrictTo(allow),D-deny,ExtR-restrictTo(allow) extended,ExtD-deny extended,ExtReg-regular with deny 
	private List<String> molProductNames;
	private List<String> molProducts;
	private List<String> molBrokerIds;
	private List<String> molExcBrokerIds;
	
	private Date molContractMinExpirationDate; 
	
	private String sexRestriction = "all"; //female - tylko dla kobiet, male - tylko dla mezczyzn, all - dla obu plci
	
	public boolean isShowForAll() {
		return showForAll;
	}
	public void setShowForAll(boolean showForAll) {
		this.showForAll = showForAll;
	}
	@XmlElementWrapper(name="modules")
	@XmlElement(name="module")
	public List<String> getModules() {
		return modules;
	}
	public void setModules(List<String> modules) {
		this.modules = modules;
	}
	
	
	@XmlElementWrapper(name="brokerIds")
	@XmlElement(name="id")
	public List<String> getBrokerIds() {
		return brokerIds;
	}
	public void setBrokerIds(List<String> brokerId) {
		this.brokerIds = brokerId;
	}
	@XmlTransient
	public String getBrokerIdsString() {
		return getStringFromList(brokerIds);
	}
	public void setBrokerIdsString(String ids) {
		brokerIds = getListFromString(ids);
	}
	@XmlElementWrapper(name="excludeBrokerIds")
	@XmlElement(name="id")
	public List<String> getExcBrokerIds() {
		return excBrokerIds;
	}
	public void setExcBrokerIds(List<String> excBrokerId) {
		this.excBrokerIds = excBrokerId;
	}
	@XmlTransient
	public String getExcBrokerIdsString() {
		return getStringFromList(excBrokerIds);
	}
	public void setExcBrokerIdsString(String ids) {
		excBrokerIds = getListFromString(ids);
	}
	
	
	@XmlElement(name="multiportfelRolesMode")
	public String getMolRolesMode() {
		return molRolesMode;
	}
	public void setMolRolesMode(String mulRolesMode) {
		this.molRolesMode = mulRolesMode;
	}
	@XmlElementWrapper(name="multiportfelRoles")
	@XmlElement(name="role")
	public List<String> getMolRoles() {
		return molRoles;
	}
	public void setMolRoles(List<String> mulRoles) {
		this.molRoles = mulRoles;
	}
	@XmlElement(name="multiportfelProductsMode")
	public String getMolProductsMode() {
		return molProductsMode;
	}
	public void setMolProductsMode(String mulProductsMode) {
		this.molProductsMode = mulProductsMode;
	}
	@XmlElementWrapper(name="multiportfelProducts")
	@XmlElement(name="role")
	public List<String> getMolProductNames() {
		return molProductNames;
	}
	public void setMolProductNames(List<String> mulProducts) {
		this.molProductNames = mulProducts;
	}
	@XmlElementWrapper(name="multiportfelProductsExt")
	@XmlElement(name="prod")
	public List<String> getMolProducts() {
		return molProducts;
	}
	public void setMolProducts(List<String> molProducts) {
		this.molProducts = molProducts;
	}
	
	@XmlElementWrapper(name="multiportfelBrokerIds")
	@XmlElement(name="id")
	public List<String> getMolBrokerIds() {
		return molBrokerIds;
	}
	public void setMolBrokerIds(List<String> molBrokerId) {
		this.molBrokerIds = molBrokerId;
	}
	@XmlTransient
	public String getMolBrokerIdsString() {
		return getStringFromList(molBrokerIds);
	}
	public void setMolBrokerIdsString(String ids) {
		molBrokerIds = getListFromString(ids);
	}
	@XmlElementWrapper(name="multiportfelExcludeBrokerIds")
	@XmlElement(name="id")
	public List<String> getMolExcBrokerIds() {
		return molExcBrokerIds;
	}
	public void setMolExcBrokerIds(List<String> molExcBrokerId) {
		this.molExcBrokerIds = molExcBrokerId;
	}
	@XmlTransient
	public String getMolExcBrokerIdsString() {
		return getStringFromList(molExcBrokerIds);
	}
	public void setMolExcBrokerIdsString(String ids) {
		molExcBrokerIds = getListFromString(ids);
	}
	
	public Date getMolContractMinExpirationDate() {
		return molContractMinExpirationDate;
	}
	public void setMolContractMinExpirationDate(Date molContractExpirationDate) {
		this.molContractMinExpirationDate = molContractExpirationDate;
	}
	
	public String getSexRestriction() {
		return sexRestriction;
	}
	public void setSexRestriction(String sexRestriction) {
		this.sexRestriction = sexRestriction;
	}
	public boolean isVisibleForBothSexes(){
		return "all".equalsIgnoreCase(sexRestriction);
	}

	private static final JAXBContext context;
	static {
		try {
			context = JAXBContext.newInstance(MarketingMessageConditions.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getMarshal(MarketingMessageConditions conditions) {
		try {
			if(conditions == null) {
				return "";
			}
			Marshaller marshaller = context.createMarshaller();
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			
			marshaller.marshal(conditions, out);
			return out.toString("UTF-8");
		} catch (Exception e) {
			throw new DAOException("Error in MarketingMessageConditions.getMarshal()", e);
		}
	}
	public static MarketingMessageConditions getUnmarshal(String conditions) {
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			MarketingMessageConditions object = (MarketingMessageConditions) unmarshaller.unmarshal(new ByteArrayInputStream(conditions.getBytes("UTF-8")));
			return object;
		} catch (Exception e) {
			throw new DAOException("Error in MarketingMessageConditions.getUnmarshal()", e);
		}
	}
	
	private static String getStringFromList(List<String> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for(String id : list) {
			buffer.append(";").append(id);
		}
		return buffer.substring(1);
	}
	private static List<String> getListFromString(String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return null;
		}
		String[] split = ids.trim().split(";");
		List<String> items = new ArrayList<String>();
		for(String item : split) {
			items.add(item.trim());
		}
		return items;
	}
	
	//sprawdzenie aby w aktualnosciach pojawila sie wiadomosc (dla wszystkich kontraktow)
	public boolean check(LoggedUser user, List<String> restrictToContracts, String restrainMode, String eventsIds) {
		return false;
	}
	
	public boolean checkForBrokerContract(String productName) {
		if (showForAll) {
			return true;
		}
		
		if (modules.contains("Multiportfel") && molProductNames.contains(productName)) {
			return true;
		}
		
		return false;
	}
}
