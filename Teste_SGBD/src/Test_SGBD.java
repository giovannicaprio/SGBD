import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class Test_SGBD {

	public static void main(String[] args) throws IOException {
		CriarArquivo();
		GravarIndice("1,2,3,4,5","3","1");
		RecuperaIndice();
		//LeituraArquivo();        	
	}

	public static void LeituraArquivo(int iOpcao ,HashMap mapa) throws IOException{

	RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
	byte[] teste = new byte[4096];
	StringBuilder sbDados = new StringBuilder();
	int posIni = 0;
	int posFim = 4096; // 4k
	int size = (int) leitura.length();
	

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
				 //############### FIXME ###########
						 
			 }
		 }	
		break;
	//Retorna um dataBlock
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
		LeituraArquivo(1,mapa);
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

