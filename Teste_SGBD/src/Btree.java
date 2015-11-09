

import java.util.ArrayList;


public class Btree<T extends Comparable<T>> {
	
private Nodo raiz;
	
	public void inserir(T chave)
	{
		raiz = inserir0(raiz, chave);
		
	}
	
	private Nodo inserir0(Nodo nodo, T chave)
	{
		if(nodo == null)
		{
			return new Nodo(chave);
		}
		
		if(nodo.esquerdo == null && nodo.direito == null)
		{
			
			System.out.println("raiz " + nodo.chave);

			//olha pro nodo e ve se tem espaco
			// tem espaco, entao coloca o rowid la dentro
			if(nodo.temEspaco())
			{
				nodo.addOrdenadoArray(chave);
				System.out.println("inseri o " + chave);
				nodo.imprimeChaves();
				return nodo;
			}
			else if(nodo.temEspaco() == false)
			{
				System.out.println("inseri pelo sem espaco " + chave);

				//nao tem espaço? tem que fazer o spli das folhas  e reerenciar
				//add pra estouraR
				nodo.addOrdenadoArray(chave);
				nodo.imprimeChaves();

				
				//chama split da esquerda
				ArrayList<T> esquerda = nodo.splitEsquerda();
				//chama split da direita
				ArrayList<T> direita = nodo.splitDireita();
				//pega o meio do array
			    T meio = (T) nodo.getEstouro();
			    System.out.println("mei" + meio);
			    for(int i = 0; i<direita.size(); i++)
			    {
			    	System.out.println("dd"+direita.get(i));
			    }
			    
			    //cria o novo nodo direito
				Nodo novoDireita = new Nodo(meio);
				//novoDireita.addDireita(direita);
				
				 //cria o novo nodo esquerdo
				nodo.addEsquerda(esquerda); 
			    		  		    
				
			    //tem que fazer o nodo que estourou subir
				// c n tem pai ele eh o raiz, logo tem que criar um pai pra ele
				if(nodo.pai == null)
				{
					Nodo novo = new Nodo(meio);
					raiz = novo;
					novo.isRamo = true;
					novo.imprimeChaves();
					novo.direito = novoDireita;
					novo.esquerdo = nodo;
					return nodo.pai = novo;// inserir0(novo, meio);
					//novo.direito = novoDireita;

					
					//nodo.pai.esquerdo = nodo;
					//nodo.pai.direito = novoDireita;
					//raiz = nodo.pai;
					
					
		
				}
				
				else
				{
					//APENAS ADD NO PAI O NOVO NODO PRA ELE REFERENCIAR OS DEMAIS e faz o split do filho 
					Nodo novo = new Nodo(meio);
					novo.esquerdo = novo;
					nodo.pai.addOrdenadoArray(inserir0(novo, meio));
				}
				
				
				
			}
		}
		else if(chave.compareTo((T) nodo.chave) < 0)
		{
			System.out.print("entrei no maior");
			
			//olha pro nodo e ve se tem espaco
			// tem espaco, entao coloca o rowid la dentro
			if(nodo.temEspaco())
			{
				nodo.esquerdo.addOrdenadoArray(chave);
				return nodo;
			}
			else if(nodo.temEspaco() ==  false)
			{
				//nao tem espaço? tem que fazer o spli das folhas  e reerenciar
				//add pra estouraR
				nodo.addOrdenadoArray(chave);
				
				//chama split da esquerda
				ArrayList<T> esquerda = nodo.splitEsquerda();
				//chama split da direita
				ArrayList<T> direita = nodo.splitDireita();
				//pega o meio do array
			    T meio = (T) nodo.getEstouro();
			    
			    //cria o novo nodo esquerdo
				Nodo novoDireita = new Nodo(meio);
				novoDireita.addDireita(direita);
	
			    //cria o novo nodo direito
				nodo.addEsquerda(esquerda); 		    
				
			    //tem que fazer o nodo que estourou subir
				// c n tem pai ele eh o raiz, logo tem que criar um pai pra ele
				if(nodo.pai == null)
				{
					Nodo novo = new Nodo(meio);
					raiz = novo;
					novo.isRamo = true;
					novo.imprimeChaves();
					novo.direito = novoDireita;
					novo.esquerdo = nodo;
					return nodo.pai = novo;// inserir0(novo, meio);
					
					/*Nodo novo = new Nodo(meio);
					novo.esquerdo = nodo;
					novo.direito = novoDireita;
					return nodo.pai = inserir0(novo, meio);
					*/
				}
				
				else
				{
					// primeiro tenho que saber se o pai tem espaco
					
					if(nodo.pai.temEspaco())
					{
						System.out.println("aqui");
						nodo.pai.addOrdenadoArray(novoDireita);
					}
					
					else if(nodo.pai.temEspaco() ==  false)
					{
						//dai tem que fazer o spllit do no ramo
					}
					
				}
				
			}
			
			
		}
		else if (chave.compareTo((T) nodo.chave) > 0)
		{
			//olha pro nodo e ve se tem espaco
			// tem espaco, entao coloca o rowid la dentro
			if(nodo.temEspaco())
			{
				nodo.direito.addOrdenadoArray(chave);
				return nodo;
			}
			else if(nodo.temEspaco() ==  false)
			{
				//nao tem espaço? tem que fazer o spli das folhas  e reerenciar
				//add pra estouraR
				nodo.addOrdenadoArray(chave);
				
				//chama split da esquerda
				ArrayList<T> esquerda = nodo.splitEsquerda();
				//chama split da direita
				ArrayList<T> direita = nodo.splitDireita();
				//pega o meio do array
			    T meio = (T) nodo.getEstouro();
			    
			    //cria o novo nodo esquerdo
				Nodo novoDireita = new Nodo(meio);
				novoDireita.addDireita(direita);
	
			    //cria o novo nodo direito
				nodo.addEsquerda(esquerda); 		    
				
			    //tem que fazer o nodo que estourou subir
				// c n tem pai ele eh o raiz, logo tem que criar um pai pra ele
				if(nodo.pai == null)
				{
					Nodo novo = new Nodo(meio);
					raiz = novo;
					novo.isRamo = true;
					novo.imprimeChaves();
					novo.direito = novoDireita;
					novo.esquerdo = nodo;
					return nodo.pai = novo;// inserir0(novo, meio);
					/*
					Nodo novo = new Nodo(meio);
					novo.esquerdo = nodo;
					novo.direito = novoDireita;
					return nodo.pai = inserir0(novo, meio);*/
					
				}
				
				else
				{
					//APENAS ADD NO PAI O NOVO NODO PRA ELE REFERENCIAR OS DEMAIS e faz o split do filho 
	
					Nodo novo = new Nodo(meio);
					novo.esquerdo = novo;
					nodo.pai.addOrdenadoArray(inserir0(novo, meio));
				}
				
			}
		}
		else
		throw new IllegalArgumentException("Chave já existe");

	return nodo;
	}
	
	

	public String toString()
	{
		return String.format("Btree[raiz=%s]", toString0(raiz));
	}
	
	private String toString0(Nodo nodo)
	{
		if(nodo == null){return "#";}
		
		//String.format("%"
		
		String msg = " " + nodo.chave;
		msg += "\n[" + toString0(nodo.esquerdo) + "]";
		msg += "\n[" + toString0(nodo.direito) + "]";
		return msg;
			
	}
	
	
	
	public static void main(String[] args) {
	
		Btree<Integer> bt = new Btree();

		for(int i = 1; i< 14; i++)
		{
			bt.inserir(i);
		}
		System.out.println(bt.raiz.chave);
		System.out.println(bt.toString());
		
		
		
	}
	

}
