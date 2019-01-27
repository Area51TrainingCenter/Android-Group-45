package pe.area51.githubsearcher.domain.use_cases;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;

import pe.area51.githubsearcher.domain.DataGatewayException;
import pe.area51.githubsearcher.domain.GitHubProject;
import pe.area51.githubsearcher.domain.GitHubProjectDataGateway;

public class SearchUseCase extends AbstractUseCase<SearchUseCase.Input, List<GitHubProject>, QueryUseCaseError> {

    @NonNull
    private final GitHubProjectDataGateway gitHubProjectDataGateway;

    public SearchUseCase(@NonNull final Executor useCaseExecutor, @NonNull final Executor outputExecutor, @NonNull final GitHubProjectDataGateway gitHubProjectDataGateway) {
        super(useCaseExecutor, outputExecutor);
        this.gitHubProjectDataGateway = gitHubProjectDataGateway;
    }

    @Override
    void onExecute(@NonNull Input input, @NonNull UseCaseOutput<List<GitHubProject>, QueryUseCaseError> useCaseOutput) {
        useCaseOutput.onProgress();
        final List<GitHubProject> successOutput;
        try {
            if (input.type == Input.Type.BY_USER_NAME) {
                successOutput = gitHubProjectDataGateway.findProjectsByUserName(input.textToSearch);
            } else {
                successOutput = gitHubProjectDataGateway.findProjectsByProjectName(input.textToSearch);
            }
            useCaseOutput.onSuccess(successOutput);
        } catch (DataGatewayException e) {
            e.printStackTrace();
            useCaseOutput.onError(QueryUseCaseError.IO_ERROR);
        }
    }

    public static final class Input {
        @NonNull
        private final Type type;
        @NonNull
        private final String textToSearch;

        public Input(@NonNull Type type, @NonNull String textToSearch) {
            this.type = type;
            this.textToSearch = textToSearch;
        }

        public enum Type {
            BY_USER_NAME,
            BY_REPOSITORY_NAME
        }

    }

}
