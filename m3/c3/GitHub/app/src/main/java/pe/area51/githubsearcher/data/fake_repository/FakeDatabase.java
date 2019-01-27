package pe.area51.githubsearcher.data.fake_repository;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pe.area51.githubsearcher.domain.DataGatewayException;
import pe.area51.githubsearcher.domain.FavoriteDataGateway;
import pe.area51.githubsearcher.domain.GitHubProject;
import pe.area51.githubsearcher.domain.GitHubProjectDataGateway;

public class FakeDatabase implements FavoriteDataGateway, GitHubProjectDataGateway {

    private final List<GitHubProject> testGitHubProjects;
    private final List<GitHubProject> favoriteProjects;

    public String[] projectNames = new String[]{
            "Fake Library!",
            "HTTP Client",
            "Another Fake Library",
            "Mock Client",
            "Awesome Graph!",
            "Lite MOD Tracker"
    };

    public String[] projectDescriptions = new String[]{
            "An awesome software library.",
            "This is an awesome software project.",
            "The best project of all time!"
    };

    public String[] userNames = new String[]{
            "User1",
            "User2",
            "User3",
            "User4"
    };

    public String[] projectUrls = new String[]{
            "http://www.example.com"
    };

    public FakeDatabase(final int size) {
        testGitHubProjects = generateData(size);
        favoriteProjects = new ArrayList<>();
    }

    private List<GitHubProject> generateData(final int size) {
        final List<GitHubProject> testGitHubProjects = new ArrayList<>();
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            final int randomInt = (int) (1000 * random.nextFloat());
            final String id = String.valueOf(i + 1);
            final String name = projectNames[randomInt % projectNames.length];
            final String description = projectDescriptions[randomInt % projectDescriptions.length];
            final String webUrl = projectUrls[randomInt % projectUrls.length];
            final String userName = userNames[randomInt % userNames.length];
            final long creationTimestamp = System.currentTimeMillis();
            final long lastUpdateTimestamp = System.currentTimeMillis();
            final int popularity = 100;
            final boolean isFavorite = false;
            final long favoriteTimestamp = -1;
            testGitHubProjects.add(new GitHubProject(
                    id,
                    name,
                    description,
                    webUrl,
                    userName,
                    creationTimestamp,
                    lastUpdateTimestamp,
                    popularity,
                    isFavorite,
                    favoriteTimestamp
            ));
        }
        return testGitHubProjects;
    }

    @NonNull
    @Override
    public List<GitHubProject> getAllFavoriteProjects() throws DataGatewayException {
        return favoriteProjects;
    }

    @Override
    public boolean addFavorite(@NonNull String projectId) throws DataGatewayException {
        for (final GitHubProject gitHubProject : testGitHubProjects) {
            if (gitHubProject.getId().equals(projectId)) {
                favoriteProjects.add(gitHubProject);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFavorite(@NonNull String projectId) throws DataGatewayException {
        GitHubProject result = null;
        for (final GitHubProject gitHubProject : favoriteProjects) {
            if (gitHubProject.getId().equals(projectId)) {
                result = gitHubProject;
                break;
            }
        }
        if (result == null) {
            return false;
        }
        favoriteProjects.remove(result);
        return true;
    }

    @NonNull
    @Override
    public List<GitHubProject> findProjectsByProjectName(@NonNull String projectName) throws DataGatewayException {
        final List<GitHubProject> result = new ArrayList<>();
        for (final GitHubProject gitHubProject : testGitHubProjects) {
            if (compareStrings(gitHubProject.getName(), projectName)) {
                result.add(gitHubProject);
            }
        }
        return result;
    }

    @NonNull
    @Override
    public List<GitHubProject> findProjectsByUserName(@NonNull String userName) throws DataGatewayException {
        final List<GitHubProject> result = new ArrayList<>();
        for (final GitHubProject gitHubProject : testGitHubProjects) {
            if (compareStrings(gitHubProject.getUserName(), userName)) {
                result.add(gitHubProject);
            }
        }
        return result;
    }

    private boolean compareStrings(final String a, final String b) {
        return a.trim().toLowerCase().contains(b.trim().toLowerCase());
    }
}
