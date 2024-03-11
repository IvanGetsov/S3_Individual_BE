package individual.individual_backend.configuration.security.token;



public interface AccessToken {
    String getSubject();

    Integer getUserId();

    String getRole();

    boolean hasRole(String roleName);
}
