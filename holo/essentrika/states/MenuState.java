package holo.essentrika.states;

import holo.essentrika.EssentrikaMain;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState
{
	public static Music music;
	private final int stateID;

	public int textSize = 12;
	public int xCoord = 0;
	public int[] titleCoords;
	public int[] startGameCoords;
	public int[] loadGameCoords;
	public int[] optionsCoords;
	public int[] quitCoords;
	
	public float startGameScale = 1.0F;
	public float loadGameScale = 1.0F;
	public float optionsScale = 1.0F;
	public float quitScale = 1.0F;

	public Image title;
	public Image startGame;
	public Image loadGame;
	public Image options;
	public Image quit;
	
	StateBasedGame game;
	
	public MenuState(int stateID, StateBasedGame game)
	{
		this.stateID = stateID;
		this.game = game;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)throws SlickException 
	{
		title = new Image("res/Title.png");
		startGame = new Image("res/StartGame.png");
		loadGame = new Image("res/LoadGame.png");
		options = new Image("res/Options.png");
		quit = new Image("res/Quit.png");

		xCoord = gc.getWidth() / 2;
		int yCoord = 0;
		titleCoords = new int[]{xCoord - title.getWidth() / 2, yCoord};
		yCoord += title.getHeight() * 2;
		startGameCoords = new int[]{xCoord - startGame.getWidth() / 2, yCoord};
		yCoord += startGame.getHeight() * 2;
		loadGameCoords = new int[]{xCoord - loadGame.getWidth() / 2, yCoord};
		yCoord += loadGame.getHeight() * 2;
		optionsCoords = new int[]{xCoord - options.getWidth() / 2, yCoord};
		yCoord += options.getHeight() * 2;
		quitCoords = new int[]{xCoord - quit.getWidth() / 2, yCoord};
		
		music = new Music("res/sound/music.wav");
		music.loop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)throws SlickException 
	{
		g.scale(EssentrikaMain.defaultWidth / gc.getWidth(), EssentrikaMain.defaultHeight / gc.getHeight());
		title.draw(titleCoords[0], titleCoords[1]);
		startGame.draw(startGameCoords[0], startGameCoords[1], startGameScale);
		loadGame.draw(loadGameCoords[0], loadGameCoords[1], loadGameScale);
		options.draw(optionsCoords[0], optionsCoords[1], optionsScale);
		quit.draw(quitCoords[0], quitCoords[1], quitScale);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)throws SlickException 
	{
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		
		switch(getMouseOverType(mouseX, mouseY))
		{
		case 0:
			startGameScale = 1.0F;
			loadGameScale = 1.0F;
			optionsScale = 1.0F;
			quitScale = 1.0F;
			startGameCoords[0] = xCoord - startGame.getWidth() / 2;
			loadGameCoords[0] = xCoord - loadGame.getWidth() / 2;
			optionsCoords[0] = xCoord - options.getWidth() / 2;
			quitCoords[0] = xCoord - quit.getWidth() / 2;
			break;
		case 1:
			startGameScale = 1.05F;
			startGameCoords[0] = xCoord - (int)(startGame.getWidth() * 1.05F) / 2;
			break;
		case 2:
			loadGameScale = 1.05F;
			loadGameCoords[0] = xCoord - (int)(loadGame.getWidth() * 1.05F) / 2;
			break;
		case 3:
			optionsScale = 1.05F;
			optionsCoords[0] = xCoord - (int)(options.getWidth() * 1.05F) / 2;
			break;
		case 4:
			quitScale = 1.05F;
			quitCoords[0] = xCoord - (int)(quit.getWidth() * 1.05F) / 2;
			break;
		}
	}
	    
	@Override
    public void mouseClicked(int b, int x, int y, int clickCount) 
    {
		if(b == Input.MOUSE_LEFT_BUTTON)
		{
			switch(getMouseOverType(x, y))
			{
			case 0:
				break;
			case 1:
				startGame();
				break;
			case 2:
				loadGame();
				break;
			case 3:
				enterOptionsMenu();
				break;
			case 4:
				System.exit(0);
				break;
			}
		}
    }
	
	public int getMouseOverType(int x, int y)
	{
		if (x > startGameCoords[0] && x < startGameCoords[0] + startGame.getWidth()
				&& y > startGameCoords[1] && y < startGameCoords[1] + startGame.getHeight())
			return 1;
		else if (x > loadGameCoords[0] && x < loadGameCoords[0] + loadGame.getWidth()
				&& y > loadGameCoords[1] && y < loadGameCoords[1] + loadGame.getHeight())
			return 2;
		else if (x > optionsCoords[0] && x < optionsCoords[0] + options.getWidth()
				&& y > optionsCoords[1] && y < optionsCoords[1] + options.getHeight())
			return 3;
		else if (x > quitCoords[0] && x < quitCoords[0] + quit.getWidth()
				&& y > quitCoords[1] && y < quitCoords[1] + quit.getHeight())
			return 4;
		else
			return 0;
	}
	
	public void startGame()
	{
		music.setPosition(0.0F);
		music.setVolume(0.7F);
		game.addState(new GameState(EssentrikaMain.GAMESTATEID, game, false));
		game.enterState(EssentrikaMain.GAMESTATEID);
	}
	
	public void loadGame()
	{
		music.setPosition(0.0F);
		music.setVolume(0.7F);
		game.addState(new GameState(EssentrikaMain.GAMESTATEID, game, true));
		game.enterState(EssentrikaMain.GAMESTATEID);
	}
	
	public void enterOptionsMenu()
	{
		game.enterState(EssentrikaMain.TUTORIALSTATEID);
	}
	
	@Override
	public int getID() 
	{
		return this.stateID;
	}

}