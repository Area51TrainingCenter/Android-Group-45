package pe.area51.githubsearcher.data.room_retrofit.retrofit;

import java.util.List;

import pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitGitHubClient {

    /***
     * @See: <a href="https://developer.github.com/v3/search/#search-repositories">GitHub REST API v3.</a>
     */
    @GET("search/repositories?sort=stars")
    Call<List<GitHubProject>> findProjectsByName(@Query("q") final String projectName);

    /***
     * @See: <a href="https://developer.github.com/v3/repos/#list-user-repositories">GitHub REST API v3.</a>
     */
    @GET("users/{userName}/repos")
    Call<List<GitHubProject>> findProjectsByUserName(@Path("userName") final String userName);

    /***
     * @See: <a href="https://github.com/octokit/rest.js/issues/163">Get repo by ID #163.</a>
     */
    @GET("repositories/{repositoryId}")
    Call<GitHubProject> findProjectById(@Path("repositoryId") final String repositoryId);

}
