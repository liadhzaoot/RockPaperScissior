package BordGraphics;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Logic.BoardLogic;

public class OpenningScreen extends JPanel implements MouseListener, ButtonListiner
{
		private BoardGame _boardGame;
		private Img _ScreenOpenImg,_instraction;
		JFrame _frame = new JFrame("My Frame");
		JFrame _frame2 = new JFrame("My Frame");
		boolean instrac = false;
		/**
		 * constractor
		 */
		public OpenningScreen()
		{
			_ScreenOpenImg = new Img("images\\screenOpenPhoto.png", 0, 0, 1440, 961);
			repaint();
			addMouseListener(this);
			//_boardGame.setBounds(0,0,1800,2000);
			_instraction = new Img("images\\instraction.png", 0, 0, 1440, 961);
			_frame.add(this);
			_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			_frame.setSize(1470,991);
			_frame.setVisible(true);
			
		}
		@Override
		protected void paintComponent(Graphics g)
		{
			// TODO Auto-generated method stub
			super.paintComponent(g);
			 System.out.println(instrac);
			 
			if(!instrac)
			_ScreenOpenImg.drawImg(g);
		     else
		     {
		    	 System.out.println("yardeni");
				_instraction.drawImg(g);
		     }
			g.drawLine(0, 0, 100, 100);
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("x = "+e.getX()+"y = "+e.getY());
			/**
			 * 
			 * play button
			 *	
			 */
			if(e.getX()<357 && e.getX()>55
			&&e.getY()<475&& e.getY()>332 && !instrac)
			{
				_boardGame = new BoardGame();
			}
			if(e.getX()<357 && e.getX()>55
				&&e.getY()<670&& e.getY()>528 && !instrac)
				{
				instrac = true;
				repaint();
				_frame2.add(this);
				_frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				_frame2.setSize(1500,1000);
				_frame2.setVisible(true);
				//_frame.setVisible(false);
				//instrac = false;
				}
			if(e.getX()<1400 && e.getX()>1160
					&&e.getY()<930&& e.getY()>800 &&
					instrac)
					{
					
					_frame2.setVisible(false);
					repaint();
					_frame.add(this);
					_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					_frame.setSize(1470,991);
					_frame.setVisible(true);
					instrac = false;
					//_frame.setVisible(true);
					
					//instrac = false;
					}
//			if(e.getX()<357 && e.getX()>55
//					&&e.getY()<670&& e.getY()>528)
//					{
//					System.exit(0);
//					}
			else
				if(instrac)
				{
					if(e.getX()<370 && e.getX()>135
					&&e.getY()<880&& e.getY()>770 )
					{
						BoardLogic._maxDepth=0;
						_frame2.setVisible(false);
						repaint();
						_frame.add(this);
						_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						_frame.setSize(1470,991);
						_frame.setVisible(true);
						instrac = false;
					}
					if(e.getX()<670 && e.getX()>440
							&&e.getY()<880&& e.getY()>770 )
					{
								BoardLogic._maxDepth=1;
								_frame2.setVisible(false);
								repaint();
								_frame.add(this);
								_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								_frame.setSize(1470,991);
								_frame.setVisible(true);
								instrac = false;
					}
					if(e.getX()<960 && e.getX()>770
							&&e.getY()<880&& e.getY()>770 )
					{
								BoardLogic._maxDepth=2;
								_frame2.setVisible(false);
								repaint();
								_frame.add(this);
								_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								_frame.setSize(1470,991);
								_frame.setVisible(true);
								instrac = false;
					}
					
				}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void buttonClick() {
			// TODO Auto-generated method stub
			
		}
		
	}


