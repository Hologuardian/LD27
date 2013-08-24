package holo.essentrika.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState
{
	private final int stateID;
	StateBasedGame game;
	
	public GameOverState(int stateID, StateBasedGame game)
	{
		this.stateID = stateID;
		this.game = game;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)throws SlickException 
	{
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)throws SlickException 
	{
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)throws SlickException 
	{
		
	}

	@Override
	public int getID() 
	{
		return this.stateID;
	}

}
