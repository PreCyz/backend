package backend.dto;

public enum LoginMode {
	SOL {
		@Override
		public boolean isOnLoginLoadMobileMessages() {
			return false;
		}
	}, IVR, MOBILE_ANDROID_PIN, MOBILE_IOS_PIN, MOBILE_ANDROID_PATTERN, MOBILE_IOS_PATTERN, MOBILE;
	
	/**Jeśli true to wczytywane są wiadmości mobilne a nie SOL-owe. */
	public boolean isOnLoginLoadMobileMessages() {
		return true;
	}
}