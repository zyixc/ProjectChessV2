package com.chessmaster.app;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.chessmaster.app.data.DataProvider;
import com.chessmaster.app.data.Game;
import com.chessmaster.app.data.Player;
import com.chessmaster.app.ui.CompareScreen;
import com.chessmaster.app.ui.GameProfileScreen;
import com.chessmaster.app.ui.GameSearchResultListScreen;
import com.chessmaster.app.ui.GameSearchScreen;
import com.chessmaster.app.ui.PlayerProfileScreen;
import com.chessmaster.app.ui.PlayerSearchResultListScreen;
import com.chessmaster.app.ui.PlayerSearchScreen;
import com.chessmaster.app.ui.StartScreen;

import java.util.List;
/**
 * MainActivity Controls all UI fragments
 * @author zyixc
 */
public class MainActivity extends FragmentActivity
        implements StartScreen.OnFragmentInteractionListener,
        PlayerSearchScreen.OnFragmentInteractionListener,
        PlayerSearchResultListScreen.OnFragmentInteractionListener,
        PlayerProfileScreen.OnFragmentInteractionListener,
        GameSearchScreen.OnFragmentInteractionListener,
        GameSearchResultListScreen.OnFragmentInteractionListener,
        GameProfileScreen.OnFragmentInteractionListener,
        CompareScreen.OnFragmentInteractionListener{
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, StartScreen.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
        DataProvider.INSTANCE.initDataProvider(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.action_player_search_screen: getFragmentManager()
                    .beginTransaction().replace(R.id.container, PlayerSearchScreen.newInstance())
                    .addToBackStack(null)
                    .commit();
                break;
            case R.id.action_game_search_screen: getFragmentManager()
                    .beginTransaction().replace(R.id.container, GameSearchScreen.newInstance())
                    .addToBackStack(null)
                    .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount()==1){
            this.finish();
        }else{
            getFragmentManager().popBackStack();
        }
    }
    
    /**
     * Handles request to move to next screen.
     * From StartScreen to PlayersSearchScreen/GameSearchScreen.
     */
    public void fromStartScreenTo(StartScreenOptions options){
        if(options == StartScreenOptions.PLAYERSEARCHSCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, PlayerSearchScreen.newInstance())
                .addToBackStack(null)
                .commit();
        else if(options == StartScreenOptions.GAMESEARCHSCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, GameSearchScreen.newInstance())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Handles request to move to next screen.
     * From PlayerSearchScreen to PlayersSearchResultListScreen.
     */
    public void fromPlayerSearchScreenTo(PlayerSearchScreenOptions options, List<Player> players){
        if(options == PlayerSearchScreenOptions.PLAYERSEARCHRESULTLISTSCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, PlayerSearchResultListScreen.newInstance(players))
                .addToBackStack(null)
                .commit();
    }
    
    /**
     * Handles request to move to next screen.
     * From PlayerSearchResultListScreen to PlayerProfileScreen.
     */
    public void fromPlayerSearchResultListTo(PlayerSearchResultListScreenOptions options, Player player){
        if(options == PlayerSearchResultListScreenOptions.PLAYERPROFILESCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, PlayerProfileScreen.newInstance(player))
                .addToBackStack(null)
                .commit();
    }
    
    /**
     * Handles request to move to next screen.
     * From PlayerProfileScreen to GameSearchResultListScreen.
     */
    public void fromPlayerProfileScreenTo(PlayerProfileScreenOptions options, List<Game> games){
        if(options == PlayerProfileScreenOptions.GAMESEARCHRESULTLISTSCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, GameSearchResultListScreen.newInstance(games))
                .addToBackStack(null)
                .commit();
    }

    /**
     * Handles request to move to next screen.
     * From GameSearchScreen to GameSearchRsultListScreen.
     */
    public void fromGameSearchScreenTo(GameSearchScreenOptions options, List<Game> games){
        if(options == GameSearchScreenOptions.GAMESEARCHRESULTLISTSCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, GameSearchResultListScreen.newInstance(games))
                .addToBackStack(null)
                .commit();
    }

    /**
     * Handles request to move to next screen.
     * From GameSearchResultListScreen to GameProfileScreen.
     */
    public void fromGameSearchResultListTo(GameSearchResultListScreenOptions options, Game game){
        if(options == GameSearchResultListScreenOptions.GAMEPROFILESCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, GameProfileScreen.newInstance(game))
                .addToBackStack(null)
                .commit();
    }

    /**
     * Handles request to move to next screen.
     * From GameProfileScreen to CompareScreen.
     */
    public void fromGameProfileScreenTo(GameProfileScreenOptions options, Game game){
        if(options == GameProfileScreenOptions.COMPARESCREEN) getFragmentManager()
                .beginTransaction().replace(R.id.container, CompareScreen.newInstance(game))
                .addToBackStack(null)
                .commit();
    }

    /**
     * Handles request to move to next screen.
     * From CompareScreen to GameSearchResultListScreen.
     */
    public void fromCompareScreenTo(CompareScreenOptions options, List<Game> games){
        if(options == CompareScreenOptions.GAMESEARCHRESULTLIST) getFragmentManager()
                .beginTransaction().replace(R.id.container, GameSearchResultListScreen.newInstance(games))
                .addToBackStack(null)
                .commit();
    }
}
