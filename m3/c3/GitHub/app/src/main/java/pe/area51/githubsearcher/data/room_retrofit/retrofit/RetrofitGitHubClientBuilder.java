package pe.area51.githubsearcher.data.room_retrofit.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.List;

import pe.area51.githubsearcher.data.room_retrofit.retrofit.deserializer.GitHubProjectDeserializer;
import pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGitHubClientBuilder {

    private final static String BASE_URL = "https://api.github.com/";

    public RetrofitGitHubClient build() {
        final Gson defaultGson = new Gson();
        final JsonDeserializer<List<GitHubProject>> githubProjectJsonDeserializer = new GitHubProjectDeserializer(defaultGson);
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(List.class, githubProjectJsonDeserializer);
        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(RetrofitGitHubClient.class);
    }
}
