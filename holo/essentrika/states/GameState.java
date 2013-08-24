package holo.essentrika.states;

import holo.essentrika.map.World;
import holo.essentrika.modules.IModule;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class GameState extends BasicGameState
{
	private final int stateID;
	StateBasedGame game;
	
	GradientFill fill;
	
	public static World world;
	int[] cameraCoords = new int[]{0, 0};
	
	
	public GameState(int stateID, StateBasedGame game, boolean load)
	{
		this.stateID = stateID;
		this.game = game;
		GameState.world = new World(load);
		System.out.println(load);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)throws SlickException 
	{
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)throws SlickException 
	{
		g.setBackground(Color.black);
		g.clear();
		g.setColor(Color.white);
//		g.drawRect(0, 0 + gc.getHeight() / 16, gc.getWidth(), gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8);
		
		int moduleWidth = gc.getWidth() / 64;
		int moduleHeight = (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64;
		
		for(int i = 0; i < gc.getWidth() / 64; ++i)
		{
			float x = i * 64;
			for (int j = 0; j < (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64; ++j)
			{
				float y = gc.getHeight() / 16 + j * 64;
				g.drawRect(x, y, 64, 64);
				Shape box = new Rectangle(x, y, 64, 64);
				Color col = Color.black;
//				Image sprite = world.getModuleAt(i - moduleWidth / 2 + cameraCoords[0], j - moduleHeight / 2 + cameraCoords[1]).getIcon();
//				g.drawImage(sprite, x, y);
				switch(world.getModuleAt(i - moduleWidth / 2 + cameraCoords[0], j - moduleHeight / 2 + cameraCoords[1]).getID())
				{
				case 0:
					col = Color.green;
					break;
				case 1:
					col = Color.blue;
					break;
				case 2:
					col = Color.orange;
					break;
				}
				fill = new GradientFill(box.getX(), box.getY(), col, box.getX() + box.getWidth(), box.getY() + box.getHeight(), col);
				g.fill(box, fill);
				g.draw(box);
			}
		}
		
		String locationTitle = "Camera Location";
		String location = cameraCoords[0] + "," + -cameraCoords[1];
		
		
		Font font = gc.getDefaultFont();
		int y = gc.getHeight() / 32 - font.getLineHeight();
		int x = gc.getWidth() - Math.max(font.getWidth(location), font.getWidth(locationTitle));
		FontUtils.drawLeft(font, locationTitle, x, y);
		y = gc.getHeight() / 32;
		FontUtils.drawLeft(font, location, x, y);
		
		
		if (getBoxFromMouseCoords(gc, gc.getInput().getMouseX(), gc.getInput().getMouseY()) != null)
		{
			int[] coord = getBoxFromMouseCoords(gc, gc.getInput().getMouseX(), gc.getInput().getMouseY());
			String mLocationTitle = "Cursor Location";
			String mLocation = coord[0] + "," + -coord[1];

			y = gc.getHeight() / 32 - font.getLineHeight();
			x -= Math.max(font.getWidth(mLocationTitle), font.getWidth(mLocation));
			FontUtils.drawLeft(font, mLocationTitle, x, y);
			y = gc.getHeight() / 32;
			FontUtils.drawLeft(font, mLocation, x, y);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)throws SlickException 
	{
		int moduleWidth = gc.getWidth() / 64;
		int moduleHeight = (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64;
		
		IModule[][] modules = new IModule[moduleWidth][moduleHeight];
		
		for(int i = 0; i < gc.getWidth() / 64; ++i)
		{
			for (int j = 0; j < (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64; ++j)
			{
				modules[i][j] = world.getModuleAt(i - moduleWidth / 2 + cameraCoords[0], j - moduleHeight / 2 + cameraCoords[1]);
//				System.out.print(modules[i][j].getID() + " ");
			}
		}
//		System.out.println();
	}
	
	public int[] getBoxFromMouseCoords(GameContainer gc, int x, int y)
	{
		int moduleWidth = gc.getWidth() / 64;
		int moduleHeight = (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) / 64;
		
		if (y < (gc.getHeight() - gc.getHeight() / 16 - gc.getHeight() / 8) && y > gc.getHeight() / 16)
		{
			int i = x / 64;
			int j = (y - gc.getHeight() / 16 - gc.getHeight() / 8) / 64;
			
			return new int[]{i - moduleWidth / 2 + cameraCoords[0], j - moduleHeight / 2 + cameraCoords[1]};
		}
		return null;
	}
	
	@Override
    public void keyPressed(int key, char c) 
    {
		if (key == Input.KEY_LEFT)
			--cameraCoords[0];
		else if (key == Input.KEY_RIGHT)
			++cameraCoords[0];
		else if (key == Input.KEY_DOWN)
			++cameraCoords[1];
		else if (key == Input.KEY_UP)
			--cameraCoords[1];
    }

	@Override
	public int getID() 
	{
		return this.stateID;
	}

}