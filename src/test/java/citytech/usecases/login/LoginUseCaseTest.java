package citytech.usecases.login;

import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class LoginUseCaseTest {
    @Inject
    LoginUseCase loginUseCase;

    @Test
    void shouldLogin() {
        try {
            LoginUseCaseRequest loginUseCaseRequest = new LoginUseCaseRequest(
                    "ashwin", "8hJfDzA5");
            var result = loginUseCase.execute(loginUseCaseRequest);
            if(result.isPresent()) {
                LoginUseCaseResponse loginUseCaseResponse = new LoginUseCaseResponse(
                        result.get().token(), "Logged In!"
                );
                Assertions.assertEquals(loginUseCaseResponse, result.get());
            }
            else{
                throw new SapatiAshwinException(SapatiAshwinExceptionType.RESPONSE_EMPTY);
            }
        } catch (SapatiAshwinException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionForUsernameNotFound() {
        LoginUseCaseRequest loginUseCaseRequest = new LoginUseCaseRequest(
                "ashwin123",
                "aeUHX1@C"
        );
        SapatiAshwinException exception = Assertions.assertThrows(SapatiAshwinException.class, () -> {
            loginUseCase.execute(loginUseCaseRequest);
        });
        assertEquals(SapatiAshwinExceptionType.USER_ENTITY_EMPTY.getMessage(), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForIncorrectPassword() {
        LoginUseCaseRequest loginUseCaseRequest = new LoginUseCaseRequest(
                "ashwin",
                "aeUHX1@C"
        );
        SapatiAshwinException exception = Assertions.assertThrows(SapatiAshwinException.class, () -> {
            loginUseCase.execute(loginUseCaseRequest);
        });
        assertEquals(SapatiAshwinExceptionType.PASSWORD_INCORRECT.getMessage(), exception.getMessage());

    }

    @Test
    void shouldThrowExceptionForBlankOrEmptyUsername(){
        LoginUseCaseRequest loginUseCaseRequest = new LoginUseCaseRequest(
          "  ",
          "test"
        );
         SapatiAshwinException exception = Assertions.assertThrows(SapatiAshwinException.class,
                ()->loginUseCase.execute(loginUseCaseRequest));
         Assertions.assertEquals(SapatiAshwinExceptionType.USERNAME_EMPTY_OR_BLANK.getMessage(),exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForBlankOrEmptyPassword(){
        LoginUseCaseRequest loginUseCaseRequest = new LoginUseCaseRequest(
          "ashwin",
          ""
        );
        SapatiAshwinException exception = Assertions.assertThrows(SapatiAshwinException.class,()->loginUseCase.execute(loginUseCaseRequest));
        Assertions.assertEquals(SapatiAshwinExceptionType.PASSWORD_EMPTY_OR_BLANK.getMessage(),exception.getMessage());
    }
}
