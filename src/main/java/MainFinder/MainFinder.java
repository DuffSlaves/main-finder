package MainFinder;

import java.io.*;
import java.util.*;
import java.net.*;

import org.apache.bcel.classfile.*;

public class MainFinder {
	public static void main(String[] args) {
		if (args.length < 2)
		{
			System.err.println("Not enough arguments given.");
			System.err.println("Usage: find-main <command> <class-path>");
			System.exit(1);
		}

		// Create a file object on the directory containing the class

		try
		{
			JavaClass jclass = new ClassParser(args[1]).parse();

			if (args[0].equals("p"))
			{
				System.out.println(jclass.getPackageName().replaceAll("\\.", "//"));
			}
			else if (args[0].equals("m"))
			{
				for (Method method : jclass.getMethods())
				{
					System.out.print(method.getName());
					System.out.println(method.getSignature());
				}				
			}
			else
			{
				System.out.println("Class: " + jclass.getClassName());
				System.out.println(jclass.getPackageName());
				System.out.println("  Methods:");
				for (Method method : jclass.getMethods())
				{
					System.out.println(method.getName());
					System.out.println(method.getSignature());
				}
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