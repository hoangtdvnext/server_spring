package vn.hoangtd.service;

import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Created by hoangtd on 1/23/2017.
 */
public interface JWTCodec {
    /**
     * @param properties
     * @param expiration
     * @param chronoUnit
     * @return
     */
    String encodeJWT(Map properties, Long expiration, ChronoUnit chronoUnit);

    /**
     * @param token
     * @return
     */
    Map decodeJWT(String token);
}
