package simpleserver.webserver;

public interface Session {

    String createJsonWebToken(String userEmail);
}
