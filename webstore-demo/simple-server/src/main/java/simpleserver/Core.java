package simpleserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import simpleserver.util.SSConsts;


@SpringBootApplication
public class Core {

    private static final Logger logger = LoggerFactory.getLogger("CORE");

    /**
     * Main entry point to the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        logger.debug(SSConsts.LOG_ENTER);
        SpringApplication.run(Core.class, args);
        logger.debug(SSConsts.LOG_EXIT);

    }

}
