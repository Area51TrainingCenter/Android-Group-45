package pe.area51.githubsearcher;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import pe.area51.githubsearcher.data.room_retrofit.retrofit.RetrofitGitHubClient;
import pe.area51.githubsearcher.data.room_retrofit.retrofit.RetrofitGitHubClientBuilder;
import pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RetrofitGitHubClientTest {

    private RetrofitGitHubClient retrofitGitHubClient;

    @Before
    public void prepare() {
        retrofitGitHubClient = new RetrofitGitHubClientBuilder().build();
    }

    @Test
    public void findProjectsByName() {
        testListResult(retrofitGitHubClient.findProjectsByName("retrofit"));
    }

    @Test
    public void findProjectsByUserName() {
        testListResult(retrofitGitHubClient.findProjectsByUserName("josemgu91"));
    }

    @Test
    public void findProjectsById() {
        testResult(retrofitGitHubClient.findProjectById("141762265"));
    }


    private void testListResult(final Call<List<GitHubProject>> projects) {
        try {
            final Response<List<GitHubProject>> response = projects.execute();
            final String raw = response.raw().toString();
            System.out.println(raw);
            final List<GitHubProject> gitHubProjects = response.body();
            Assert.assertNotNull(gitHubProjects);
            System.out.println(gitHubProjects);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("IOException");
        }
    }

    private void testResult(final Call<GitHubProject> projects) {
        try {
            final Response<GitHubProject> response = projects.execute();
            final String raw = response.raw().toString();
            System.out.println(raw);
            final GitHubProject gitHubProject = response.body();
            Assert.assertNotNull(gitHubProject);
            System.out.println(gitHubProject);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("IOException");
        }
    }


}