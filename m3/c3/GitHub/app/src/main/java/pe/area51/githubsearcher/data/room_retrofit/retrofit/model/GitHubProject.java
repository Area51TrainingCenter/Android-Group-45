package pe.area51.githubsearcher.data.room_retrofit.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class GitHubProject {

    @SerializedName("id")
    public final long id;
    @SerializedName("name")
    public final String name;
    @SerializedName("description")
    public final String description;
    @SerializedName("html_url")
    public final String webUrl;
    @SerializedName("owner")
    public final User user;
    @SerializedName("created_at")
    public final String creationDate;
    @SerializedName("updated_at")
    public final String lastUpdateTimestamp;
    @SerializedName("stargazers_count")
    public final int popularity;

    public GitHubProject(long id, String name, String description, String webUrl, User user, String creationDate, String lastUpdateTimestamp, int popularity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.webUrl = webUrl;
        this.user = user;
        this.creationDate = creationDate;
        this.lastUpdateTimestamp = lastUpdateTimestamp;
        this.popularity = popularity;
    }

    public static class User {

        @SerializedName("id")
        public final long id;
        @SerializedName("login")
        public final String userName;

        public User(long id, String userName) {
            this.id = id;
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GitHubProject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", user=" + user +
                ", creationDate='" + creationDate + '\'' +
                ", lastUpdateTimestamp='" + lastUpdateTimestamp + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
