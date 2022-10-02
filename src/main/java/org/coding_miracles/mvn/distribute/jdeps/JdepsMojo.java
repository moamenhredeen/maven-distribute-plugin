package org.coding_miracles.mvn.distribute.jdeps;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "jdeps", defaultPhase = LifecyclePhase.PACKAGE)
public class JdepsMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    /**
     * Indicates whether the build will continue even if there are jdeps warnings.
     */
    @Parameter(defaultValue = "true", property = "jdeps.failOnWarning")
    private boolean failOnWarning;

    @Parameter(property = "jdeps.verbose", required = false)
    private String verbose;

    private final Jdeps jdeps = new Jdeps();

    public void execute() throws MojoExecutionException {
        jdeps.help()
                .verbose()
                .run(null);
    }
}
