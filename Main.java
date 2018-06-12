package filtro;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {
	
    static double alfa=0.5,beta;
    static Mat src1,src2,dst; //matrici che mi serviranno per inserire le immagini
    static URL img1_url,img2_url;
    static String ruta1="C:/images/27.jpg",
            	  ruta2="C:/images/Vfiltro.jpg";
    
    public static void main(String args[]){
        System.out.println(Core.NATIVE_LIBRARY_NAME);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
 
        src1 = Imgcodecs.imread(ruta1,Imgcodecs.CV_LOAD_IMAGE_COLOR);
        src2 = Imgcodecs.imread(ruta2,Imgcodecs.CV_LOAD_IMAGE_COLOR);
 
		//Controllo se l'immagine viene caricata altrimenti genero un messaggio di errore
        if( src1.empty() ) { System.out.println("Errore nel caricamento dell'immagine 1 \n");}
        if( src2.empty() ) { System.out.println("Errore nel caricamento dell'immagine 2 \n");}
 
        dst = new Mat();
        beta = 1.0 - alfa;
        Core.addWeighted(src1, alfa, src2, beta, 0.0, dst);
        
        //Interface Grafic
        Finestra v = new Finestra();
        
        v.setImage(convertir(dst));
    }
    //Questa funzione ci permette di fondere il filtro invecchiamento e il filtro Blur
    public static void imshow(String windowname, Mat m){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( m.channels() > 1 ) 
			type = BufferedImage.TYPE_3BYTE_BGR;
		int bufferSize = m.channels()*m.cols()*m.rows();
		byte [] b = new byte[bufferSize];
		m.get(0,0,b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		ImageIcon icon=new ImageIcon(image);
		JFrame frame=new JFrame(windowname);
		JLabel lbl=new JLabel(icon);
		frame.add(lbl);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
 
    private static Image convertir(Mat image) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, matOfByte); 
 
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
 
        try {
 
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            Imgcodecs.imwrite("C:/images/fusion.jpg", image);
            
            //Filtro Blur
            Mat mat = Imgcodecs.imread("C:/images/fusion.jpg");
            Mat mat2 = mat.clone();
            Imgproc.medianBlur(mat, mat2, 5);
    		imshow("medianBlur (3px)", mat2);
    		
            Imgcodecs.imwrite("C:/images/f2.jpg", mat2);
    		

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Image)bufImage;
    }
}