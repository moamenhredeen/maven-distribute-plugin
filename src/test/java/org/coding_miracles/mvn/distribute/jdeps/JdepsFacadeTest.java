package org.coding_miracles.mvn.distribute.jdeps;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class JdepsFacadeTest {

    private final JdepsFacade jdepsFacade = new JdepsFacade();

    @Test
    void testJdeps() {
        try {
            jdepsFacade.jdeps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}