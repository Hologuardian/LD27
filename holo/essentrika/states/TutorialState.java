package holo.essentrika.states;

import holo.essentrika.EssentrikaMain;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class TutorialState extends BasicGameState
{
	private final int stateID;
	StateBasedGame game;
	public Image title;
	public int[] titleCoords;
	public Image tutorial;
	public int[] tutorialCoords;

	public int[] menuCoords;
	public Image menu;
	
	public float menuScale = 1.0F;
	
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
		"Funding will be provided at the rate of $20 per 10 seconds for each powered tile.",
		"&If you are for some reason you are unable to keep up with the demand the company will have to abandon the project."};
	
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
		menu = new Image("res/Return.png");
		
		xCoord = gc.getWidth() / 2;
		yCoord = 0;
		tutorialCoords = new int[]{xCoord - tutorial.getWidth() / 2, yCoord};
		titleCoords = new int[]{xCoord - title.getWidth() / 2, yCoord};
		menuCoords = new int[]{xCoord - menu.getWidth() / 2, yCoord};
		yCoord += title.getHeight() * 1.5; 
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException
	{
		g.scale(EssentrikaMain.defaultWidth / gc.getWidth(), EssentrikaMain.defaultHeight / gc.getHeight());
		tutorial.draw(tutorialCoords[0], tutorialCoords[1]);
		
		Font font = gc.getDefaultFont();
		int y = (int) (tutorialCoords[1] + tutorial.getHeight());
		int x = gc.getWidth() / 2;
		
		for(String text : line)
		{
			if(text.charAt(0) == '&')
			{
				y += font.getLineHeight();
				text = text.substring(1);
			}
			FontUtils.drawCenter(font, text, x - font.getWidth(text) / 2, y, font.getWidth(text));
			y += font.getLineHeight();
		}
		
		titleCoords[1] = y;
		menuCoords[1] = y + title.getHeight();

		title.draw(titleCoords[0], titleCoords[1], titleScale);
		menu.draw(menuCoords[0], menuCoords[1], menuScale);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
	{
		int x = gc.getInput().getMouseX();
		int y = gc.getInput().getMouseY();

		titleScale = 1.0F;
		menuScale = 1.0F;
		titleCoords[0] = xCoord - title.getWidth() / 2;
		menuCoords[0] = xCoord - menu.getWidth() / 2;
		
		if (x > titleCoords[0] && x < titleCoords[0] + title.getWidth()
				&& y > titleCoords[1] && y < titleCoords[1] + title.getHeight())
		{
			titleScale = 1.05F;
			titleCoords[0] = xCoord - (int)(title.getWidth() * 1.05F) / 2;
		}
		else if (x > menuCoords[0] && x < menuCoords[0] + menu.getWidth()
				&& y > menuCoords[1] && y < menuCoords[1] + menu.getHeight())
		{
			menuScale = 1.05F;
			menuCoords[0] = xCoord - (int)(menu.getWidth() * 1.05F) / 2;
		}
	}
	
	@Override
    public void mouseClicked(int b, int x, int y, int clickCount) 
    {
		if (x > titleCoords[0] && x < titleCoords[0] + title.getWidth()
				&& y > titleCoords[1] && y < titleCoords[1] + title.getHeight())
		{
			game.addState(new GameState(EssentrikaMain.GAMESTATEID, game, false));
			game.enterState(EssentrikaMain.GAMESTATEID);
		}
		else if (x > menuCoords[0] && x < menuCoords[0] + menu.getWidth()
				&& y > menuCoords[1] && y < menuCoords[1] + menu.getHeight())
		{
			game.enterState(EssentrikaMain.MENUSTATEID);
		}
    }
	
	@Override
    public void keyPressed(int key, char c) 
    {
		if (key == Input.KEY_ESCAPE)
		{
			game.enterState(EssentrikaMain.MENUSTATEID);
		}
    }

	@Override
	public int getID()
	{
		return stateID;
	}

}
