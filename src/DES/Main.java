package DES;
import java.util.Arrays;
public class Main {
//opa
	public static void main(String[] args) {
		Tabela t = new Tabela();
		
		int[] bloco = t.getINPUT_TESTE_INICIAL();
		int[] chave = t.getKEY();
		
		bloco = faisel(bloco, chave);

		//System.out.println("------------------------------------------");
		//System.out.println("Tamanho "+ bloco.length);
		for(int i=0; i<bloco.length; i++) {
			//System.out.println(bloco[i]);
			//System.out.println(String.format("%02x", (int) bloco[i]));
			//System.out.println( String.format( "%06d", Integer.parseInt(Integer.toString(bloco[i],2))));
			//System.out.println(Integer.toString(bloco[i],2));
		}
	}
	//--------------------------------------------------------------------------------------------------------------------------
	
	public static int[] permutacao(int[] bloco, int[][] tabelaIP) {
		int sizeX = tabelaIP.length;
		int sizeY = tabelaIP[0].length;

		int[] temp = new int[sizeX*sizeY];
		
		for(int x=0; x<sizeX; x++) {
			for(int y=0; y<sizeY; y++) {
				temp[x*sizeY+y] = bloco[tabelaIP[x][y]-1];
			}
		}
	
		return temp;
	}
	
	public static char[] permutacao(char[] bloco, int[][] tabelaIP) {
		int sizeX = tabelaIP.length;
		int sizeY = tabelaIP[0].length;

		char[] temp = new char[sizeX*sizeY];
		
		for(int x=0; x<sizeX; x++) {
			for(int y=0; y<sizeY; y++) {
				temp[x*sizeY+y] = bloco[tabelaIP[x][y]-1];
			}
		}
	
		return temp;
	}
	public static int[] rotacao(int[] bloco) {
		int[] result = new int[bloco.length];
		int tamanhoDoBloco = bloco.length;
		int[] c = Arrays.copyOfRange(bloco, 0, tamanhoDoBloco/2);
		int[] d = Arrays.copyOfRange(bloco, tamanhoDoBloco/2, tamanhoDoBloco);
		int lastPosition;
		
		lastPosition = c[0];
		for(int i=0; i<c.length-1; i++) {
			c[i] = c[i+1];	
		}
		c[c.length-1] = lastPosition;
		
		lastPosition = d[0];
		for(int i=0; i<d.length-1; i++) {
			d[i] = d[i+1];
		}
		d[d.length-1] =lastPosition;

		//Copia a array C para a Array de resultado. 28 posicoes de 56
		System.arraycopy(c, 0, result, 0, c.length);
		//Copia a array D para a Array de resultado, as posicoes que faltaram a partir da 28
		System.arraycopy(d, 0, result, c.length, d.length);
		return result;
	}
	
	public static int[] dividir(int[] bloco) {
		int tamanho = bloco.length;
		int[] temp = new int[tamanho/2];
		for(int i=0;i<tamanho/2; i++) {
			temp[i] = bloco[i];
		}
		return temp;
	}
	
	public static char[] dividir(char[] bloco) {
		int tamanho = bloco.length;
		char[] temp = new char[tamanho/2];
		for(int i=0;i<tamanho/2; i++) {
			temp[i] = bloco[i];
		}
		return temp;
	}
	
	public static int[] keyTransform(int[] chave) {
		Tabela t = new Tabela();
		chave = rotacao(chave);
		chave = permutacao(chave, t.getTABLE_PC_MENOS_2());
		
		return chave;
	}
	
	public static char[] faisel(char[] bloco, char[] chave) {
		bloco = dividir(bloco);
		bloco = permutacao(bloco, new Tabela().getTABLE_EXPANSION_E());
		//bloco = xor(bloco,chave);
		char[] temp = new char[bloco.length];
		for(int i=0; i<bloco.length; i++) {
			String s = String.format( "%06d", Integer.parseInt(Integer.toString(bloco[i],2)));
			int x = Integer.parseInt(String.valueOf(s.charAt(0)) + String.valueOf(s.charAt(5)),2);
			int y = Integer.parseInt(
						String.valueOf(s.charAt(1))+
						String.valueOf(s.charAt(2))+
						String.valueOf(s.charAt(3))+
						String.valueOf(s.charAt(4))
					,2);
			int[][] tabela = new Tabela().getSbox1();
			String c = String.format("%04d", Integer.parseInt(Integer.toBinaryString(tabela[x][y])));
			char car = (char)Integer.parseInt(c,2);
			temp[i] = car;

		}
		return temp;
	}
	
	public static int[] faisel(int[] bloco, int[] chave) {
		bloco = new Tabela().getINPUT_TESTE_BIN();
		chave = new Tabela().getKEY_CHAR_BIN();
		bloco = dividir(bloco);
		bloco = permutacao(bloco, new Tabela().getTABLE_EXPANSION_E());
		bloco = xor(bloco,chave);
		int[] temp = new int[bloco.length];
		System.out.println(bloco.length);
		for(int i=0; i<bloco.length; i=i+8) {
			String x = 	""+
						bloco[i+0] +
						bloco[i+7]; 
			String y = ""+
					bloco[i+1] + 
					bloco[i+2] + 
					bloco[i+3] + 
					bloco[i+4] + 
					bloco[i+5] + 
					bloco[i+6];
						
			System.out.println(x+","+y);
		}
		return temp;
	}
	
	private static int[] xor(int[] bloco, int[] chave){
		int[] temp = bloco;
		for(int i=0; i<bloco.length;i++) {
			temp[i] = bloco[i] ^ chave[i];
		}
		return temp;
	}
	
	private static char[] xor(char[] bloco, char[] chave){
		char[] temp = new char[bloco.length];
		for(int i=0; i<bloco.length;i++) {
			temp[i] =  (char) (bloco[i]^chave[i]);
		}
		return temp;
	}
}