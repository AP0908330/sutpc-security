package com.sutpc.security.core.util;


import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import sun.misc.BASE64Decoder;

/**
 * .
 *
 * @Author:ShangxiuWu
 * @Description
 * @Date: Created in 9:18 2019/7/4 0004.
 * @Modified By:
 */
public class KeyCloakUtils {


  /**
   * secret 来自keycloak
   */
  private static final String SECRET = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArn0UT6G/05QiYmgmRvoRmKhPzDdqdeasbxy0YaIE6o0y/LnxnIbPorxG3KL0A/HRqvdsj6Y/3njxZ+jQvz4DjZ0oqxF5dJJSxnH8n3cfYbCvEmAX2jEA9/1olHWbkJgdQmFJdBSkm+CNxFkFre5UJ5hNKMQbetg6tPxQlJNchnoIcttsqix1JIq/uWu7qSGly6IQL6Ymb7J3LjRLu30LiVVqiIP2REKfXAu3l6avaXdS+bPO6eXFrVdmsNzvGptBxjluXhSjXtcUDmcZccesOuGoAK+k4BrJFEb0S2O/iy45C9yNY5pCLTreMB0qpLzxkQq0SK5QAz0qrbCmYiW14QIDAQAB";


  private static String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaUTQtRE9qbTVqYmxSNHJXNFpyRm5VZU9oTzczREdaWU9ockwzcWJocUgwIn0.eyJqdGkiOiI4NTkxZjQ0MC02Y2M0LTRhNjEtOGE3MS1iNGJiNWMxZjJmZWMiLCJleHAiOjE1NjIyMDg2MDYsIm5iZiI6MCwiaWF0IjoxNTYyMjA4MzA2LCJpc3MiOiJodHRwOi8vMTAuMTAuMjAxLjI6NTgwODIvYXV0aC9yZWFsbXMvemhhbmppYW5nLXNzbyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI3Mjc5MTliNy0wNWY4LTRlODItOTU4Ni0yNWI5MTRmYTc1ZmEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ6aGFuamlhbmctc3NvLXRlc3QiLCJhdXRoX3RpbWUiOjE1NjIyMDcyMDUsInNlc3Npb25fc3RhdGUiOiI5ZWU2NzQyNy0yODk5LTQ5MmUtYjAyYS1hNGNmY2UwNTcxNmIiLCJhY3IiOiIwIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJ1c2VyX25hbWUiOlsidXNlcm5hbWVvZmZsaW5lX2FjY2VzcyIsInVzZXJuYW1ldW1hX2F1dGhvcml6YXRpb24iXSwibmFtZSI6InNoYW5neGl1IHd1IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW4iLCJnaXZlbl9uYW1lIjoic2hhbmd4aXUiLCJmYW1pbHlfbmFtZSI6Ind1IiwiZW1haWwiOiJhZG1pbjEyM0BzdXRwYy5jb20ifQ.k0dkJqHTbmBTz-h8_42_cA24caLKHhgucvxdKbi9d-2P4TENY2_CWIT-5n7yjCu2ccCQRLVACxlbcJ4N6pke-pjQaVBAvzj8GWJnJk2BIPMtxL5vizC5PEHZIJvT4EpSjDMrfTprX-7eqxdQi_GNy-LVguIiSjv0ooc9G9StoiLJOW6mOoAHDUkCj6PwVhZsIfFnYDyXsnUbfcrkXy7WKXwbHfE05629nf46MKoJ5MiwYON9prTTc6ptECNpRFM2E4CLA2XT8Vo8RG6kVU0T3V6CT5w9kiLbTWwkqZLe1uexwFi6VZt60Tt6j-d7Ha4j1QpEsLteyNVxBvRNS4eDRg";


  public static Claims getTokenBody(String token) throws Exception {
    return Jwts.parser()
        .setSigningKey(getPublicKey(SECRET))
        .parseClaimsJws(token)
        .getBody();
  }

  public static String getUserName(String token) throws Exception {
    Claims tokenBody = getTokenBody(token);
    Object username = tokenBody.get("preferred_username");
    return username == null ? null : (String) username;
  }

  public static PublicKey getPublicKey(String key) throws Exception {
    byte[] keyBytes;
    keyBytes = (new BASE64Decoder()).decodeBuffer(key);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicKey = keyFactory.generatePublic(keySpec);
    return publicKey;
  }

}
