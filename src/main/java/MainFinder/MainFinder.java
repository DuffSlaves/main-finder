package MainFinder;

import java.io.*;
import java.util.*;
import java.net.*;

import org.apache.bcel.classfile.*;

public class MainFinder {

	
	static String getMethodDescriptor(Method m)
	{
		return m.toString();
	}
		
	public static void main(String[] args) {
		if (args.length < 1)
		{
			System.err.println("Not enough arguments given.");
			System.err.println("Usage: find-main <class-path>");
			System.exit(1);
		}
		
		// Create a file object on the directory containing the class
		
		try
		{
			JavaClass jclass = new ClassParser(args[0]).parse();
			
			System.out.println("Class: " + jclass.getClassName());
			System.out.println("  Methods:");
			for (Method method : jclass.getMethods())
			{
				System.out.println("    " + method);
				
				System.out.println(method.getSignature());
			}
		}
		catch (IOException e)
		{
			System.err.println("The class file did not exist.");
			System.exit(2);
		}
		catch (ClassFormatException e)
		{
			System.err.println("The class file was invalid.");
			System.exit(3);
		}
		
	}

}