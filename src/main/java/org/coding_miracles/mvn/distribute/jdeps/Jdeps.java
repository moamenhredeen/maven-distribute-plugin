package org.coding_miracles.mvn.distribute.jdeps;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.spi.ToolProvider;
import java.util.stream.Collectors;

public class Jdeps {


	//********************************************************************************************
	//
	// options:
	//
	//********************************************************************************************

	/**
	 * -version
	 * --version
	 * version information
	 */
	private static final String VERSION = "--version";

	/**
	 * -h
	 * -?
	 * --help
	 * Print this help message
	 */
	private static final String HELP = "-help";

	/**
	 * -q
	 * -quit
	 * Suppress warning messages
	 */
	private static final String QUIT = "-quit";

	/**
	 * -v
	 * -verbose
	 * Print all class level dependencies Equivalent to -verbose:class -filter:none.
	 */
	private static final String VERBOSE = "-verbose";

	/**
	 * -verbose:package
	 * Print package-level dependencies excluding dependencies within the same package by default
	 */
	private static final String VERBOSE_PACKAGE = "-verbose:package";

	/**
	 * -verbose:class
	 * Print class-level dependencies excluding  dependencies within the same package by default
	 */
	private static final String VERBOSE_CLASS = "-verbose:class";

	/**
	 * -s
	 * -summary
	 * Print dependency summary only.
	 */
	private static final String SUMMARY = "-summary";


	/**
	 * -cp <path>
	 * -classpath <path>
	 * --class-path <path>
	 * specify where to find class files
	 */
	private static final String CLASS_PATH = "--class-path";


	/**
	 * -dotoutput <dir>
	 * --dot-output <dir>
	 * Destination directory for DOT file output
	 */
	private static final String DOT_OUTPUT = "--dot-output";



	//********************************************************************************************
	//
	// Module dependence analysis options:
	//
	//********************************************************************************************

	/**
	 * -m <module-name>
	 * --module <module-name>
	 * Specify the root module for analysis
	 */
	private static final String MODULE = "--module";

	/**
	 * --generate-module-info <dir>
	 * Generate module-info.java under the specified directory.
	 * The specified JAR files will be analyzed.
	 * This option cannot be used with --dot-output or --class-path.
	 * Use --generate-open-module option for open modules.
	 */
	private static final String GENERATE_MODULE_INFO = "--generate-module-info";

	/**
	 * --check  <module-name>[,<module-name>...
	 * Analyze the dependence of the specified modules
	 * It prints the module descriptor, the resulting
	 * module dependencies after analysis and the
	 * graph after transition reduction.  It also
	 * identifies any unused qualified exports.
	 */
	private static final String CHECK = "--check";

	/**
	 * --list-deps
	 * Lists the module dependencies.  It also prints
	 * any internal API packages if referenced.
	 * This option transitively analyzes libraries on
	 * class path and module path if referenced.
	 * Use --no-recursive option for non-transitive
	 * dependency analysis.
	 */
	private static final String LIST_DEPS = "--list-deps";

	/**
	 * --list-reduced-deps
	 * Same as --list-deps with not listing
	 * the implied reads edges from the module graph.
	 * If module M1 reads M2, and M2 requires
	 * transitive on M3, then M1 reading M3 is implied
	 * and is not shown in the graph.
	 */
	private static final String LIST_REDUCED_DEPS = "--list-reduced-deps";

	/**
	 * --print-module-deps
	 * Same as --list-reduced-deps with printing
	 * a comma-separated list of module dependencies.
	 * This output can be used by jlink --add-modules
	 * in order to create a custom image containing
	 * those modules and their transitive dependencies.
	 */
	private static final String PRINT_MODULE_DEPS = "--print-module-deps";


	/**
	 * --ignore-missing-deps
	 * Ignore missing dependencies.
	 */
	private static final String IGNORE_MISSING_DEPS = "--ignore-missing-deps";



	private final Map<String, String> options = new HashMap<>();

	public Jdeps help() {
		this.options.put(HELP, "");
		return this;
	}

	public Jdeps verbose() {
		this.options.put(VERBOSE, "");
		return this;
	}

	public Jdeps version() {
		this.options.put(VERSION, "");
		return this;
	}

	public Jdeps classPath(final String classPath) {
		this.options.put(CLASS_PATH, classPath);
		return this;
	}

	public Jdeps listDeps(){
		this.options.put(LIST_DEPS, "");
		return this;
	}



	/**
	 * @param path can be a pathname to a .class file, a directory, a JAR file.
	 */
	public void run(final String path) {
		var jdepsOptional = ToolProvider.findFirst("jdeps");
		if (jdepsOptional.isEmpty()) {
			return;
		}

		var transformedOptions = this.options.entrySet()
				.stream()
				.map(entry -> entry.getKey() + entry.getValue())
				.collect(Collectors.toList());

		if (!(path == null || path.isEmpty())){
			transformedOptions.add(path);
		}

		System.out.println(transformedOptions);

		var byteArrayOutputStream = new ByteArrayOutputStream();
		var printOutputStream = new PrintStream( byteArrayOutputStream, true, StandardCharsets.UTF_8);

		var jdeps = jdepsOptional.get();

		jdeps.run(printOutputStream, System.err, transformedOptions.toArray(new String[0]));

		new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())))
				.lines()
				.map(e -> e.split(" "))
				.forEach(System.out::println);

	}
}
