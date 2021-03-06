package backend.itest.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

public abstract class AbstractIT {
	
	private static TimeUnit timeUnit = TimeUnit.SECONDS;
	
	protected abstract void setup() throws Exception;
	protected abstract void executeTests() throws Exception;
	protected abstract void cleanup();
	
	public double run() throws Exception {
		setup();
		
		long start = getSystemNanoTime();
		executeTests();
		long end = getSystemNanoTime();
		double duration = calculateTestDuration(start, end);
		displayTestDuration(duration);
		
		cleanup();
		return duration;
	}
	
	public static void assertion(String msg, boolean condition) {
		if(condition) {
			System.out.printf("%s%n", msg);
		} else {
			System.err.printf("%s%n", msg);
		}
	}
	
	public static long getSystemNanoTime() {
		return System.nanoTime();
	}
	
	public void displayTestDuration(double duration) {
		System.out.printf("%s - test duration: %f %s%n", getClass().getSimpleName(), duration, timeUnit);
	}
	
	public double calculateTestDuration(long start, long end) {
		final long nanoValue = 1000000000;
		final long secondsInHour = 3600;
		final long secondsInMinute = 60;
		final int scale = 3;
		
		BigDecimal duration = new BigDecimal(end - start);
		BigDecimal result = duration.divide(new BigDecimal(nanoValue), scale, RoundingMode.HALF_UP);
		timeUnit = TimeUnit.SECONDS;
		
		if (result.doubleValue() >= secondsInMinute && result.doubleValue() < secondsInHour) {
			result = duration.divide(new BigDecimal(secondsInMinute), scale, RoundingMode.HALF_UP);
			timeUnit = TimeUnit.MINUTES;
		} else if (result.doubleValue() >= secondsInHour) {
			result = duration.divide(new BigDecimal(secondsInHour), scale, RoundingMode.HALF_UP);
			timeUnit = TimeUnit.HOURS;
		}
		return result.doubleValue();
	}
	
	protected void displayTestName() {
		String testName = retriveTestName();
		System.out.println(testName);
	}

	private String retriveTestName() {
		final int INDEX_OF_PROPER_STACK = 3;
		StackTraceElement element = Thread.currentThread().getStackTrace()[INDEX_OF_PROPER_STACK];
		String fileName = element.getFileName();
		String className = fileName.substring(0, fileName.indexOf("."));
		String methodName = element.getMethodName();
		String testName = String.format("%s.%s ...", className, methodName);
		return testName;
	}
}
