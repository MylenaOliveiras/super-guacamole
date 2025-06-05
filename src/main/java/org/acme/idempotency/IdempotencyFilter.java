package org.acme.idempotency;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Provider
@ApplicationScoped
@Priority(Priorities.HEADER_DECORATOR)
public class IdempotencyFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";
    private static final String IDEMPOTENT_CONTEXT_PROPERTY = "idempotent-context";

    private final Cache<String, IdempotencyRecord> cache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();
        Class<?> clazz = resourceInfo.getResourceClass();

        Idempotent methodAnnotation = method.getAnnotation(Idempotent.class);
        Idempotent classAnnotation = clazz.getAnnotation(Idempotent.class);

        if (methodAnnotation == null && classAnnotation == null) return;

        Idempotent config = methodAnnotation != null ? methodAnnotation : classAnnotation;

        String idempotencyKey = requestContext.getHeaderString(IDEMPOTENCY_KEY_HEADER);

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity("O cabeçalho X-Idempotency-Key é obrigatório.")
                    .build());
            return;
        }

        String cacheKey = createCacheKey(requestContext, idempotencyKey);

        IdempotencyRecord record = cache.getIfPresent(cacheKey);

        if (record != null) {
            requestContext.abortWith(Response.status(record.getStatus()).entity(record.getBody()).build());
            return;
        }

        requestContext.setProperty(IDEMPOTENT_CONTEXT_PROPERTY,
                new IdempotentContext(cacheKey, config.expireAfter()));
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        IdempotentContext context = (IdempotentContext) requestContext.getProperty(IDEMPOTENT_CONTEXT_PROPERTY);
        if (context == null) return;

        IdempotencyRecord record = new IdempotencyRecord(
                responseContext.getStatus(),
                responseContext.getEntity(),
                Instant.now().plusSeconds(context.getExpireAfter())
        );

        cache.put(context.getCacheKey(), record);
    }

    private String createCacheKey(ContainerRequestContext requestContext, String idempotencyKey) {
        return requestContext.getMethod() + ":" + requestContext.getUriInfo().getPath() + ":" + idempotencyKey;
    }

    private static class IdempotentContext {
        private final String cacheKey;
        private final int expireAfter;

        public IdempotentContext(String cacheKey, int expireAfter) {
            this.cacheKey = cacheKey;
            this.expireAfter = expireAfter;
        }

        public String getCacheKey() {
            return cacheKey;
        }

        public int getExpireAfter() {
            return expireAfter;
        }
    }

    private static class IdempotencyRecord {
        private final int status;
        private final Object body;
        private final Instant expiry;

        public IdempotencyRecord(int status, Object body, Instant expiry) {
            this.status = status;
            this.body = body;
            this.expiry = expiry;
        }

        public int getStatus() {
            return status;
        }

        public Object getBody() {
            return body;
        }

        public Instant getExpiry() {
            return expiry;
        }
    }
}
