package dynamic;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.UUID;

public class Magic {

	public static <T> T getImplementation(Class<T> clazz) {
		assert clazz.isInterface();

		String code = createSourceCode(clazz);
		System.out.println(code);

		return null;
	}

	private static <T> String createSourceCode(Class<T> clazz) {
		StringBuffer buffer = new StringBuffer();

		printClassName(buffer, clazz.getSimpleName(), clazz.getName());
		printClassBody(buffer, clazz.getDeclaredMethods());

		return buffer.toString();
	}

	private static void printClassName(StringBuffer buffer, String interfaceName, String fullInterfaceName) {
		buffer.append("public final class ")
				.append(interfaceName + "Impl" + UUID.randomUUID().toString().replace("-", "")).append(" implements ")
				.append(fullInterfaceName);

	}

	private static void printClassBody(StringBuffer buffer, Method[] methods) {
		buffer.append("{\n");
		for (Method method : methods) {
			buffer.append("\t@Override\n");
			buffer.append("\tpublic ").append(method.getReturnType().getCanonicalName()).append(' ')
					.append(method.getName()).append("(");
			int counter = 0;
			for (Type type : method.getGenericParameterTypes()) {
				buffer.append(type.getTypeName()).append(" arg" + counter++);
			}

			buffer.append(")");
			printMethodBody(buffer, method);
		}
		buffer.append("}");
	}

	private static void printMethodBody(StringBuffer buffer, Method method) {
		buffer.append("{\n");
		buffer.append("\t\tSystem.out.println(\"" + method.getName() + "\");\n");
		buffer.append("\t}\n");

	}

}
