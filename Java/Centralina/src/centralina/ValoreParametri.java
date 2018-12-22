package centralina;

public class ValoreParametri {
	
	private String nome_via;
	private int id_centralina;
	private int numero_veicoli_limite;
	private int limite_velocita;
	private String inizio;
	private String fine;
	private int media;
	private int n_veicoli;

	public ValoreParametri(String nome_via, int id_centralina, int numero_veicoli_limite,
            int limite_velocit�, String inizio, String fine) {
		this.setNome_via(nome_via);
		this.setId_centralina(id_centralina);
		this.setNumero_veicoli_limite(numero_veicoli_limite);
		this.setLimite_velocit�(limite_velocit�);
		this.setInizio(inizio);
		this.setFine(fine);
	}
	
	public void aggiornamentoParametri(ValoreParametri nuovivalori) {
		this.setId_centralina(nuovivalori.getId_centralina());
		this.setLimite_velocit�(nuovivalori.getLimite_velocit�());
		this.setNumero_veicoli_limite(nuovivalori.getNumero_veicoli_limite());
		this.setNome_via(nuovivalori.getNome_via());
		this.setInizio(nuovivalori.getInizio());
		this.setFine(nuovivalori.getFine());
	}
	
	// getter e setter
		
		public int getNumero_veicoli_limite() {
			return numero_veicoli_limite;
		}
		public void setNumero_veicoli_limite(int numero_veicoli_limite) {
			this.numero_veicoli_limite = numero_veicoli_limite;
		}
		public int getLimite_velocit�() {
			return limite_velocita;
		}
		public void setLimite_velocit�(int limite_velocit�) {
			this.limite_velocita = limite_velocit�;
		}
		public int getId_centralina() {
			return id_centralina;
		}
		public void setId_centralina(int id_centralina) {
			this.id_centralina = id_centralina;
		}

		public String getFine() {
			return fine;
		}

		public void setFine(String fine) {
			this.fine = fine;
		}

		public String getInizio() {
			return inizio;
		}

		public void setInizio(String inizio) {
			this.inizio = inizio;
		}

		public String getNome_via() {
			return nome_via;
		}

		public void setNome_via(String nome_via) {
			this.nome_via = nome_via;
		}

		public int getMedia() {
			return media;
		}

		public void setMedia(int media) {
			this.media = media;
		}

		public int getN_veicoli() {
			return n_veicoli;
		}

		public void setN_veicoli(int n_veicoli) {
			this.n_veicoli = n_veicoli;
		}
		
}
