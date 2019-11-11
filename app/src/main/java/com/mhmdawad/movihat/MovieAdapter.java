package com.mhmdawad.movihat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends FirestoreRecyclerAdapter<Movie , MovieAdapter.MovieHolder> {

    private OnItemClickListener listener;
    MovieAdapter(@NonNull FirestoreRecyclerOptions<Movie> options) {
        super(options);
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rv_items,parent,false);
        return new MovieHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull MovieHolder holder, int position, @NonNull Movie model) {
        Picasso.get().load(model.getMovie_cover()).into(holder.imageViewCover);
        holder.textViewMovie.setText(model.getMovie_name());
        Log.i("TAG", "cover " + model.getMovie_cover() + "name" + model.getMovie_name());
    }

    class MovieHolder extends RecyclerView.ViewHolder{
        ImageView imageViewCover;
        TextView  textViewMovie;

        MovieHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCover = itemView.findViewById(R.id.imgMovieCover);
            textViewMovie  = itemView.findViewById(R.id.dTxtMovieName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);

                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
