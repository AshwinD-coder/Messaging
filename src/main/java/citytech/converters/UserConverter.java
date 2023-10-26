package citytech.converters;

import citytech.controllers.payloads.LoginPayload;
import citytech.controllers.payloads.SignupPayload;
import citytech.repositories.user.UserEntity;
import citytech.usecases.login.LoginUseCaseRequest;
import citytech.usecases.messaging.MessagingUseCaseRequest;
import citytech.usecases.signup.SignupUseCaseRequest;

public class UserConverter {
    private UserConverter() {
    }

    public static SignupUseCaseRequest toSignupUseCaseRequest(SignupPayload signupPayload) {
        return new SignupUseCaseRequest(signupPayload.username(), signupPayload.email(), signupPayload.phoneNumber());
    }

    public static UserEntity toUserEntity(SignupUseCaseRequest signupUseCaseRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signupUseCaseRequest.username());
        userEntity.setEmail(signupUseCaseRequest.email());
        userEntity.setPhoneNumber(signupUseCaseRequest.phoneNumber());
        return userEntity;
    }

    public static LoginUseCaseRequest toLoginUseCaseRequest(LoginPayload loginPayload) {
        return new LoginUseCaseRequest(loginPayload.username(), loginPayload.password());
    }

    public static MessagingUseCaseRequest toMessagingUseCaseRequest(UserEntity userEntity) {
        return new MessagingUseCaseRequest(userEntity.getEmail(), userEntity.getUsername(), userEntity.getPassword());
    }

}
