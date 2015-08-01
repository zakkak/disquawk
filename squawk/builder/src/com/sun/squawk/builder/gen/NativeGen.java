/*
 * Copyright     2015, FORTH-ICS / CARV
 *                    (Foundation for Research & Technology -- Hellas,
 *                     Institute of Computer Science,
 *                     Computer Architecture & VLSI Systems Laboratory)
 * Copyright 2004-2008 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * only, as published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * Please contact Sun Microsystems, Inc., 16 Network Circle, Menlo
 * Park, CA 94025 or visit www.sun.com if you need additional
 * information or have any questions.
 */

package com.sun.squawk.builder.gen;

import java.util.Vector;
import java.util.Hashtable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

public class NativeGen {

	/**
	 * Hashtable of methods to exclude,
	 */
	Hashtable<String, NativeGen> exclude = new Hashtable<String, NativeGen>();

	/**
	 * Native method id allocator.
	 */
	int nextNativeMethodIdentifier;

	/**
	 * The table of native methods defined so far. The identifier for each method is given
	 * by its index in this vector.
	 */
	Vector<String> nativeMethods = new Vector<String>();

	/**
	 * Output option.
	 */
	int optionNo;

	/**
	 * Constructor,
	 */
	NativeGen() {
		exclude.put("math", this);
		exclude.put("floatToIntBits", this);
		exclude.put("doubleToLongBits", this);
		exclude.put("intBitsToFloat", this);
		exclude.put("longBitsToDouble", this);
	}

