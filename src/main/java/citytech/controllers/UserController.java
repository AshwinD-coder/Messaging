package citytech.controllers;


import citytech.controllers.payloads.LoginPayload;
import citytech.controllers.payloads.SignupPayload;
import citytech.converters.UserConverter;
import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.platforms.response.SapatiAshwinResponse;
import citytech.usecases.login.LoginUseCase;
import citytech.usecases.login.LoginUseCaseRequest;
import citytech.usecases.login.LoginUseCaseResponse;
import citytech.usecases.signup.SignupUseCase;
import citytech.usecases.signup.SignupUseCaseRequest;
import citytech.usecases.signup.SignupUseCaseResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Controller("/user")
public class UserController {

    private final SignupUseCase signupUseCase;

    private final LoginUseCase loginUseCase;

    @Inject
    public UserController(SignupUseCase signupUseCase, LoginUseCase loginUseCase) {
        this.signupUseCase = signupUseCase;
        this.loginUseCase = loginUseCase;
    }

    @Post("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SapatiAshwinResponse<Object>> signupUser(@Body SignupPayload signupPayload) throws IOException, URISyntaxException, InterruptedException {
        SignupUseCaseRequest signupUseCaseRequest = UserConverter.toSignupUseCaseRequest(signupPayload);
        Optional<SignupUseCaseResponse> signupUseCaseResponse = signupUseCase.execute(signupUseCaseRequest);
        if (signupUseCaseResponse.isPresent()) {
            return HttpResponse.ok().body(new SapatiAshwinResponse<>().success(signupUseCaseResponse.get()));
        } else throw new SapatiAshwinException(SapatiAshwinExceptionType.RESPONSE_EMPTY);
    }

    @Post("login")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<SapatiAshwinResponse<Object>> loginUser(@Body LoginPayload loginPayload) {
        LoginUseCaseRequest loginUseCaseRequest = UserConverter.toLoginUseCaseRequest(loginPayload);
        Optional<LoginUseCaseResponse> loginUseCaseResponse = loginUseCase.execute(loginUseCaseRequest);
        if (loginUseCaseResponse.isPresent()) {
            return HttpResponse.ok().body(new SapatiAshwinResponse<>().success(loginUseCaseResponse.get()));
        } else throw new SapatiAshwinException(SapatiAshwinExceptionType.RESPONSE_EMPTY);
    }
}
