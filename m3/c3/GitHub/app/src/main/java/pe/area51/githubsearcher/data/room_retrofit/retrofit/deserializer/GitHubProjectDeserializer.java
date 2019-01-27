package pe.area51.githubsearcher.data.room_retrofit.retrofit.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

import pe.area51.githubsearcher.data.room_retrofit.retrofit.model.GitHubProject;

public class GitHubProjectDeserializer implements JsonDeserializer<List<GitHubProject>> {

    private final static String ITEMS_TAG = "items";
    private final Gson gson;

    public GitHubProjectDeserializer(Gson defaultDeserialization) {
        this.gson = defaultDeserialization;
    }

    @Override
    public List<GitHubProject> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final boolean isJsonObject = json.isJsonObject();
        if (!isJsonObject) {
            return gson.fromJson(json.getAsJsonArray(), typeOfT);
        }
        final JsonObject jsonObject = json.getAsJsonObject();
        final boolean hasItems = jsonObject.has(ITEMS_TAG);
        if (hasItems) {
            return gson.fromJson(jsonObject.getAsJsonArray(ITEMS_TAG), typeOfT);
        }
        throw new JsonParseException("Unknown JSON format!");
    }

}
