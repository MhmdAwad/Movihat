package com.mhmdawad.movihat;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {

    MediaController mediaController;
    @BindView(R.id.dTxtMovieName)
    TextView dTxtMovieName;
    @BindView(R.id.dImgMovieBackground)
    ImageView dImgMovieBackground;
    @BindView(R.id.dImgMovieCover)
    ImageView dImgMovieCover;
    @BindView(R.id.dBtnMovieLink)
    Button dBtnMovieLink;
    @BindView(R.id.dVideoView)
    VideoView dVideoView;
    @BindView(R.id.dImgPlayMovie)
    ImageView dImgPlayMovie;
//    @BindView(R.id.progressBar)
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        final String movieName = bundle.getString("movieName");
        final String movieCover = bundle.getString("movieCover");
        final String movieLink = bundle.getString("movieLink");

        dTxtMovieName.setText(movieName);
        Picasso.get().load(movieCover).into(dImgMovieCover);
        dImgPlayMovie.setOnClickListener(v -> playVideo(movieLink));

        dBtnMovieLink.setOnClickListener(v -> startDownload(movieLink, movieName));

    }

    public void playVideo(String videoPath) {

        dImgPlayMovie.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);

        try {
            mediaController = new MediaController(this);
            mediaController.setAnchorView(dVideoView);
            Uri video = Uri.parse(videoPath);
            dVideoView.setMediaController(mediaController);
            dVideoView.setVideoURI(video);

            dVideoView.setOnPreparedListener(mp -> {
//                progressBar.setVisibility(View.GONE);
                dVideoView.start();


            });

        } catch (Exception e) {
//            progressBar.setVisibility(View.GONE);
            System.out.println("Video Play Error :" + e.getMessage());
        }
    }

    public void startDownload(String videoPath, String videoName) {
        DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(videoPath);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(videoName);
        request.setDescription("Downloading...");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, videoName);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadmanager.enqueue(request);
    }






}
