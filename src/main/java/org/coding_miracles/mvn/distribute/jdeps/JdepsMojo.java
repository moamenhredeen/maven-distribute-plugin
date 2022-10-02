package org.coding_miracles.mvn.distribute.jdeps;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "jdeps", defaultPhase = LifecyclePhase.PACKAGE )
public class JdepsMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;

    public void execute() throws MojoExecutionException
    {
        getLog().info("execute jdeps");
    }
}
