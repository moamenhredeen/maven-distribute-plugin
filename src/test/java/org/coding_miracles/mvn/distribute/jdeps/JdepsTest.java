package org.coding_miracles.mvn.distribute.jdeps;

import org.junit.jupiter.api.Test;

class JdepsTest {
    private final Jdeps jdeps = new Jdeps();

    @Test
    void testJdepsRun(){
        this.jdeps.run("C:/Users/moame/git-repos/maven-distribute-plugin/target/maven-distribute-plugin-1.0.jar");
    }
}