package MainFinder;

import java.io.*;
import java.util.*;
import java.net.*;

import org.apache.bcel.classfile.*;

public class MainFinder {

	public static void printHelp() {
		System.err.println("Usage: find-main <command> <class-path>");
		System.err.println("Commands:\n"
				+ "    p  Print the package that the class is in.\n"
				+ "    pp Print the package that the class is in as a unix path.\n"
				+ "    m  Print all methods within the class.\n"
				+ "    mm Print the main method of the class. If there is no main method return error.\n");		
	}
	public static void main(String[] args) {
		if (args.length < 2)
		{
			System.err.println("Not enough arguments given.");
			printHelp();
			System.exit(1);
		}

		// Create a file object on the directory containing the class

		try
		{
			JavaClass jclass = new ClassParser(args[1]).parse();

			if (args[0].equals("pp"))
			{
				System.out.println(jclass.getPackageName().replaceAll("\\.", "//"));
			}
			else if (args[0].equals("pp"))
			{
				System.out.println(jclass.getPackageName());
			}
			else if (args[0].equals("m"))
			{
				for (Method method : jclass.getMethods())
				{
					System.out.print(method.getName());
					System.out.println(method.getSignature());
				}				
			}
			else if (args[0].equals("mm"))
			{
				ArrayList<Method> mains = new ArrayList<Method>();

				for (Method method : jclass.getMethods())
				{
					if (method.getName().equals("main") &&
							method.getSignature().equals("([Ljava/lang/String;)V"))
					{
						mains.add(method);
					}
				}
				
				if (mains.size() == 0)
				{
					System.err.println("Error: No main method found.");
					System.exit(10);
				}
				else if (mains.size() > 1)
				{
					System.err.println("Error: Multiple main methods within the same class.");
					System.exit(11);
				}
				
				System.out.print(jclass.getClassName() + '.');
				System.out.print(mains.get(0).getName());
				System.out.print(mains.get(0).getSignature());
				System.out.println();
			}
			else
			{
				System.err.println("Error: Invalid command.");
				System.exit(1);
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