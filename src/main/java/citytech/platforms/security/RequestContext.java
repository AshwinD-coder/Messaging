package citytech.platforms.security;

public class RequestContext {
    private String username;

    public RequestContext() {
    }

    public RequestContext(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "RequestContext{" + "username='" + username + '\'' + '}';
    }
}