package citytech.platforms.usecase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;

public interface UseCase<I extends UseCaseRequest, O extends UseCaseResponse> {
    Optional<O> execute(I request) throws IOException, URISyntaxException, InterruptedException, SQLException;
}
