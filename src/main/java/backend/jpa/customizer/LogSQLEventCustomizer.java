package backend.jpa.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;

import backend.jpa.entity.log.LogSQLEvent;

public class LogSQLEventCustomizer implements DescriptorCustomizer {

	private final String COLUMN_NAME = "MESSAGE";
	
	@Override
	public void customize(ClassDescriptor classDescriptor) throws Exception {
		ExpressionBuilder account = new ExpressionBuilder();
		Expression expression = account.getField(COLUMN_NAME).containsSubstring(LogSQLEvent.class.getSimpleName());
		classDescriptor.getInheritancePolicy().setOnlyInstancesExpression(expression);
	}
}
