package pe.area51.githubsearcher.android.ui.search;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TooltipCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pe.area51.githubsearcher.R;
import pe.area51.githubsearcher.databinding.ElementGithubProjectBinding;
import pe.area51.githubsearcher.domain.GitHubProject;

public class GitHubProjectAdapter extends RecyclerView.Adapter<pe.area51.githubsearcher.android.ui.search.GitHubProjectAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<GitHubProject> gitHubProjects;
    private final OnItemClickListener onItemClickListener;
    private final OnFavoriteClickListener onFavoriteClickListener;

    public GitHubProjectAdapter(LayoutInflater layoutInflater,
                                OnItemClickListener onItemClickListener,
                                OnFavoriteClickListener onFavoriteClickListener) {
        this.layoutInflater = layoutInflater;
        this.gitHubProjects = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
        this.onFavoriteClickListener = onFavoriteClickListener;
    }

    public final void replaceElements(@NonNull List<GitHubProject> gitHubProjects) {
        this.gitHubProjects.clear();
        this.gitHubProjects.addAll(gitHubProjects);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ElementGithubProjectBinding elementGithubProjectBinding =
                ElementGithubProjectBinding.inflate(
                        layoutInflater,
                        parent,
                        false);
        return new ViewHolder(elementGithubProjectBinding, onItemClickListener, (position, gitHubProject) -> {
            final int index = gitHubProjects.indexOf(gitHubProject);
            final GitHubProject newGitHubProject = new GitHubProject(
                    gitHubProject.getId(),
                    gitHubProject.getName(),
                    gitHubProject.getDescription(),
                    gitHubProject.getWebUrl(),
                    gitHubProject.getUserName(),
                    gitHubProject.getCreationTimestamp(),
                    gitHubProject.getLastUpdateTimestamp(),
                    gitHubProject.getPopularity(),
                    !gitHubProject.isFavorite(),
                    gitHubProject.getFavoriteTimestamp()
            );
            gitHubProjects.set(index, newGitHubProject);
            notifyItemChanged(position);
            onFavoriteClickListener.onFavoriteClick(newGitHubProject);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GitHubProject gitHubProject = gitHubProjects.get(position);
        final Context context = holder.elementGithubProjectBinding.getRoot().getContext();
        holder.elementGithubProjectBinding.textViewProjectName.setText(gitHubProject.getName());
        holder.elementGithubProjectBinding.textViewProjectDescription.setText(gitHubProject.getDescription());
        final String favText = context.getString(
                gitHubProject.isFavorite() ? R.string.element_unmark_favorite : R.string.element_mark_favorite
        );
        final Drawable favDrawable = ContextCompat.getDrawable(
                context,
                gitHubProject.isFavorite() ? R.drawable.ic_action_fav : R.drawable.ic_action_unfav
        );
        TooltipCompat.setTooltipText(
                holder.elementGithubProjectBinding.imageViewFavorite, favText
        );
        holder.elementGithubProjectBinding.imageViewFavorite.setContentDescription(favText);
        holder.elementGithubProjectBinding.imageViewFavorite.setImageDrawable(favDrawable);
        holder.elementGithubProjectBinding.textViewPopularity.setText(
                String.format(Locale.getDefault(), "%d", gitHubProject.getPopularity())
        );
        holder.elementGithubProjectBinding.textViewCreation.setText(
                context.getString(R.string.element_created, formatDate(gitHubProject.getCreationTimestamp()))
        );
        holder.elementGithubProjectBinding.textViewLastUpdate.setText(
                context.getString(R.string.element_last_update, formatDate(gitHubProject.getLastUpdateTimestamp()))
        );
        holder.gitHubProject = gitHubProject;
    }

    @Override
    public int getItemCount() {
        return gitHubProjects.size();
    }

    public final static class ViewHolder extends RecyclerView.ViewHolder {

        private final ElementGithubProjectBinding elementGithubProjectBinding;
        private GitHubProject gitHubProject;

        public ViewHolder(final ElementGithubProjectBinding elementGithubProjectBinding,
                          final OnItemClickListener onItemClickListener,
                          final InternalOnFavoriteClickListener internalOnFavoriteClickListener) {
            super(elementGithubProjectBinding.getRoot());
            this.elementGithubProjectBinding = elementGithubProjectBinding;
            this.elementGithubProjectBinding.getRoot().setOnClickListener(
                    v -> onItemClickListener.onItemClick(gitHubProject)
            );
            this.elementGithubProjectBinding.imageViewFavorite.setOnClickListener(
                    v -> internalOnFavoriteClickListener.onFavoriteClick(getAdapterPosition(), gitHubProject)
            );
        }
    }

    private String formatDate(final long timeStamp) {
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        return dateFormat.format(new Date(timeStamp));
    }

    public interface OnItemClickListener {

        void onItemClick(final GitHubProject gitHubProject);
    }

    public interface OnFavoriteClickListener {

        void onFavoriteClick(final GitHubProject gitHubProject);
    }

    private interface InternalOnFavoriteClickListener {

        void onFavoriteClick(final int position, final GitHubProject gitHubProject);
    }

}