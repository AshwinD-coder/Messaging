package citytech.platforms.security;


import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.platforms.response.SapatiAshwinResponse;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


@Filter("/**")
public class SecurityFilter implements HttpServerFilter {
    private static final String TOKEN = "X-XSRF-TOKEN";
    private final SecurityUtils securityUtils;

    private final Logger logger = Logger.getLogger(SecurityFilter.class.getName());

    @Inject
    public SecurityFilter(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        try {
            if (request.getMethod() == HttpMethod.OPTIONS) return Flowable.just(HttpResponse.ok());
            var token = request.getHeaders().get(TOKEN);
            if (request.getPath().contains("/user/signup")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/user/login")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/rest-client/login")) {
                return chain.proceed(request);
            }
            if (request.getPath().contains("/reverse-geocoding")) {
                return chain.proceed(request);
            }
            if (Objects.isNull(token) || token.isEmpty()) {
                throw new SapatiAshwinException(SapatiAshwinExceptionType.SECURITY_TOKEN_MISSING);
            }
            RequestContext requestContext = securityUtils.parseTokenAndGetContext(token);
            logger.log(Level.INFO, "REQUESTED BY :: {0} ", requestContext.getUsername());
            return Flowable.just(true).doOnRequest(t -> {
                ContextHolder.set(requestContext);
            }).switchMap(aBoolean -> chain.proceed(request)).onErrorReturn(throwable -> {
                throw new SapatiAshwinException(SapatiAshwinExceptionType.SECURITY_INTERCEPTOR_EXCEPTION);
            });
        } catch (SapatiAshwinException e) {
            return Flowable.just(HttpResponse.badRequest(new SapatiAshwinResponse<>(e.getCode(), e.getMessage(), null)));
        }

    }

}