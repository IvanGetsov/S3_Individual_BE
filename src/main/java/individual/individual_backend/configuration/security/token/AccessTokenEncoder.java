package individual.individual_backend.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
