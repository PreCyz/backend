package backend.util;

public class LoggerHelper {
	
	public static void printStackTrace() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for(StackTraceElement element : elements) {
			String fileName = element.getFileName();
			if(fileName.indexOf(".") > 0) {
				String className = fileName.substring(0, fileName.indexOf("."));
				String methodName = element.getMethodName();
				System.out.printf("%s.%s ... \n", className, methodName);
			} else {
				System.out.printf("%s ... \n", fileName);
			}
		}
	}

}
