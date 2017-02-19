package backend.exception;

import org.springframework.dao.DataAccessException;

/**
 * Klasa reprezentujaca wyjatki wyrzucane z warstwy danych.
 * Wszystkie wyjatki jakie pojawiaja sie w warstwie danych sa przerzucane na wyjatek typu
 * DAOException.
 */
public class DAOException extends DataAccessException {

	String messageKey;
    String[] messageParams;
    String addUllaResult;
    boolean notLog; 
    
    
    private static final long serialVersionUID = -5966684352181072896L;

    /**
     * Konstrutkor klasy wykozystujacy nielokalizowany komunikat do wypisania
     *
     * @param string Nie lokalizowany opis wyjatku.
     */
    public DAOException(String string) {
        super(string);
    }

    /**
     * Konstrutkor klasy
     *
     * @param string Nie lokalizowany opis wyjatku
     * @param messageKey Klucz lokalizowanego komunikatu 
     */
    public DAOException(String string, String messageKey) {
        super(string);
        this.messageKey = messageKey;
    }
    
    
    /**
     * Konstrutkor klasy
     *
     * @param string Nie lokalizowany opis wyjatku
     * @param messageKey Klucz lokalizowanego komunikatu 
     */
    public DAOException(String string, String messageKey, String addUllaResult) {
    	super(string);
    	this.messageKey = messageKey;
    	this.addUllaResult = addUllaResult;
    }

    /**
     * Konstrutkor klasy
     *
     * @param string Nie lokalizowany opis wyjatku
     * @param messageKey klucz lokalizowanego komunikatu 
     * @param messageParams dodatkowe parametry wstawiane w odpowiednie miejsca w komunikacie przedstawianym uzytkownikowi
     */
    public DAOException(String string, String messageKey, String messageParams[]) {
        super(string);
        this.messageKey = messageKey;
        this.messageParams = messageParams;
    }

    /**
     * Konstrutkor klasy
     *
     * @param string    Nie lokalizowany opis wyjatku
     * @param throwable Przyczyna zrodlo wyjatku
     */
    public DAOException(String string, Throwable throwable) {
        super(string, throwable);
    }

    /**
     * Konstrutkor klasy
     *
     * @param string    Nie lokalizowany opis wyjatku
     * @param throwable Przyczyna zrodlo wyjatku
     * @param messageKey klucz lokalizowanego komunikatu 
     * @param messageParams dodatkowe parametry wstawiane w odpowiednie miejsca w komunikacie przedstawianym uzytkownikowi
     */
    public DAOException(String string, Throwable throwable, String messageKey) {
        super(string, throwable);
        this.messageKey = messageKey;
    }
    
    /**
     * Konstrutkor klasy
     *
     * @param string    Nie lokalizowany opis wyjatku
     * @param throwable Przyczyna zrodlo wyjatku
     * @param messageKey klucz lokalizowanego komunikatu 
     * @param messageParams dodatkowe parametry wstawiane w odpowiednie miejsca w komunikacie przedstawianym uzytkownikowi
     */
    public DAOException(String string, Throwable throwable, String messageKey, String messageParams[]) {
        super(string, throwable);
        this.messageKey = messageKey;
        this.messageParams = messageParams;
    }
    
    public DAOException setNotLog() {
		this.notLog = true;
		return this;
	}
    
    public boolean isNotLog() {
		return notLog;
	}
    
    public String getMessageKey() {
		return messageKey;
	}
    
    public String[] getMessageParams() {
		return messageParams;
	}
    
    public String getAddUllaResult() {
    	if (addUllaResult == null && getCause() != null && getCause() instanceof DAOException) {
    		return ((DAOException)getCause()).getAddUllaResult();
    	}
		return addUllaResult;
	}
    public String getDeepMessageKey() {
    	if (messageKey == null && getCause() != null && getCause() instanceof DAOException) {
    		return ((DAOException)getCause()).getDeepMessageKey();
    	}
    	return messageKey;
    }
}
