package citytech.platforms.security;

import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.usecases.login.LoginUseCaseRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Singleton
public class SecurityUtils {
    public static String token(LoginUseCaseRequest user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        return Jwts.builder()
                .claim("username", user.username())
                .signWith(getKey())
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
    }

    public static Key getKey() {
        String jwtSignature = "93ab45906f7b4213d82a4bc1ec5c9a7a18f159d121b7bc371d7dca68e764c6ea";
        return Keys.hmacShaKeyFor(jwtSignature.getBytes(StandardCharsets.UTF_8));
    }

    public RequestContext parseTokenAndGetContext(String token) {
        try {
            Claims claims = this.parseToken(token);
            RequestContext requestContext = new RequestContext();
            requestContext.setUsername(claims.get("username", String.class));
            return requestContext;
        } catch (Exception exception) {
            if (exception instanceof ExpiredJwtException)
                throw new SapatiAshwinException(SapatiAshwinExceptionType.SECURITY_TOKEN_EXPIRED);
            throw new SapatiAshwinException(SapatiAshwinExceptionType.SECURITY_TOKEN_INVALID);
        }
    }

}