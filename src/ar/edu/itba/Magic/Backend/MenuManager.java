package ar.edu.itba.Magic.Backend;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

public class MenuManager {
    private static MenuManager self;
    //private MenuTypes state=MenuTypes.Main;
    //private GameModeTypes mode;

    private MenuManager(){
        
    }
    
    public static MenuManager getInstance(){
        if(self == null){
            self = new MenuManager();
        }
        return self;
    }
    
   


  /*  private void generateGame(GameModeTypes mode) {
        
        GameManager.getInstance().newGame(mode);
        GameManagerUI.getInstance().newGame(mode);
    }*/
    

    
    public void update(float deltaTime) {	
    }
}
