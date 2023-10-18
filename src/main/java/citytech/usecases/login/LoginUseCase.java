package citytech.usecases.login;

import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.platforms.security.SecurityUtils;
import citytech.platforms.usecase.UseCase;
import citytech.repositories.user.UserRepository;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.Optional;

public class LoginUseCase implements UseCase<LoginUseCaseRequest, LoginUseCaseResponse> {

    private final UserRepository userRepository;

    @Value("${datasources.default.url}")
    private String url;

    @Value("${datasources.default.username}")
    private String username;

    @Value("${datasources.default.password}")
    private String password;

    @Inject
    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<LoginUseCaseResponse> execute(LoginUseCaseRequest request) throws SapatiAshwinException {
        validateRequest(request);
        String token = SecurityUtils.token(request);
        return Optional.of(
                new LoginUseCaseResponse(token, "Logged In!")
        );
    }

    private void validateRequest(LoginUseCaseRequest request) throws SapatiAshwinException {
        if (request.username().isEmpty() || request.username().isBlank()) {
            throw new SapatiAshwinException(SapatiAshwinExceptionType.USERNAME_EMPTY_OR_BLANK);
        }
        if (request.password().isEmpty() || request.password().isBlank()) {
            throw new SapatiAshwinException(SapatiAshwinExceptionType.PASSWORD_EMPTY_OR_BLANK);
        }
        validateUsernameAndPassword(request);
    }

    private void validateUsernameAndPassword(LoginUseCaseRequest request) {
        String query = String.format("SELECT * FROM users WHERE username = '%s';", request.username());
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            if (!resultSet.next()) {
                throw new SapatiAshwinException(SapatiAshwinExceptionType.USER_ENTITY_EMPTY);
            }
            if (!resultSet.getString("password").equals(request.password())) {
                throw new SapatiAshwinException(SapatiAshwinExceptionType.PASSWORD_INCORRECT);
            }
        } catch (SQLException e) {
            throw new SapatiAshwinException(SapatiAshwinExceptionType.DB_LOGIN_ERROR);
        }

    }
}
