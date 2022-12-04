package com.fourJava;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

//@QuarkusTest
public class GreetingResourceTest {

//	@Test
	public void reviewAnnotationApplicationScopedInServices() throws ClassNotFoundException {
		getClassesForPackage(PACKAGE_INFRAESTRUCTURE).forEach(entity -> {
			if (entity.getName().contains("Service") && !entity.getName().contains("$1")) {
				Annotation annotation = entity.getAnnotation(CLASS_APPLICATION_SCOPED);
				if (annotation == null) {
					LOG.error("La clase " + entity.getName());
					LOG.error("No posee [" + CLASS_APPLICATION_SCOPED.toString() + "]");
					LOG.error("Agregela y tome de ejemplo de como estan en otros services");
				}
				assertNotNull(annotation);
			}
		});
	}

	// Test Arq RESTful
//	@Test
	public void reviewNotVerbInPathByRest() throws ClassNotFoundException {
		getClassesForPackage(PACKAGE_REST).forEach(entity -> {
			validateVerbInPath(entity);
		});
	}

//	@Test
	public void reviewNotPrepositionInPathByRest() throws ClassNotFoundException {
		getClassesForPackage(PACKAGE_REST).forEach(entity -> {
			validatePrepositionInPath(entity);
		});
	}

//	@Test
	public void reviewReturnValidTypeData() throws ClassNotFoundException {
		getClassesForPackage(PACKAGE_REST).forEach(entity -> {
			validateTypeData(entity);
		});
	}

	private void validateTypeData(Class entity) {
		for (Method declaredMethod : entity.getDeclaredMethods()) {
			List<String> packageValids = List.of("javax.ws.rs.core", PACKAGE_REST + ".dtos");
			if (packageValids.contains(declaredMethod.getReturnType().getPackageName())) {
			} else {
				LOG.error("-------------------------------------------------------------");
				LOG.error("La entidad [" + entity + "]");
				LOG.error("Contiene un metodo denominado [" + declaredMethod.getName() + "]");
				LOG.error("Que retorna un tipo de dato no valido: " + declaredMethod.getReturnType());
				LOG.error("Evite usar tipos de datos basicos y emplee JSON");
				assertEquals("javax.ws.rs.core", declaredMethod.getReturnType().getPackageName());
				assertEquals(PACKAGE_REST + ".dtos", declaredMethod.getReturnType().getPackageName());
			}
		}
	}

	private void validatePrepositionInPath(Class entity) {
		for (Method declaredMethod : entity.getDeclaredMethods()) {
			Annotation annotation = declaredMethod.getDeclaredAnnotation(Path.class);
			if (annotation != null) // ej @Post, no tiene @Path
				try {
					String[] prepositions = { "by", "for", "to", "at", "of", "from", "with", "in", "on", "at", "to",
							"into" };
					Method declared = annotation.getClass().getDeclaredMethod("value", null);
					Object paths = declared.invoke(annotation, new Object[0]);
					for (String pathComplete : valueOf(paths).split("/"))
						for (String pathSplit : pathComplete.split("(?=\\p{Upper})"))
							for (String verb : prepositions) {
								if (verb.equalsIgnoreCase(pathSplit)) {
									LOG.error("La clase " + entity.getName());
									LOG.error("Tiene un metodo [" + declaredMethod.getName() + "]");
									LOG.error("Que tiene como path [" + pathComplete + "] y en concreto [" + pathSplit
											+ "]");
									LOG.error(
											"Esto viola la condicion de RESTful, elimine prepociciones y deje solo sustantivos/entidades");
								}
								assertFalse(verb.equalsIgnoreCase(pathSplit));
							}
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
		}

	}

	private void validateVerbInPath(Class entity) {
		for (Method declaredMethod : entity.getDeclaredMethods()) {
			Annotation annotation = declaredMethod.getDeclaredAnnotation(Path.class);
			if (annotation != null) // ej @Post, no tiene @Path
				try {
					String[] verbs = { "find", "found", "search" };
					Method declared = annotation.getClass().getDeclaredMethod("value", null);
					Object paths = declared.invoke(annotation, new Object[0]);
					for (String path : valueOf(paths).split("/"))
						for (String verb : verbs) {
							if (verb.equalsIgnoreCase(path)) {
								LOG.error("La clase " + entity.getName());
								LOG.error("Tiene un metodo [" + declaredMethod.getName() + "]");
								LOG.error("Que tiene como path [" + paths + "]");
								LOG.error(
										"Esto viola la condicion de RESTful, elimine verbos y deje solo sustantivos/entidades");
							}
							assertFalse(verb.equalsIgnoreCase(path));
						}
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
		}
	}

	private void validateMappedByInFields(Class entity, Class... annotations) {
		Field[] fields = entity.getDeclaredFields();
		try {
			for (Class annotation : annotations)
				for (Field field : fields) {
					Annotation annotationActual = field.getDeclaredAnnotation(annotation);
					if (annotationActual != null) {
						Method method = annotationActual.getClass().getDeclaredMethod("mappedBy", null);
						Object invoke = method.invoke(annotationActual, new Object[0]);
						boolean isBlank = valueOf(invoke).isBlank();
						if (!isBlank)
							LOG.error("El campo [" + field.getName() + "], de la clase " + entity.getName()
									+ " viola la condicion de Clean Architecture");
						assertTrue(isBlank);
					}
				}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static List<Class> getClassesForPackage(String packageName) throws ClassNotFoundException {
		List<File> directories = new ArrayList<File>();
		try {
			ClassLoader cLoad = Thread.currentThread().getContextClassLoader();
			if (cLoad == null)
				throw new ClassNotFoundException("Can't get class loader.");
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = cLoad.getResources(path);
			while (resources.hasMoreElements())
				directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(
					packageName + " does not appear to be a valid package (Null pointer exception)");
		} catch (UnsupportedEncodingException encex) {
			throw new ClassNotFoundException(
					packageName + " does not appear to be a valid package (Unsupported encoding)");
		} catch (IOException ioex) {
			throw new ClassNotFoundException(
					"IOException was thrown when trying to get all resources for " + packageName);
		}

		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : directories)
			if (directory.exists()) {
				String[] files = directory.list();
				for (String file : files)
					if (file.endsWith(".class") && !file.contains("Test"))
						classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6)));
			} else {
				String pakage = packageName + " (" + directory.getPath() + ")";
				throw new ClassNotFoundException(pakage + "does not appear to be a valid package");
			}
		return classes;
	}

	private static final Class<ApplicationScoped> CLASS_APPLICATION_SCOPED = ApplicationScoped.class;

	// Not Requerid

	private static final String PACKAGE = "com.fourJava";
	private static final String PACKAGE_REST = PACKAGE + ".rest";
	private static final String PACKAGE_DOMAIN = PACKAGE + ".domain";
	private static final String PACKAGE_INFRAESTRUCTURE = PACKAGE + ".infraestructure";

	@Inject
	Logger LOG;
}