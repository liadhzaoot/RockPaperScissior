package BordGraphics;


import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		JFrame _frame = new JFrame();
		/**
		JFrame j = new JFrame();
		
		try
		{
		MyPanel p = new MyPanel();
		j.add(p);
		
		}
		catch(NullPointerException n)
		{
			System.out.println("the image xxx not good");
		}
		
		j.setVisible(true);
		j.setSize(600, 600);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		**/
		
		//BoardGame bp= new BoardGame();
		//BoardPanel bp = new BoardPanel();
		OpenningScreen bp = new OpenningScreen();
//		_frame.add(bp);
//		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		_frame.setSize(1800,2000);
//		_frame.setVisible(true);
	}

}
