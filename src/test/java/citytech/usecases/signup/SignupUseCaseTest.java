package citytech.usecases.signup;

import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.repositories.user.UserRepository;
import citytech.usecases.messaging.MessagingUseCase;
import citytech.usecases.messaging.MessagingUseCaseResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class SignupUseCaseTest {

    private SignupUseCase signupUseCase;
    private UserRepository userRepository;
    private final MessagingUseCase messagingUseCase;


    @Inject
    public SignupUseCaseTest(SignupUseCase signupUseCase, UserRepository userRepository, MessagingUseCase messagingUseCase) {
        this.signupUseCase = signupUseCase;
        this.userRepository = userRepository;
        this.messagingUseCase = messagingUseCase;
    }


    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        signupUseCase = new SignupUseCase(userRepository, messagingUseCase);
    }

    @Test
    void shouldSignupUser() {
        try {
            SignupUseCaseRequest signupUseCaseRequest = new SignupUseCaseRequest(
                    "test",
                    "test@gmail.com",
                    "9861292963"
            );
            var result = signupUseCase.execute(signupUseCaseRequest);
            if (result.isPresent()) {
                SignupUseCaseResponse signupUseCaseResponse = SignupUseCaseResponseBuilder.builder()
                        .username("test")
                        .email("test@gmail.com")
                        .message("Please check your email for password!")
                        .messagingUseCaseResponse(new MessagingUseCaseResponse("EMAIL_SENT", "test@gmail.com"))
                        .build();
                Assertions.assertEquals(result.get(), signupUseCaseResponse);
            } else throw new SapatiAshwinException(SapatiAshwinExceptionType.RESPONSE_EMPTY);
        } catch (SapatiAshwinException | IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void shouldThrowExceptionForUsernameEmptyOrBlank() {
        SignupUseCaseRequest signupUseCaseRequest = new SignupUseCaseRequest(
                " ",
                "test@gmail.com",
                "9861292963"
        );
        SapatiAshwinException exception = Assertions.assertThrows(SapatiAshwinException.class, () -> {
            signupUseCase.execute(signupUseCaseRequest);
        });
        assertEquals(SapatiAshwinExceptionType.USERNAME_EMPTY_OR_BLANK.getMessage(), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmailInvalid() {
        SignupUseCaseRequest signupUseCaseRequest = new SignupUseCaseRequest(
                "test",
                "test@12gmail.com",
                "9861292963"
        );
        SapatiAshwinException exception = Assertions.assertThrows(SapatiAshwinException.class, () -> {
            signupUseCase.execute(signupUseCaseRequest);
        });
        assertEquals(SapatiAshwinExceptionType.EMAIL_INVALID.getMessage(), exception.getMessage());
    }
}
