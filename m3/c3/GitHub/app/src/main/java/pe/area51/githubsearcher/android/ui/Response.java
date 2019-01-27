package pe.area51.githubsearcher.android.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Response<Success, Error> {

    @NonNull
    public final Status status;
    @Nullable
    public final Success successData;
    @Nullable
    public final Error errorData;

    public Response(@NonNull Status status, Success successData, Error errorData) {
        this.status = status;
        this.successData = successData;
        this.errorData = errorData;
    }

    public enum Status {
        IN_PROGRESS,
        SUCCESS,
        ERROR
    }

}
