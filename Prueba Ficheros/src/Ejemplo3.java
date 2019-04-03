import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ejemplo3 {

	public static void main(String[] args) {
		DataInputStream in = null;
		try {
			in = new DataInputStream(new BufferedInputStream(new FileInputStream("C:\\Users\\aula9\\Desktop\\datos\\parrot.bmp")));
			String tipo = "";
			tipo += (char) in.read();
			tipo += (char) in.read();
			System.out.println("Tipo: " + tipo);
			System.out.println("Longitud del fichero: " + Integer.reverseBytes(in.readInt()));		 //Se utiliza para numeros enteros de mas de un byte.
			in.skip(4);		//4 bytes que no se necesitan.
			int offset = Integer.reverseBytes(in.readInt());
			System.out.println("Offset de la imagen: " + offset);
			System.out.println("Longitud cabecera de la imagen: " + Integer.reverseBytes(in.readInt()));
			int width= Integer.reverseBytes(in.readInt());
			System.out.println("Ancho: " + width);
			int height = Integer.reverseBytes(in.readInt());
			System.out.println("Alto: " + height);
			System.out.println("Planos: " + Short.reverseBytes(in.readShort()));		//Al almacenarse solo en 2 bytes, se utiliza short.
			int colorDepth = Short.reverseBytes(in.readShort());
			System.out.println("Profundidad de color: " + colorDepth);
			System.out.println("Tipo de compresion: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Tamaño de la imagen: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Resolucion horizontal: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Resolucion vertical: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Número de colores: " + Integer.reverseBytes(in.readInt()));
			System.out.println("Número de colores importantes: " + Integer.reverseBytes(in.readInt()));
			
			in.skip(offset - 54);
			
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			for(int f=height-1; f>=0; f--) {
				for(int c=0; c<width; c++) {
					int pixel = 0;
					if(colorDepth == 24) {
						pixel += in.read();
						pixel += (in.read() << 8);
						pixel += (in.read() << 16);
						pixel += (in.read() << 24);
						pixel |= 255 << 24;
						pixel |= 0xff000000;
					}else if (colorDepth ==32) {
						pixel = Integer.reverseBytes(in.readInt() << 8);
						pixel |= 255 << 24;
//						pixel |= 0xff000000;
					}
					image.setRGB(c, f, pixel);
				}
				in.skip(width % 4);
			}
			
			JFrame ventana = new JFrame("Visor de BMP");
			ventana.getContentPane().add(new Surface(image));
			ventana.pack();
			ventana.setLocationRelativeTo(null);
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			SwingUtilities.invokeLater(() -> {ventana.setVisible(true);}); 
					
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
