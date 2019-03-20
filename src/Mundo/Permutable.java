package Mundo;

public class Permutable {

	private static int[][] TABLE = null;
	
	public static int[] permute(int[] bloco) {
		int sizeX = TABLE.length;
		int sizeY = TABLE[0].length;

		int[] temp = new int[sizeX*sizeY];
		for(int x=0; x<sizeX; x++) {
			for(int y=0; y<sizeY; y++) {
				temp[x*sizeY+y] = bloco[TABLE[x][y]-1];
			}
		}
	
		return temp;
	}
}
