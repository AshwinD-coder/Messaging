package citytech.usecases.signup;

import citytech.converters.UserConverter;
import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.platforms.usecase.UseCase;
import citytech.platforms.utils.HelperUtils;
import citytech.repositories.user.UserEntity;
import citytech.repositories.user.UserRepository;
import citytech.usecases.messaging.MessagingUseCase;
import citytech.usecases.messaging.MessagingUseCaseRequest;
import citytech.usecases.messaging.MessagingUseCaseResponse;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public class SignupUseCase implements UseCase<SignupUseCaseRequest, SignupUseCaseResponse> {
    private final UserRepository userRepository;
    private final MessagingUseCase messagingUseCase;

    @Inject
    public SignupUseCase(UserRepository userRepository, MessagingUseCase messagingUseCase) {
        this.userRepository = userRepository;
        this.messagingUseCase = messagingUseCase;
    }

    @Override
    public Optional<SignupUseCaseResponse> execute(SignupUseCaseRequest request) throws IOException, URISyntaxException, InterruptedException {
        validateRequest(request);
        UserEntity userEntity = UserConverter.toUserEntity(request);
        userEntity.setPassword(HelperUtils.generateRandomPassword());
        this.userRepository.save(userEntity);
        writeToCsvFile(userEntity);
        MessagingUseCaseResponse messagingUseCaseResponse = sendEmail(userEntity);
        return Optional.of(SignupUseCaseResponseBuilder.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .message("Please check your email for password!")
                .messagingUseCaseResponse(messagingUseCaseResponse)
                .build());
    }

    private MessagingUseCaseResponse sendEmail(UserEntity userEntity) throws SapatiAshwinException, IOException, URISyntaxException, InterruptedException {
        MessagingUseCaseRequest messagingUseCaseRequest = UserConverter.toMessagingUseCaseRequest(userEntity);
        var response = messagingUseCase.execute(messagingUseCaseRequest);
        if (response.isPresent()) return response.get();
        else throw new SapatiAshwinException(SapatiAshwinExceptionType.RESPONSE_EMPTY);
    }

    private void writeToCsvFile(UserEntity userEntity) throws IOException {
        File file = new File("users.csv");
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper.schemaFor(UserEntity.class).withHeader();
            csvMapper.writerFor(UserEntity.class).with(csvSchema).writeValue(file, userEntity);

        } else {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper.schemaFor(UserEntity.class).withHeader();
            List<Object> existingData = csvMapper.readerFor(UserEntity.class)
                    .with(csvSchema)
                    .readValues(file)
                    .readAll();
            existingData.add(userEntity);
            csvMapper.writerFor(UserEntity.class)
                    .with(csvSchema)
                    .writeValues(file)
                    .writeAll(existingData);
        }
    }

    private void validateRequest(SignupUseCaseRequest request) throws SapatiAshwinException {
        validateUsername(request);
        validateEmail(request);
    }

    private void validateUsername(SignupUseCaseRequest request) throws SapatiAshwinException {
        Optional<UserEntity> userEntity = this.userRepository.findByUsername(request.username());
        if (userEntity.isEmpty()) {
            if (request.username().isBlank() || request.username().isEmpty()) {
                throw new SapatiAshwinException(SapatiAshwinExceptionType.USERNAME_EMPTY_OR_BLANK);
            }
        } else {
            throw new SapatiAshwinException(SapatiAshwinExceptionType.USERNAME_ALREADY_EXISTS);
        }
    }

    private void validateEmail(SignupUseCaseRequest request) throws SapatiAshwinException {
        boolean isValidEmail = HelperUtils.matchEmailPattern(request.email());
        Optional<UserEntity> userEntity = this.userRepository.findByEmail(request.email());
        if (!isValidEmail) {
            throw new SapatiAshwinException(SapatiAshwinExceptionType.EMAIL_INVALID);
        }
        if (userEntity.isPresent()) {
            throw new SapatiAshwinException(SapatiAshwinExceptionType.EMAIL_ALREADY_EXISTS);
        }
    }
}
