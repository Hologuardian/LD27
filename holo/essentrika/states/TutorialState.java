package holo.essentrika.states;

import holo.essentrika.EssentrikaMain;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TutorialState extends BasicGameState
{
	private final int stateID;
	StateBasedGame game;
	public Image title;
	public int[] titleCoords;
	public Image tutorial;
	public int[] tutorialCoords;
	
	public int xCoord;
	public int yCoord;
	public float titleScale = 1.0F;
	
	public static final String line[] = new String[]
			{"Congratulations! You are the new head of Essentrika, a start-up power company providing power to a city in construction.",
		"It is your job to provide a power grid that the construction crews will have to use when building the city.",
		"You can see your current funds in the top left of the screen and the current power requirement in the top center.",
		"&Use the arrow keys to move the screen.",
		"You can select a tile by clicking on it, from there you will be able to upgrade it into different modules.",
		"Generators create power, conduits will carry power from generators to land tiles.",
		"You can upgrade land tiles to use more power but provide more powered sections. (Conserve Space)",
		"Funding will be provided at the rate of $18 per 10 seconds for each powered tile.",
		"&If you are for some reason "};
	
	public TutorialState(int stateID, StateBasedGame game)
	{
		this.stateID = stateID;
		this.game = game;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException
	{
		tutorial = new Image("res/Options.png");
		title = new Image("res/StartGame.png");
		
		xCoord = gc.getWidth() / 2;
		yCoord = 0;
		titleCoords = new int[]{xCoord - title.getWidth() / 2, yCoord};
		yCoord += title.getHeight() * 1.5; 
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException
	{
		g.scale(EssentrikaMain.defaultWidth / gc.getWidth(), EssentrikaMain.defaultHeight / gc.getHeight());
		title.draw(titleCoords[0], titleCoords[1], titleScale);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
	{
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();
		
		if (x > titleCoords[0] && x < titleCoords[0] + title.getWidth()
				&& y > titleCoords[1] && y < titleCoords[1] + title.getHeight())
		{
			titleScale = 1.05F;
			titleCoords[0] = xCoord - (int)(title.getWidth() * 1.05F) / 2;
		}
		else
		{
			titleScale = 1.0F;
			titleCoords[0] = xCoord - title.getWidth() / 2;
		}
	}

	@Override
	public int getID()
	{
		return stateID;
	}

}
