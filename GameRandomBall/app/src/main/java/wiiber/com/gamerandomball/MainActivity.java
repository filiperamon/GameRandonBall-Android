package wiiber.com.gamerandomball;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends Activity {

    private Switch switchRest;
    private TextView textViewNumberRandom, tvSum;
    private Button tvNumOne, tvNumTwo, tvNumTree, tvNumFour, tvNumFive, tvNumSix ;
    private ImageButton btnVolume;
    public Random random;
    private int count = 0;
    private int player = 0;
    private int[] results;
    private int soma, volume;
    private MediaPlayer audeoRoleta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNumberRandom = (TextView) findViewById(R.id.txvNumeroRandomico);
        tvNumOne     = (Button) findViewById(R.id.textViewNumOne);
        tvNumTwo     = (Button) findViewById(R.id.textViewNumTwo);
        tvNumTree    = (Button) findViewById(R.id.textViewNumTree);
        tvNumFour    = (Button) findViewById(R.id.textViewNumFour);
        tvNumFive    = (Button) findViewById(R.id.textViewNumFive);
        tvNumSix     = (Button) findViewById(R.id.textViewNumSix);
        btnVolume = (ImageButton) findViewById(R.id.volume);
        tvSum = (TextView) findViewById(R.id.textViewSoma);
        switchRest = (Switch) findViewById(R.id.switchGerar);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/postmaster.ttf");
        tvSum.setTypeface(font);
        textViewNumberRandom.setTypeface(font);
        tvNumOne.setTypeface(font);
        tvNumTwo.setTypeface(font);
        tvNumTree.setTypeface(font);
        tvNumFour.setTypeface(font);
        tvNumFive.setTypeface(font);
        tvNumSix.setTypeface(font);
        switchRest.setTypeface(font);

        audeoRoleta = MediaPlayer.create(this,R.raw.roleta);
        random = new Random();
        textViewNumberRandom.setText("?");
        results = new int[6];
        soma = 0;
        volume = 1;

        switchRest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    resetNumbers();
                    switchRest.setChecked(false);
                }
            }
        });

        textViewNumberRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    if (player <= 6) {
                        refreshView();
                    } else {
                        player = 0;
                        soma = 0;
                    }
                }
            }
        });

        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (volume == 1) {
                    btnVolume.setImageResource(R.drawable.volume_off);
                    volume = 0;
                } else {
                    btnVolume.setImageResource(R.drawable.volume_high);
                    volume = 1;
                }
            }
        });
   }

    private void efeitoSonoroRoleta(){
        if(volume==1)
            audeoRoleta.start();
    }

    private void refreshView(){
        final int numberRandom = random.nextInt(60) + 1;
        textViewNumberRandom.setText(String.valueOf(numberRandom));
        count ++;

        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count < 10) {
                    efeitoSonoroRoleta();
                    animationRoleta(textViewNumberRandom);
                    refreshView();
                } else {
                    count = 0;
                    player++;
                    textViewNumberRandom.setText(String.valueOf(numberRandom));

                    if(player == 6){
                        textViewNumberRandom.setClickable(false);
                    }

                    switch (player) {
                        case 1:
                            animationResults(tvNumOne);
                            results[0] = Integer.valueOf(textViewNumberRandom.getText().toString());
                            animationButton(tvNumOne, textViewNumberRandom.getText().toString());
                            break;
                        case 2:
                            animationResults(tvNumTwo);
                            results[0] = Integer.valueOf(textViewNumberRandom.getText().toString());
                            animationButton(tvNumTwo, textViewNumberRandom.getText().toString());
                            break;
                        case 3:
                            animationResults(tvNumTree);
                            results[0] = Integer.valueOf(textViewNumberRandom.getText().toString());
                            animationButton(tvNumTree, textViewNumberRandom.getText().toString());
                            break;
                        case 4:
                            animationResults(tvNumFour);
                            results[0] = Integer.valueOf(textViewNumberRandom.getText().toString());
                            animationButton(tvNumFour, textViewNumberRandom.getText().toString());
                            break;
                        case 5:
                            animationResults(tvNumFive);
                            results[0] = Integer.valueOf(textViewNumberRandom.getText().toString());
                            animationButton(tvNumFive, textViewNumberRandom.getText().toString());
                            break;
                        case 6:
                            animationResults(tvNumSix);
                            results[0] = Integer.valueOf(textViewNumberRandom.getText().toString());
                            animationButton(tvNumSix, textViewNumberRandom.getText().toString());
                            player = 0;
                            break;
                    }

                    for(int i = 0; i < results.length; i++){
                        soma += results[i];
                    }
                    tvSum.setText(getString(R.string.soma) + ": " + soma);
                }
            }
        }, 60);
    }

    private void animationResults(View view) {
        Animation animator = AnimationUtils.loadAnimation(this, R.anim.from_middle);
        view.setAnimation(animator);
        view.startAnimation(animator);
    }

    private void animationRoleta(View view) {
        Animation animator = AnimationUtils.loadAnimation(this, R.anim.from_middle2);
        view.setAnimation(animator);
        view.startAnimation(animator);
    }

    public void animationButton(final TextView view, final String numero){
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setText(numero);
            }
        }, 300);
    }

    private void resetNumbers(){

        animationResults(tvNumOne);
        animationButton(tvNumOne, "");
        animationResults(tvNumTwo);
        animationButton(tvNumTwo, "");
        animationResults(tvNumTree);
        animationButton(tvNumTree, "");
        animationResults(tvNumFour);
        animationButton(tvNumFour, "");
        animationResults(tvNumFive);
        animationButton(tvNumFive, "");
        animationResults(tvNumSix);
        animationButton(tvNumSix, "");

        animationRoleta(textViewNumberRandom);
        animationButton(textViewNumberRandom, "?");
        textViewNumberRandom.setClickable(true);

        count = 0;
        soma = 0;
        tvSum.setText(getString(R.string.soma) + ": " + soma);
        results = new int[6];
        player = 0;
    }
}
