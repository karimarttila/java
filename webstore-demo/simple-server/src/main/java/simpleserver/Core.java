package simpleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleserver.util.Consts;

@SpringBootApplication
public class Core {

    private static final Logger logger = LoggerFactory.getLogger("CORE");
    /**
     * Main entry point to the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        logger.debug(Consts.LOG_ENTER);
        SpringApplication.run(Core.class, args);
        logger.debug(Consts.LOG_EXIT);

    }
}
