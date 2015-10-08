import java.awt.image.ConvolveOp;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map.Entry;

import org.xml.sax.helpers.ParserFactory;

public class Test_SGBD {

	public static void main(String[] args) throws IOException {
		CriarArquivo();
		GravarIndice("1,2,3,4,5","3","1");
		//Teste de Insert
		int id = 200;
		String texto = "Bla Bla bla \n";
		String rowID="5;2";
		//
		GravaDataBlock( id, texto, rowID);
		
		
		
		
				
		
		RecuperaIndice();
		//LeituraArquivo();        	
	}

	public static boolean GravaDataBlock(int id, String texto, String rowID) throws IOException{
		RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
		//Referencia de ter feito UPDATE ou INSERT primeira posicao, além de ter gravado
		boolean bGravou = false;
		//Referência para tamanho do ID
		int tamIDBytes=7;
		//Hash Map
		HashMap <Integer, String>  mapDataBlock = new HashMap<Integer, String>();
		//Posição de referência do valor dentro do rowid
		int posrowid=0;
		//Auxiliar para ler os dados do byte e Ler ID do dado	
		StringBuilder sbDados = new StringBuilder();
		StringBuilder sbID = new StringBuilder();
		//Criar um bytes do tamanho de cada datablock auxiliares
		byte[] datablockAux = new byte[4096];
		//Pega a posição de memória do dado dentro do datablock 5;2 ficará so o 2
		int refPos =Integer.parseInt(rowID.split(";")[1]);		
		//Referencia para ler a posição inicial do 4kb
		int posIni = 4096 * Integer.parseInt(rowID.split(";")[0]); // Restará so 5 do 5;2
		posIni =4096 - posIni;
		//passagem do bytes vazios e retorno dele preenchido com os dados daquela posição específica
		leitura.read(datablockAux,posIni,datablockAux.length);
		//Percorrer e converter o bytes
		for (int i = 0; i < datablockAux.length ; i++){
			//Diferente de dados em branco 0 bytes
			 if(datablockAux[i] != 0){
				 //Se não for \n como referência de parada 
				 if(datablockAux[i] != 10){
					 char cLetra = (char)datablockAux[i];
					//Decrementa1 quando for ID
					 tamIDBytes--;
					 if(tamIDBytes > 0){
						 sbID.append(cLetra); // Guarda a ID
					 }else{
						 sbDados.append(cLetra); // Guarda o texto
					 }
				 }else{
					 //Reset Variavel
					 tamIDBytes = 7;
					 if(sbID.toString().length()>0){
						 int IDConvertdo = Integer.parseInt(sbID.toString());
						 StringBuilder sJuncao = new StringBuilder();
						 sJuncao.append(sbID.toString());
						 //UPDATE
						 if(IDConvertdo == id){ bGravou = true; sJuncao.append(texto);}
						 //CONDICAO DE NÃO SER O REGISTRO
						 else{ sJuncao.append(sbDados.toString());}
						 
						 mapDataBlock.put(posrowid,sJuncao.toString());
						 posrowid ++;
					 }
				 }
			 }
		}
		//Grava na primeira posição os dados
		if(mapDataBlock.isEmpty()){
			bGravou = true;
			mapDataBlock.put(0,String.valueOf(id).trim() + texto.trim());
		}
		//Grava na última posição caso não tenha dentro do datablock 
		if(!bGravou){
			mapDataBlock.put(posrowid,String.valueOf(id).trim() + texto.trim());
			bGravou = true;
		}
		
		//Salvar no buffer
		datablockAux = new byte[4096];
		StringBuilder sBGravarDados = new StringBuilder();
		for(Entry<Integer, String> entry : mapDataBlock.entrySet()) {
		    //AQUI FICARIA O ID; 5;12358
		    //AQUI ESCREVER O TEXTO EM BYTES// entry.getValue();
		}
		
		return bGravou;
	}
	
