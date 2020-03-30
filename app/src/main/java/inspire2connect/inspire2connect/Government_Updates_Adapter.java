package inspire2connect.inspire2connect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Government_Updates_Adapter extends RecyclerView.Adapter<Government_Updates_Adapter.MyViewHolder> {
    private ArrayList<Boolean> play_pause_list = new ArrayList<Boolean>();
    private ArrayList<custom_media_Class> media_player_list = new ArrayList<>();
    private ArrayList<daily_update_single_object> List;
    private static MyClickListener myClickListener;

    public ArrayList<custom_media_Class> getMedia_player_list() {
        return media_player_list;
    }

    Float X, Y;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ConstraintLayout constraintLayout;
        public ImageView play_pause;
        private LinearLayout linearLayout;

        //public CardView guideline_cv;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.gov_updates_view);
            title.setMovementMethod(LinkMovementMethod.getInstance());
            play_pause = (ImageView) view.findViewById(R.id.play_pause_gov_update);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraint_gov_updates);
            //linearLayout=(LinearLayout)itemView.findViewById(R.id.Linear_layout);
        }
    }


    public Government_Updates_Adapter(ArrayList<daily_update_single_object> List) {
        this.List = List;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.government_update_single, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        daily_update_single_object movie = List.get(position);
        holder.title.setText(movie.getDaily_update());
        holder.play_pause.setBackgroundResource(R.drawable.play_icon);
        play_pause_list.add(false);
        media_player_list.add(new custom_media_Class(null, true));
        holder.play_pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("Testing", "Card" + Integer.toString(position) + "clicked");
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
//                        Log.d("On_Click_DOWN",Float.toString(motionEvent.getX())+" "+Float.toString(motionEvent.getY()));
//                        X = motionEvent.getX();
//                        Y = motionEvent.getY();
//                        if (motionEvent.getX() > 820.0)
//                        {
                        for (int i = 0; i < media_player_list.size(); i++) {
                            if (i != position) {
                                MediaPlayer temp = media_player_list.get(i).getMediaPlayer();
                                if (temp != null && temp.isPlaying()) {
                                    temp.seekTo(temp.getDuration());
                                }
                            }
                        }
                        Log.d("Testing", "Button Touched");
                        if (media_player_list.get(position).getPaused()) {
                            holder.play_pause.setImageDrawable(null);
                            holder.play_pause.setBackgroundResource(R.drawable.pause_icon);
                            //media_player_list.get(position).setPaused(false);
                            MediaPlayer temp = media_player_list.get(position).getMediaPlayer();
                            try {
                                if (false) {
                                } else {
                                    if (temp == null) {
                                        temp = new MediaPlayer();
                                        media_player_list.get(position).setMediaPlayer(temp);
                                        Log.d("Testing", "Step1" + List.get(position).getAudio_url());
                                        temp.setDataSource(List.get(position).getAudio_url());
                                        temp.prepare();
                                        temp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mediaPlayer) {
                                                mediaPlayer.start();
                                                media_player_list.get(position).setPaused(false);
                                            }
                                        });
                                        temp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                            @Override
                                            public void onCompletion(MediaPlayer mediaPlayer) {
                                                Log.d("Testing", "Media Player finished");
                                                media_player_list.get(position).setPaused(true);
                                                holder.play_pause.setBackgroundResource(R.drawable.play_icon);
                                                mediaPlayer = null;
                                            }
                                        });
                                    } else {
                                        Log.d("Testing", "In here not null");
                                        if (media_player_list.get(position).getPaused()) {
                                            temp.seekTo(media_player_list.get(position).getCurrent_time());
                                            temp.start();
                                            media_player_list.get(position).setPaused(false);
                                        } else {
                                            temp.setDataSource(List.get(position).getAudio_url());
                                            temp.prepare();
                                            temp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                                @Override
                                                public void onPrepared(MediaPlayer mediaPlayer) {
                                                    mediaPlayer.start();
                                                    media_player_list.get(position).setPaused(false);
                                                }
                                            });
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("Testing", "Exception", e);
                                e.printStackTrace();
                            }
                        } else {
                            holder.play_pause.setBackgroundResource(R.drawable.play_icon);
                            //play_pause_list.set(position,false);
                            //media_player_list.get(position).setPaused(true);
                            MediaPlayer temp = media_player_list.get(position).getMediaPlayer();
                            if (temp != null) {
                                if (temp.isPlaying()) {
                                    temp.pause();
                                    Log.d("Testing", "Step2");
                                    media_player_list.get(position).setPaused(true);
                                    media_player_list.get(position).setCurrent_time(temp.getCurrentPosition());
                                }
                            }
                        }
//                        }
                    }
                }
                return false;
            }

        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}