	/**
	 * Entry point.
	 *
	 * @param args command line arguments
	 */
	public void run(String[] args) throws Exception {
		optionNo = Integer.parseInt(args[0]);
		if (optionNo == 2) {
			System.out.println("//if[SUITE_VERIFIER]");
		}
		System.out.println("/* **DO NOT EDIT THIS FILE** */");
		System.out.println("/*");
		System.out.println(" * Copyright 2004 Sun Microsystems, Inc. All Rights Reserved.");
		System.out.println(" * Copyright 2014 Sun Microsystems, Inc. All Rights Reserved.");
		System.out.println(" * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER");
		System.out.println(" *");
		System.out.println(" * This code is free software; you can redistribute it and/or modify");
		System.out.println(" * it under the terms of the GNU General Public License version 2");
		System.out.println(" * only, as published by the Free Software Foundation.");
		System.out.println(" *");
		System.out.println(" * This code is distributed in the hope that it will be useful, but");
		System.out.println(" * WITHOUT ANY WARRANTY; without even the implied warranty of");
		System.out.println(" * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU");
		System.out.println(" * General Public License version 2 for more details (a copy is");
		System.out.println(" * included in the LICENSE file that accompanied this code).");
		System.out.println(" *");
		System.out.println(" * You should have received a copy of the GNU General Public License");
		System.out.println(" * version 2 along with this work; if not, write to the Free Software");
		System.out.println(" * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA");
		System.out.println(" * 02110-1301 USA");
		System.out.println(" *");
		System.out.println(" * Please contact Sun Microsystems, Inc., 16 Network Circle, Menlo");
		System.out.println(" * Park, CA 94025 or visit www.sun.com if you need additional");
		System.out.println(" * information or have any questions.");
		System.out.println(" */");
		System.out.println();
		if (optionNo == 2) {
			System.out.println("package com.sun.squawk.translator.ir.verifier;");
		} else {
			System.out.println("package com.sun.squawk.vm;");
		}
		System.out.println();
		if (optionNo == 0) {
			System.out.println("/**");
			System.out.println(" * This class defines the native method identifiers used in the Squawk system.");
			System.out.println(" * It was automatically generated as a part of the build process");
			System.out.println(" * and should not be edited by hand.");
			System.out.println(" *");
			System.out.println(" * @author   Nik Shaylor");
			System.out.println(" * @author   Doug Simon");
			System.out.println(" */");
			System.out.println("public final class Native {");
			System.out.println();
		} else if (optionNo == 1) {
			System.out.println("import com.sun.squawk.compiler.*;");
			System.out.println("/**");
			System.out.println(" * This class defines the native method dispatch table.");
			System.out.println(" * It was automatically generated as a part of the build process");
			System.out.println(" * and should not be edited by hand.");
			System.out.println(" *");
			System.out.println(" * @author   Nik Shaylor");
			System.out.println(" * @author   Doug Simon");
			System.out.println(" */");
			System.out.println("abstract class InterpreterNative extends InterpreterSwitch {");
			System.out.println();
			System.out.println("    void do_nativeswitch() {");
		} else {
			System.out.println("import com.sun.squawk.*;");
			System.out.println("import com.sun.squawk.vm.Native;");
			System.out.println("import com.sun.squawk.util.Assert;");
			System.out.println();
			System.out.println("class NativeVerifierHelper {");
			System.out.println("    private static final Klass INT = Klass.INT,");
			System.out.println("                               SHORT = Klass.SHORT,");
			System.out.println("                               CHAR = Klass.CHAR,");
			System.out.println("                               BYTE = Klass.BYTE,");
			System.out.println("                               BOOLEAN = Klass.BOOLEAN,");
			System.out.println("/*if[FLOATS]*/");
			System.out.println("                               FLOAT = Klass.FLOAT,");
			System.out.println("                               DOUBLE = Klass.DOUBLE,");
			System.out.println("/*end[FLOATS]*/");
			System.out.println("                               WORD = Klass.OFFSET,");
			System.out.println("                               UWORD = Klass.UWORD,");
			System.out.println("                               REF = Klass.ADDRESS,");
			System.out.println("                               LONG = Klass.LONG,");
			System.out.println("                               KLASS = Klass.KLASS,");
			System.out.println("                               OOP = Klass.OBJECT;");
			System.out.println();
			System.out.println("    static void do_invokenative(Frame frame, int index) {");
			System.out.println("        switch (index) {");
		}
		lookup(Class.forName("com.sun.squawk.Address"));
		lookup(Class.forName("com.sun.squawk.UWord"));
		lookup(Class.forName("com.sun.squawk.Offset"));

		// Only methods of classes processed before this point can be linked to by classes loaded dynamically
		if (optionNo == 0) {
			defineLinkableNativeMethodTable(nativeMethods);
		}

		lookup(Class.forName("com.sun.squawk.NativeUnsafe"));
		lookup(Class.forName("com.sun.squawk.VM"));
		lookup(Class.forName("com.sun.squawk.CheneyCollector"));
		lookup(Class.forName("com.sun.squawk.ServiceOperation"));
		lookup(Class.forName("com.sun.squawk.GarbageCollector"));
		lookup(Class.forName("com.sun.squawk.Lisp2Bitmap"));
		lookup(Class.forName("com.sun.squawk.Lisp2Bitmap$Iterator"));

		lookup(Class.forName("com.sun.squawk.SoftwareCache"));
		lookup(Class.forName("com.sun.squawk.platform.MMP"));
		lookup(Class.forName("com.sun.squawk.platform.MMGR"));
		lookup(Class.forName("com.sun.squawk.RWlock"));

		output(Class.forName("com.sun.squawk.VM"), "lcmp", true, new Class[] { Long.TYPE, Long.TYPE }, Integer.TYPE);

		if (optionNo == 0) {
			defineNativeMethodIdentifier("ENTRY_COUNT", "" + nextNativeMethodIdentifier);
		} else if (optionNo == 1) {
			System.out.println("    }");
			System.out.println("    abstract protected void nativepop(Type t);");
			System.out.println("    abstract protected void nativepush(Type t);");
			System.out.println("    abstract protected void nativebind(int x);");
			System.out.println("    abstract protected void nativedone();");
		} else {
			System.out.println("        }");
			System.out.println("        Assert.that(false, \"native method with index \" + index + \" was not found\");");
			System.out.println("    }");
		}
		System.out.println("}");
	}

/**
 * Writes the lookup table for the native methods that can be linked to by dynamically loaded classes.
 * The table written is encoded as a String.
 *
 * @param methods   the methods to put in the table
 */
	void defineLinkableNativeMethodTable(Vector<String> methods) {

		System.out.println();
		System.out.println("    /**");
		System.out.println("     * An encoded table of the native methods that can be linked to during class loading at runtime.");
		System.out.println("     * Each entry in the table is a length 'n' (encoded as a char relative to '0') followed by one or more characters 'chars' delimited");
		System.out.println("     * by a space character. The index of the entry is the native method identifier and the name of the native");
		System.out.println("     * method is constructed from the first 'n' characters of the name of the previous method concatenated with 'chars'.");
		System.out.println("     */");
		System.out.println("    public final static String LINKABLE_NATIVE_METHODS =");
		System.out.println();

		String previous = "";
		int id = 0;
		for (String method: methods) {
			int substring = 0;
			while (previous.regionMatches(false, 0, method, 0, substring + 1)) {
				substring++;
			}

			// Convert length to character value relative to '0'
			char charValue = (char)(substring + '0');
			String charValueAsString;
			if (charValue <= '~') {
				// Printable ASCII constant
				charValueAsString = "" + charValue;
			} else {
				// Unicode constant
				charValueAsString = Integer.toHexString(charValue);
				while (charValueAsString.length() < 4) {
					charValueAsString = "0" + charValueAsString;
				}
				charValueAsString = "\\\\u" + charValueAsString;
			}

			int indent = substring - charValueAsString.length();
			String idString = "" + id;
			while (idString.length() < 3) {
				idString = " " + idString;
			}

			String prefix = "        /* " + idString + " */\"" + charValueAsString + "\" + ";
			System.out.print(prefix);
			for (int i = 0; i <= indent; ++i) {
				System.out.print(' ');
			}
			System.out.println("\"" + method.substring(substring) + " \" +");

			previous = method;
			++id;
		}
		System.out.println("        \"\";");
		System.out.println();

	}

/**
 * Lookup methods in a class.
 *
 * @param cls the class to lookup
 */
	void lookup(Class<?> cls) throws Exception {
		Method[] methods = cls.getDeclaredMethods();
		Arrays.sort(methods, new Comparator<Method>() {
				public int compare(Method o1, Method o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
		for (int i = 0 ; i < methods.length ; i++) {
			Method method = methods[i];
			if (Modifier.isNative(method.getModifiers()) || hasNativePragma(method)) {
				output(method);
			}
		}
	}

/**
 * hasNativePragma
 *
 * @param method Method
 * @return boolean
 */
	private boolean hasNativePragma(Method method) {
		Class<?>[] exceptionTypes = method.getExceptionTypes();
		for (int i = 0; i != exceptionTypes.length; ++i) {
			Class<?> type = exceptionTypes[i];
			if (type.getName().equals("com.sun.squawk.pragma.NativePragma")) {
				return true;
			}
		}
		return false;
	}

/**
 * Allocates a unique identifier for a native method and writes its definition.
 *
 * @param name  the name by which the native method is looked up
 */
	void allocateAndDefineNativeMethodIdentifier(String name) {
		int identifier = nextNativeMethodIdentifier++;
		defineNativeMethodIdentifier(name, ""+(identifier));

		name = name.replace('_', '.').replace('$', '.');
		nativeMethods.addElement(name);
	}

/**
 * Writes the definition of a native method's identifier.
 *
 * @param name  the name by which the native method is looked up
 * @param id String
 */
	void defineNativeMethodIdentifier(String name, String id) {
		System.out.println(space("    public final static int "+name, 73)+" = "+id+";");
	}

/**
 * Output a method.
 *
 * @param method the method
 */
	void output(Method method) throws Exception {
		Class<?> cls        = method.getDeclaringClass();
		String mname     = method.getName();
		boolean isStatic = Modifier.isStatic(method.getModifiers());
		Class<?>[] parms    = method.getParameterTypes();
		Class<?> ret        = method.getReturnType();
		output(cls, mname, isStatic, parms, ret);
	}

/**
 * Output a method.
 *
 * @param cls       the class in which the method was declared
 * @param mname     the name of the method
 * @param isStatic  true if the method is static
 * @param parms     the types of the method's parameters
 * @param ret       the return type of the method
 */
	void output(Class<?> cls, String mname, boolean isStatic, Class<?>[] parms, Class<?> ret) throws Exception {
		if (mname.indexOf('_') != -1 ||  mname.indexOf('$') != -1) {
			System.err.println("Must not have '.' or '$' in native method name: "+mname);
			System.exit(-1);
		}
		String symbol = cls.getName().replace('.', '_') + "$" + mname;
		if (optionNo == 0) {
			allocateAndDefineNativeMethodIdentifier(symbol);
		} else if (optionNo == 1) {
			System.out.println("        nativebind(Native."+symbol+");");

			if (symbol.equals("java_lang_VM$executeCIO")) {
				System.out.println("/*if[INCLUDE_EXECUTECIO_PARMS]*/");
			}

			for (int i = parms.length - 1 ; i >= 0 ; --i) {
				Class<?> parm = parms[i];
				String type = getType(parm);
				System.out.println("            nativepop("+type+"); // "+parm.getName());
			}
			if (!isStatic) {
				String type = getType(cls);
				System.out.println("            nativepop("+type+"); // " + cls.getName() + " (receiver)");
			}

			if (symbol.equals("java_lang_VM$executeCIO")) {
				System.out.println("/*end[INCLUDE_EXECUTECIO_PARMS]*/");
			}

			System.out.println("            invokenativeswapping(Native."+symbol+");");
			if (ret != Void.TYPE) {
				System.out.println("            nativepush("+getType(ret)+"); // "+ret.getName());
			}
			System.out.println("            nativedone();");
			System.out.println();
		} else if (optionNo == 2) {
			System.out.println("        case Native." + symbol + ": {");
//if (symbol.equals("java_lang_VM$executeCIO")) {
//   System.out.println("/*if[INCLUDE_EXECUTECIO_PARMS]*/");
//}
			for (int i = parms.length - 1; i >= 0; i--) {
				Class<?> parm = parms[i];
				String type = getType(parm, false);
				System.out.println("            frame.pop(" + type + "); // " + parm.getName());
			}
			if (!isStatic) {
				String type = getType(cls, false);
				System.out.println("            frame.pop(" + type + "); // " + cls.getName() + " (receiver)");
			}
//if (symbol.equals("java_lang_VM$executeCIO")) {
//   System.out.println("/*end[INCLUDE_EXECUTECIO_PARMS]*/");
//}
			System.out.println("            Assert.that(frame.isStackEmpty());");
			if (ret != Void.TYPE) {
				System.out.println("            frame.push(" + getType(ret, false) + "); // " + ret.getName());
			}
			System.out.println("            return;");
			System.out.println("        }");
			System.out.println();
		} else {
			System.out.println("            case Native."+symbol+": {");
			System.out.println("                break;");
			System.out.println("            }\n");
		}
	}

/**
 * Add spaces to a string
 */
	String space(String s, int n) {
		while (s.length() < n) {
			s += " ";
		}
		return s;
	}

/**
 * Get the Squawk compiler type for a class.
 * Will return INT for any integer type smaller than long.
 *
 * @param cls the class
 * @return the type
 */
	String getType(Class<?> cls) {
		return getType(cls, true);
	}

/**
 * Get the Squawk compiler type for a class.
 *
 * @param cls the class
 * @param grow whether to grow integer types smaller than INT up to INT
 * @return the type
 */
	String getType(Class<?> cls, boolean grow) {
		if (cls == Float.TYPE) {
			return "FLOAT";
		}
		if (cls == Double.TYPE) {
			return "DOUBLE";
		}
		if (cls == Long.TYPE) {
			return "LONG";
		}
		if (cls == Integer.TYPE) {
			return "INT";
		}
		if (cls == Short.TYPE) {
			return (grow) ? "INT" : "SHORT";
		}
		if (cls == Character.TYPE) {
			return (grow) ? "INT" : "CHAR";
		}
		if (cls == Byte.TYPE) {
			return (grow) ? "INT" : "BYTE";
		}
		if (cls == Boolean.TYPE) {
			return (grow) ? "INT" : "BOOLEAN";
		}
		String cname = cls.getName();
		if (cname.equals("com.sun.squawk.Address")) {
			return "REF";
		} else if (cname.equals("com.sun.squawk.Word") || (optionNo == 2 && cname.equals("com.sun.squawk.UWord"))) {
			return "UWORD";
		} else if (cname.equals("com.sun.squawk.Offset")) {
			return "WORD";
		} else if (optionNo == 2 && cname.equals("com.sun.squawk.Klass")) {
			return "KLASS";
		} else {
			return "OOP";
		}
	}

/**
 * Entry point.
 *
 * @param args command line arguments
 */
	public static void main(String[] args) throws Exception {
		new NativeGen().run(args);
	}
}
