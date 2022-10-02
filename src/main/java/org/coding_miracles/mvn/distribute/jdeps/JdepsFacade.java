package org.coding_miracles.mvn.distribute.jdeps;

import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JdepsFacade {


    private MavenProject project;

    public void jdeps() throws IOException, CommandLineException {

        var cmd = new Commandline();
        var consumer = new Consumer();
        var jdepsPath = getJDepsExecutablePath();
        cmd.setExecutable(jdepsPath.toString());

        int exitCode = CommandLineUtils.executeCommandLine(cmd, System.out::println, System.err::println);
    }


    private Path getJDepsExecutablePath() throws IOException {
        var javaHome = System.getenv("JAVA_HOME");
        var jdepsExecutablePath = Paths.get(javaHome).resolve("bin/jdeps.exe");
        if (!Files.exists(jdepsExecutablePath)) {
            throw new FileNotFoundException("jdeps not fount");
        }

        return jdepsExecutablePath;
    }
}
