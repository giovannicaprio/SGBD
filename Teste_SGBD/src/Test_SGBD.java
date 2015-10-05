import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;


public class Test_SGBD {

	public  void main(String[] args) throws IOException {
		
		CriarArquivo();
		GravarArquivo();
		LeituraArquivo();        	
}

public void LeituraArquivo() throws IOException{
	/*###################### LEITURA DE DADOS ######################*/
	RandomAccessFile leitura = new RandomAccessFile("arquivo.bin", "rw");
    byte btTam[] = new byte[4000];
    boolean bContinuaLeitura = true;
    while(bContinuaLeitura){
    	long size;	
        long posIni = 0;
    	try{
    		//tamanho do arquivo
        	size = leitura.readLong();
        }catch (EOFException e){
        	break;
        }
    	//leitura de datablock 
        int len = 4000;
        while(size > 0 && size > len){
        	try{
        		//preenchimento do buffer 
        		leitura.read(btTam,(int) posIni, len);
               	 StringBuilder sbDados = new StringBuilder();
               	ByteBuffer btSeparacao = ByteBuffer.wrap(btTam);
               	
               	
               	
               	 
               	 
	        	    for (byte b : btTam) {
	        	    	char cLetra = (char)b;
	        	    	//visualizar dados
	        	        sbDados.append(cLetra);
	        	        //GAMBIARRA PARA STOP bytes, para o procedimento quando encontrar \n
	        	        if(b == 10){
	        	        	break;
	        	        }
	        	    }
	        	    // prints character
		            System.out.print(sbDados.toString());
	          	size -= len;
	          	posIni += len + 1;
	          	len = len + len;	
        	}catch (IndexOutOfBoundsException e){
        			System.out.println("Fim dos dados!");
        			//Para a leitura
        			bContinuaLeitura = false;
        			//Termina o segundo loop
        			break;
        	}
        }
    }
    leitura.close();	
}

public  void GravarArquivo() {
	/*###################### GRAVACAO DE DADOS ######################*/
	try{
		//Acesso randomico 
		RandomAccessFile escrita = new RandomAccessFile("arquivo.bin", "rw");
		// define o ponteiro do arquivo na posição 0
		escrita.seek(0);
		// Escrever algo no arquivo
		escrita.writeUTF(" Hello World teste com dados gravados  \n");
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

public  void CriarArquivo() {
	try{
		FileOutputStream out = new FileOutputStream("arquivo.bin");  
		ObjectOutputStream os = new ObjectOutputStream(out);
		//Especifica o tamanho do arquivo 256MB
		byte[] buf = new byte[260880000];
		os.write(buf);
		os.flush();
		os.close();	       
			
	}catch(FileNotFoundException e){
		e.printStackTrace();
		System.out.println("Erro = "+e);  
	}catch(IOException e){
		e.printStackTrace();
		System.out.println("Erro = "+e);  
	}	
}	
	
	
}

