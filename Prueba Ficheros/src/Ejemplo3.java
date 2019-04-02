import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Ejemplo3 {

	public static void main(String[] args) {
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream("C:\\Users\\aula9\\Desktop\\datos\\prueba"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

}
