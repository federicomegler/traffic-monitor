package creatoreMappa;
public class Strada {
	
 private Nodo node1, node2;
 private String via;
 private int lunghezza;
 private char stato;
 private String id_centralina = null;


 public Strada(Nodo i1, Nodo i2, String nome, int distanza, String  centralina ) { //modificare centralina
   setNode1(i1);
   setNode2(i2);
   setVia(nome);
   setLunghezza(distanza);
   setStato('R');
   setId_centralina(centralina);
 }

 public boolean equals(Strada tratto) {
	 
	 return((tratto.node1.equals(node1))&&(tratto.node2.equals(node2))
			 &&(tratto.via.equals(via))&&(tratto.lunghezza==lunghezza));
	 
 }
 

 
 //getter e setter
 
public char getStato() {
	return stato;
}
public void setStato(char stato) {
	this.stato = stato;
}
public int getLunghezza() {
	return lunghezza;
}
public void setLunghezza(int lunghezza) {
	this.lunghezza = lunghezza;
}
public String getVia() {
	return via;
}
public void setVia(String via) {
	this.via = via;
}

public Nodo getNode1() {
	return node1;
}

public void setNode1(Nodo node1) {
	this.node1 = node1;
}

public Nodo getNode2() {
	return node2;
}

public void setNode2(Nodo node2) {
	this.node2 = node2;
}

public String getId_centralina() {
	return id_centralina;
}

public void setId_centralina(String id_centralina) {
	this.id_centralina = id_centralina;
}
 

 
}

