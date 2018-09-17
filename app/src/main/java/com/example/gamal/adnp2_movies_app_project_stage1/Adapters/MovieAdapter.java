package com.example.gamal.adnp2_movies_app_project_stage1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamal.adnp2_movies_app_project_stage1.Models.Movie;
import com.example.gamal.adnp2_movies_app_project_stage1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private List<Movie> mMoviesData;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private final OnItemClickListener onItemClickListener;

    public MovieAdapter(List<Movie> mMoviesData, Context context, OnItemClickListener listener) {
        this.mMoviesData = mMoviesData;
        this.context = context;
        onItemClickListener = listener;
    }


    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_list_item_movie_card, parent, false);
        MovieAdapterViewHolder viewHolder = new MovieAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        holder.movieTitle.setText(mMoviesData.get(position).getTitle());
        holder.movieCategory.setText(mMoviesData.get(position).getCategory());
        holder.movieVoteAverage.setText(mMoviesData.get(position).getVoteAvg());

        Picasso.with(context)
                .load(mMoviesData.get(position).getMoviePoster())
                .error(R.drawable.error_404)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        if (mMoviesData == null) {
            return 0;
        } else {
            return mMoviesData.size();
        }
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView movieImage;
        public TextView movieTitle;
        public TextView movieCategory;
        public TextView movieVoteAverage;

        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImage);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieCategory = itemView.findViewById(R.id.movieCategory);
            movieVoteAverage = itemView.findViewById(R.id.movieVoteAverage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            onItemClickListener.onItemClick(clickedPosition);
        }
    }

}
