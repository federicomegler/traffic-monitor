package graficaUtente;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.rmi.NotBoundException;

public class Line implements Comparable<Line> {
	    private String nome;
	    private final int x1;
	    private final int x2;
	    private final int y1;
	    private final int y2;
	    private char stato;
	    private boolean presenza;
	    boolean selected = false;
	    public Line(String linea) {//1m =0.15px 100m=15px
	    	String[] vector = linea.split("\\,");
	    	String[]coordinate = vector[0].split("\\£");
	    	y1=Integer.parseInt(coordinate[0]);
	    	x1=Integer.parseInt(coordinate[1]);
	    	coordinate = vector[1].split("\\£");
	    	y2=Integer.parseInt(coordinate[0]);
	    	x2=Integer.parseInt(coordinate[1]);
	    	stato = vector[2].charAt(0);
	    	nome = new String(vector[3]);
	    	presenza = vector[4].equals("1");
	    }
	    
	    public void paintBack(Graphics g) {
		 	
		 	Graphics2D g2 = (Graphics2D) g;
		 	g2.setStroke(new BasicStroke(16));
			if(!selected)return;
			g.setColor(Color.MAGENTA);
			g2.drawLine((int)(x1*0.10)+50,(int)(y1*0.10)+50,(int)(x2*0.10)+50,(int)(y2*0.10)+50);
		 }
	public void paintStatus(Graphics g) {
	 	
	 	Graphics2D g2 = (Graphics2D) g;
	 	g2.setStroke(new BasicStroke(4));
	 	switch (this.stato) {
		case 'I':
			g2.setColor(Color.RED);
			break;
		case 'M':
			g2.setColor(Color.YELLOW);
			break;
		default:
			g2.setColor(Color.GREEN);
			break;
		} 
	 	
	 	g2.drawLine((int)(x1*0.10)+50,(int)(y1*0.10)+50,(int)(x2*0.10)+50,(int)(y2*0.10)+50);
		
	 }
	public void paintStreet(Graphics g) {
	 	
	 	Graphics2D g2 = (Graphics2D) g;
	 	g2.setStroke(new BasicStroke(10));
		g.setColor(Color.GRAY);
	 	
		g2.drawLine((int)(x1*0.10)+50,(int)(y1*0.10)+50,(int)(x2*0.10)+50,(int)(y2*0.10)+50);
		
	 }
	    
	    
	    public void setSelected(boolean l) {
	    	this.selected = l;
	    }
	    
	    public String getNome() {
	    	return this.nome;
	    }

		public boolean isPresenza() {
			return presenza;
		}

		public void setPresenza(boolean presenza) {
			this.presenza = presenza;
		}

		public int getX1() {return this.x1;}

		public int getY1() {return this.y1;}

		public int getX2() {return this.x2;}

		public int getY2() {return this.y2;}
		@Override
		public int compareTo(Line o) {
			// TODO Auto-generated method stub
			return this.getNome().compareTo(o.getNome());
		}
}
