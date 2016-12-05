package MainFinder;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.net.*;

import org.reflections.*;
import org.reflections.scanners.*;
import org.reflections.util.*;

import static org.reflections.ReflectionUtils.*;

public class MainFinder {

	static String getMethodDescriptor(Method m)
	{
		return m.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		if (args.length < 2)
		{
			System.err.println("Not enough arguments given.");
			System.err.println("Usage: find-main <class-file>");
			System.exit(1);
		}
		
		// Create a file object on the directory containing the class
		File file = new File(args[1]);
		
		try {
			// Convert file to URL
			URI url = file.toURI();
			URL[] urls = new URL[]{ url.toURL() };
						
			Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(urls));
			
			Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
			
			Set<Method> possibleMains = new HashSet<Method>();
			
			for (Class<?> c : classes)
			{
				possibleMains.addAll(getAllMethods(c, 
						withReturnType(void.class),
						withParameters(String[].class),
						withName("main"),
						withModifier(Modifier.PUBLIC)));
			}
			
			if (possibleMains.isEmpty())
			{
				System.exit(1);
			}
			Method main = possibleMains.toArray(new Method[0])[0];
			
			System.out.println(main.toString());
			
		} catch (MalformedURLException e) {
			System.err.print("Unable to find given file");
			System.exit(2);
		} 
	}

}
