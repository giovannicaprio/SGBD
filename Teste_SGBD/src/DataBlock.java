
public class DataBlock {
	public int id;
	public DataBlock prox;
	public DataBlock ant;
	public String dados;

	
	public DataBlock(int id, DataBlock prox, DataBlock ant, String dados){
		this.id = id;
		this.prox = prox;
		this.ant = ant;
		this.dados = dados;
	}
}
