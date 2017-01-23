package vn.hoangtd.service;

/**
 * Created by hoangtd on 1/23/2017.
 */
public interface AuthenticationService {
    /**
     * @param signInForm
     * @return
     */
    String signIn(SignInForm signInForm);

    /**
     * @param token
     * @return
     */
    Employee findAuthByAccessToken(String token);

    /**
     * @param token
     * @return
     */
    Boolean isLoggedIn(String token);
}
