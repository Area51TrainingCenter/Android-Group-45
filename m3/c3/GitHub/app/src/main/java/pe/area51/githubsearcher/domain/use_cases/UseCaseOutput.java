package pe.area51.githubsearcher.domain.use_cases;

import android.support.annotation.NonNull;

public interface UseCaseOutput<Success, Error> {

    void onProgress();

    void onSuccess(@NonNull Success success);

    void onError(@NonNull Error error);

}
