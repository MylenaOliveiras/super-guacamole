package org.acme.security;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;


@Provider
@ApplicationScoped
public class ApiKeyFilter implements ContainerRequestFilter
{

    @ConfigProperty(name = "quarkus.api-key.value")
    String apiKey;

    @ConfigProperty(name = "quarkus.api-key.header-name", defaultValue = "X-API-Key")
    String apiKeyHeader;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        if (isPublicRoute(path)) {
            return;
        }

        String providedKey = requestContext.getHeaderString(apiKeyHeader);

        if (providedKey == null || !providedKey.equals(apiKey)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("API key inválida ou ausente.")
                            .build()
            );
        }
    }

    private boolean isPublicRoute(String path) {
        return path.contains("/public/") || path.startsWith("/health") || path.startsWith("/swagger");
    }
}
