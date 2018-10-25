import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import io.jsonwebtoken.JwtException;

Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
String myClaim = "Jamppa";
Calendar calendar = Calendar.getInstance();
calendar.add(Calendar.SECOND, 4);
Date expirationDate = calendar.getTime();
String jws = Jwts.builder().
                setSubject(myClaim).
                signWith(key).
                setExpiration(expirationDate).
                compact();

String parsedClaim;
try {
    parsedClaim = Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject();
}
catch (JwtException e) {
    System.out.println("e: " + e.getMessage());
}
System.out.println("parsedClaim: " + parsedClaim);
