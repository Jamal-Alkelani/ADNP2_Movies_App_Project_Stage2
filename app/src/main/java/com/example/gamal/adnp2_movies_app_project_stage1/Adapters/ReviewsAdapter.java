package com.example.gamal.adnp2_movies_app_project_stage1.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.gamal.adnp2_movies_app_project_stage1.Models.Review;
import com.example.gamal.adnp2_movies_app_project_stage1.R;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MovieReviewAdapterViewHolder> {
    List<Review> mReviewsData;

    public ReviewsAdapter(List<Review> mReviewsData) {
        this.mReviewsData = mReviewsData;
    }

    @NonNull
    @Override
    public ReviewsAdapter.MovieReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_list_item_review, parent, false);
        MovieReviewAdapterViewHolder viewHolder = new MovieReviewAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.MovieReviewAdapterViewHolder holder, int i) {
        holder.review.setText(mReviewsData.get(i).getAuthor());
        holder.author.setText(mReviewsData.get(i).getContent().toString().substring(0, 60));
        holder.readMore.setText("Read More");
    }

    @Override
    public int getItemCount() {
        return mReviewsData.size();
    }

    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView author;
        public TextView review;
        public TextView readMore;

        public MovieReviewAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_author);
            review = itemView.findViewById(R.id.tv_review);
            readMore = itemView.findViewById(R.id.tv_read_more);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
