package simpleserver.webserver;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleserver.util.SSConsts;

import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;
import simpleserver.util.SSProperties;

import java.security.Key;

@Service
public class SessionImpl implements Session {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The Synchronized mySessions. Synchronized since Spring Services
     * are singletons by default (and hence not multi-thread safe)
     * and class member variables need to be synchoronized
     * to be thread safe.
     */
    private final Set<String> mySessions = Collections.synchronizedSet(new HashSet<>());

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final SSProperties props;

    @Autowired
    public SessionImpl(SSProperties props) {
        this.props = props;
    }

    @Override
    public String createJsonWebToken(String userEmail) {
        logger.debug(SSConsts.LOG_ENTER);
        int expSecs = props.getJsonWebTokenExpirationSeconds();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expSecs);
        Date expirationDate = calendar.getTime();
        String jws = Jwts.builder()
                .setSubject(userEmail)
                .signWith(key)
                .setExpiration(expirationDate)
                .compact();
        logger.trace("jsonWebToken: {}", jws);
        mySessions.add(jws);
        logger.debug(SSConsts.LOG_EXIT);
        return jws;
    }

    @Override
    public String validateJsonWebToken(String jwt) {
        logger.debug(SSConsts.LOG_ENTER);
        String ret = null;
        // Validation #1.
        boolean found = mySessions.contains(jwt);
        if (!found) {
            logger.warn("Token not found in my sessions - unknown token: {}", jwt);
        }
        // Validation #2.
        else {
            try {
                ret = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getSubject();
            }
            catch (ExpiredJwtException expiredEx) {
                logger.warn("Token is expired, removing it from my sessions and returning nil: {}", expiredEx.getMessage());
                mySessions.remove(jwt);
            }
            catch (JwtException otherEx) {
                logger.error("Some error in session handling: {}", otherEx.getMessage());
            }
        }
        logger.debug(SSConsts.LOG_EXIT);
        return ret;
    }
}
