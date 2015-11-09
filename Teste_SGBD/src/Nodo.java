

import java.awt.List;
import java.util.ArrayList;

public class Nodo<T extends Comparable<T>>  {
	
	public T chave;
	public Nodo esquerdo;
	public Nodo direito;
	public Nodo pai;
	public ArrayList<T> rowIds = new ArrayList<T>();
	public boolean isRamo;
	
	public int altura; // melhor gastar memoria do que procesador
	
	public Nodo(T chave)
	{
		this.chave = chave;
		altura = 0; // um novo nodo sempre tem altura zero
		esquerdo = direito = pai = null;
		isRamo = false;
		this.addOrdenadoArray(chave);
	}
	
	public void addOrdenadoArray(T chave)
	{
			rowIds.add(chave);
			//ordena	
			for (int i = 0; i < rowIds.size(); i++) 
				{
					for (int j = 0; j < rowIds.size() - 1; j++) 
					{
						if (rowIds.get(j).compareTo(rowIds.get(j + 1)) > 0) 
						{
							troca(rowIds, j, j + 1);
							
						}
					}
				}
	}
	
	public void addOrdenadoArray(Nodo<T> chave)
	{
		
			//add
			rowIds.add((T) chave);
			//ordena	
			for (int i = 0; i <= rowIds.size(); i++) 
				{
					for (int j = 0; j <= rowIds.size() - 1; j++) 
					{
						if (rowIds.get(j).compareTo(rowIds.get(j + 1)) > 0) 
						{
							troca(rowIds, j, j + 1);
						}
					}
				}
	}
	
	
	public void troca(ArrayList<T> lista, int j, int i) {
		T tmp = lista.get(i);
		lista.add(i, lista.get(j));
		lista.add(j, tmp);
	}

	public T getEstouro()
	{
		return (T) rowIds.get(2);
	}
	
	public T getPrimeiroDireita()
	{
		return (T) rowIds.get(3);
	}

	
	public boolean temEspaco()
	{
		if(rowIds.size() >= 4 )
			return false;
		else
			return true;
	}

		public ArrayList<T> splitEsquerda() {
		ArrayList<T> resposta = new ArrayList<T>();
		resposta.add(0, rowIds.get(0));
		resposta.add(1, rowIds.get(1));
		
		return resposta;
	}
	
	public ArrayList<T> splitDireita() {
		ArrayList<T> resposta = new ArrayList<T>();
		resposta.add(0, rowIds.get(2));
		resposta.add(1, rowIds.get(3));
		resposta.add(2, rowIds.get(4));
		
		return resposta;
	}
	
	public void addEsquerda(ArrayList<T> esquerda) 
	{
		for(int i = 0; i<esquerda.size(); i++)
			{
				rowIds.add(i, esquerda.get(i));

			}
	}
	
	public void addDireita(ArrayList<T> direita) 
	{
		for(int i = 0; i<direita.size(); i++)
			{
				rowIds.add(i, direita.get(i));

			}
	}
	
	public void imprimeChaves() 
	{
		for(int i = 0; i<rowIds.size(); i++)
			{
				System.out.println(rowIds.get(i));

			}
	}
	
	

}
