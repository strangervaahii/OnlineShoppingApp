package com.shoppingapp.OnlineShoppingApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtService {

    public static final String SECRET = "gCq5A8XMjiw5AFeUYPDYqiVoZGFFE5dHowhgaSw/qG6s2STGMBWvUls/2wvvVE/TaF5hPR4bUHVXaiyBXc+duaJzTPsANRyWOmrdPyckr31hkqivHA7g71yU8cv9IquuJSopH7SorJcii0c4HzK4OkFmxIsfPbkeRbUIj0mcoF6yk/iZsTmAXrOMHIi7FR860OAFa1pesAMoCf4ro7chpGM39VYDiYs/q4MwxzmcLo6u27JEr98WRA4OYOXPSyNB/r7iDBOOI64w3YhbAey7pzpmKBYoioGOg6ycN37dyL44tg9+DtShqiStHgnifEnrfUhkqX5GvniXuyOEzOwaILaA7BIyX7nN8Ia/85ZX4NY=\n";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration((new Date(System.currentTimeMillis()+1000*60*60*24*10)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keybytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keybytes);
    }
}
