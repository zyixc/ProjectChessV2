package com.chessmaster.app.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.chessmaster.app.R;
import com.chessmaster.app.data.Game;

/**
 * GameProfileScreen ui Fragment
 * @author zyixc
 */
public class GameProfileScreen extends Fragment {
    private OnFragmentInteractionListener mListener;
    private static Game game;

    /**
     * Creates a new Instance 
     * @param linkedgame
     * @return Fragment
     * @see Fragment
     */
    public static GameProfileScreen newInstance(Game linkedgame) {
        GameProfileScreen fragment = new GameProfileScreen();
        game = linkedgame;
        return fragment;
    }
    
    /**
     * Empty constructor
     */
    public GameProfileScreen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_game_profile_screen, container, false);
        final TextView white = (TextView) view.findViewById(R.id.fGPS_White_Put_TextView);
        final TextView white_rating = (TextView) view.findViewById(R.id.fGPS_White_Rating_TextView);
        final TextView black = (TextView) view.findViewById(R.id.fGPS_Black_Put_TextView);
        final TextView black_rating = (TextView) view.findViewById(R.id.fGPS_Black_Rating_TextView);
        final TextView result = (TextView) view.findViewById(R.id.fGPS_Result_Put_TextView);
        final Button compare = (Button) view.findViewById(R.id.fGPS_Compare_Button);
        WebView webview = (WebView) view.findViewById(R.id.fGPS_WebView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        
        webview.loadUrl("file:///android_asset/chess/index.html?moves=\""+game.getMoves()+" "+game.getResult()+"\"");
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.fromGameProfileScreenTo(OnFragmentInteractionListener.GameProfileScreenOptions.COMPARESCREEN, game);
            }
        });

        white.setText(game.getWhite());
        white_rating.setText(Integer.toString(game.getWhite_elo()));
        black.setText(game.getBlack());
        black_rating.setText(Integer.toString(game.getBlack_elo()));
        result.setText(game.getResult());

        return view;
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Interface to be implemented by MainActivity
     * @author zyixc
     */
    public interface OnFragmentInteractionListener {
        public enum GameProfileScreenOptions{
            COMPARESCREEN
        }

        public void fromGameProfileScreenTo(GameProfileScreenOptions options, Game game);
    }

}
