package holo.essentrika;

import holo.essentrika.states.GameOverState;
import holo.essentrika.states.GameState;
import holo.essentrika.states.MenuState;
import holo.essentrika.states.TutorialState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EssentrikaMain extends StateBasedGame
{
	public static final int MENUSTATEID = 0;
	public static final int GAMESTATEID = 1;
	public static final int GAMEOVERSTATEID = 2;
	public static final int TUTORIALSTATEID = 3;
	public static final int defaultWidth = 1280;
	public static final int defaultHeight = 800;
	
	public EssentrikaMain() 
	{
		super("Essentrika");
	}
    
    public static void main(String[] args) throws SlickException 
    {
            AppGameContainer app = new AppGameContainer(new EssentrikaMain());
              
            app.setDisplayMode(app.getScreenWidth() * 2 / 3, app.getScreenHeight() * 2 / 3, false);
            
            app.setVSync(true);
            app.setUpdateOnlyWhenVisible(false);
            
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() { if (GameState.world != null)GameState.world.save(); }
            });
            
            app.start();
    }

	@Override
	public void initStatesList(GameContainer gc) throws SlickException 
	{
		this.addState(new MenuState(MENUSTATEID, this));
		this.addState(new GameOverState(GAMEOVERSTATEID, this));
		this.addState(new TutorialState(TUTORIALSTATEID, this));
	}

}
