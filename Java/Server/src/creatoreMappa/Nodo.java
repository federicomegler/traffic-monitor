package creatoreMappa;

import java.util.HashSet;
import java.util.Set;



public class Nodo {
    private boolean mark;
	private String coordinate;
	private double x,y;
	private Set<Strada> lista; 
	
	public Nodo(String coordinate) {
		
		setCoordinate(coordinate);
	   setLista(new HashSet<Strada>());
	   setMark(false);
   	String s[]=coordinate.split("\\£" );
    setY(Double.parseDouble(s[0]));
   	setX(Double.parseDouble(s[1]));
 
	}

	public boolean equals(Nodo node) {
		
		return(coordinate.equals(node.getCoordinate()));
		
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public Set<Strada> getLista() {
		return lista;
	}

	public void setLista(Set<Strada> lista) {
		this.lista = lista;
	}

	public boolean isMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
}
