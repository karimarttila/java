package simpleserver.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleserver.util.Consts;

import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import simpleserver.util.SSConfiguration;

import java.security.Key;

@Service
public class SessionImpl implements Session {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The Synchronized sessions. Synchronized since Spring Services
     * are singletons by default (and hence not multi-thread safe)
     * and class member variables need to be synchoronized
     * to be thread safe.
     */
    private Set<String> sessions = Collections.synchronizedSet(new HashSet<String>());

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    SSConfiguration config;


    @Override
    public String createJsonWebToken(String userEmail) {
        logger.debug(Consts.LOG_ENTER);
        int expSecs = config.getJsonWebTokenExpirationSeconds();
        String myClain = "{ " +
                        "\"email\": \"" + userEmail + "\"" +
                        " }";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expSecs);
        Date expirationDate = calendar.getTime();
        String jws = Jwts.builder().
                setSubject(myClain).
                signWith(key).
                setExpiration(expirationDate).
                compact();
        logger.debug(Consts.LOG_EXIT);
        return jws;
    }
}
