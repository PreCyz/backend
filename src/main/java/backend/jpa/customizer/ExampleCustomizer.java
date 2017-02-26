package backend.jpa.customizer;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.Expression;
import org.eclipse.persistence.expressions.ExpressionBuilder;

public class ExampleCustomizer implements DescriptorCustomizer {

	private final String COLUMN_NAME = "MESSAGE";
	
	@Override
	public void customize(ClassDescriptor classDescriptor) throws Exception {
		ExpressionBuilder account = new ExpressionBuilder();
		Expression expression = account.getField(COLUMN_NAME).not().containsSubstring("some value");
		classDescriptor.getInheritancePolicy().setOnlyInstancesExpression(expression);
	}

}
