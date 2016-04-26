package marcos_buenacasa_lpez.rgbphisicstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zealcharm on 4/16/16.
 */
public class GameRatingActivity extends Activity {
    public static String LEVEL_SET_KEY = "level_set"; // To send the level from the Intent
    public static String NUM_PLAYERS_KEY = "num_players"; // To send the level from the Intent
    public static String PLAYING_TIME_KEY = "playing_time"; // To send the level from the Intent
    public static String NUM_TURNS_KEY = "num_turns"; // To send the level from the Intent

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setText("RATING SCREEN STUB. TIME="+getIntent().getIntExtra(PLAYING_TIME_KEY,-1)+",NUM_TURNS="+getIntent().getIntExtra(NUM_TURNS_KEY,-1));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameRatingActivity.this.finish();
                Intent i = new Intent(GameRatingActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        setContentView(button);
    }
}