	/* ######################## NÃO APAGAR ######################### 
	public static void GravaDataBlock(String rowID, String texto) throws IOException{
		RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
		//Hash Map
		HashMap <Integer, String>  mapDataBlock = new HashMap<Integer, String>();
		//Posição de referência do valor dentro do rowid
		int posrowid=0;
		//Auxiliar para ler os dados do byte	
		StringBuilder sbDados = new StringBuilder();
		//Criar um bytes auxiliares
		byte[] teste = new byte[4096];
		//Pega a posição de memória do dado dentro do datablock 1;5 ficará so o 5
		int refPos =Integer.parseInt(rowID.split(";")[1]);		
		//Referencia para ler a posição inicial do 4kb
		int posIni = 4096 * Integer.parseInt(rowID.split(";")[0]); // Restará so 1 do 1;5
		posIni =4096 - posIni;
		//passagem do bytes vazios e retorno dele preenchido com os dados daquela posição específica
		leitura.read(teste,posIni,teste.length);
		//Percorrer e converter o bytes
		for (int i = 0; i < teste.length ; i++){
			//Diferente de dados em branco 0 bytes
			 if(teste[i] != 0){
				 //Se não for \n como referência de parada 
				 if(teste[i] != 10){
					 char cLetra = (char)teste[i];
					 sbDados.append(cLetra);
				 }else{
					 //Adiciona os dados no map 
					 mapDataBlock.put(posrowid,sbDados.toString());
					 posrowid ++;
				 }
			 }
		}
		//Grava na primeira posição os dados
		if(sbDados.toString().length() == 0){
			leitura.write(sbDados.toString().getBytes(),posIni,sbDados.toString().getBytes().length);
		}else{
			//irá dar um update no arquivo naquela posição 
			if(mapDataBlock.containsKey(refPos)){
				mapDataBlock.put(refPos,sbDados.toString());			
			}else{
				//TODO Gravara na ultima posição				
			}
		}
		
		//TODO ver apartir dos dados como fazer a substituição
		
		
	}*/
	
	
	public static void LeituraArquivo(int iOpcao ,HashMap mapa, int rowID) throws IOException{
	RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
	byte[] teste = new byte[4096];
	StringBuilder sbDados = new StringBuilder();
	int posIni = 0;
	int posFim = 4096; // 4k
	int size = (int) leitura.length();
	
	//Gambiarra para ler a posição do datablock
	posIni = 4096 - posFim;
	posFim = posFim * rowID;
	
	//1 - Indice, 2 - DataBlock específico
	switch (iOpcao) {
	//Retorna o Indice em um hashMap
	case 1:
		int icountZero= 0;
		leitura.read(teste, posIni, posFim);
		for (int i = 0; i < teste.length ; i++){
			 if(teste[i] != 0){
				char cLetra = (char)teste[i];
		   		sbDados.append(cLetra); 
			 }else {
				 
				 if (icountZero == 0 && sbDados.toString().length() > 0){
					 //Armazena a lista FREE
					  mapa.put("free", sbDados.toString());
					  //Limpa a variável
					  sbDados = new StringBuilder();
						 icountZero ++; 
				 }else if (sbDados.toString().length() > 0 && icountZero == 1){
					  mapa.put("root", sbDados.toString());	
					  //Limpa a variável
					  sbDados = new StringBuilder();
					   icountZero ++; 
				 }
				 else if(sbDados.toString().length() > 0 && icountZero > 1){
					 mapa.put("table", sbDados.toString());	
					  //Limpa a variável
					  sbDados = new StringBuilder();
					   break;  
					
				 }
			}
		 }	
		break;
	//CASO QUE RETORNA O DATABLOCK ESPECÍFICO
	case 2:
		while(size > 0 && posIni < size){
			leitura.read(teste, posIni, posFim);
			for (int i = 0; i < teste.length ; i++){
				 if(teste[i] != 0){
					char cLetra = (char)teste[i];
			   		sbDados.append(cLetra); 
				 }              		    
			 }
				System.out.print(sbDados.toString());
				size -= posFim;
				posIni += posFim + 1;
				posFim = posFim + posFim;	
			}
		
		   leitura.close();	   
	}


	}

	public static void GravarIndice(String listFree, String root, String table) {
	/*###################### GRAVACAO DE DADOS ######################*/
	try{
		//Acesso randomico 
		RandomAccessFile escrita = new RandomAccessFile("arquivo.bin", "rw");
		//Referencias
		String sfree = listFree;
		String sroot = root;
		String stable = table;
		//Colocando valores nas posições
		escrita.seek(0);
		escrita.write(sfree.getBytes("utf-8"));
		
		escrita.seek(1001);
		escrita.write(sroot.getBytes("utf-8"));
		
		escrita.seek(1021);
		escrita.write(stable.getBytes("utf-8"));
		
		//Fecha o arquivo
		escrita.close();	
		
	}catch(FileNotFoundException e){
		e.printStackTrace();
		System.out.println("Erro = "+e);  
	}catch(IOException e){
		e.printStackTrace();
		System.out.println("Erro = "+e);  
	}	
	}
	

	public static HashMap RecuperaIndice() throws IOException{
		DataBlock dtBlock =  new DataBlock();
		HashMap <String, String>  mapa = new HashMap<String, String>();
		LeituraArquivo(1,mapa,1);
		return mapa;	
	}

	public static void CriarArquivo() {
	try{
		FileOutputStream out = new FileOutputStream("arquivo.bin");  
		ObjectOutputStream os = new ObjectOutputStream(out);
		//Especifica o tamanho do arquivo 256MB
		byte[] buf = new byte[260884000];
		os.write(buf);
		os.flush();
		os.close();	 		
	
	}catch(FileNotFoundException e){
		e.printStackTrace();
		System.out.println("Erro = "+e);  
	}catch(IOException e){
		e.printStackTrace();
		System.out.println("Erro = "+e);  
	}catch(IndexOutOfBoundsException e){
		e.printStackTrace();
		System.out.println("Erro = "+e);  
	}		
	}	
	
	
}

