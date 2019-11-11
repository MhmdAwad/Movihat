package com.mhmdawad.movihat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity  {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference movieRef = db.collection("movies");
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Query query = movieRef.orderBy("movie_name", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Movie> options = new FirestoreRecyclerOptions.Builder<Movie>()
                .setQuery(query , Movie.class)
                .build();

        adapter = new MovieAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            Movie movie = documentSnapshot.toObject(Movie.class);
            Intent intent = new Intent(MainActivity.this,MovieDetails.class);
            assert movie != null;
            intent.putExtra("movieName",movie.getMovie_name());
            intent.putExtra("movieCover",movie.getMovie_cover());
            intent.putExtra("movieLink",movie.getMovie_link());
            startActivity(intent);
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
