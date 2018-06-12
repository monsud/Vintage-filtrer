package filtro;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class filtroImage {
	public static void main(String[] args){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//Carico l'immagine della copertina dell'album Thriller di MJ
		Mat mat = Imgcodecs.imread("D:\\opencv\\images\\27.jpg");
		imshow("Filtro", mat);
		
		//CALCULATE LAPLACIAN
		Mat mat2 = new Mat(mat.rows(), mat.cols(), CvType.CV_8UC1);
		
		//SOBEL KERNEL
        Mat kernel_sobel = new Mat(3,3, CvType.CV_32F){
            {
				put(0,0,-1);
				put(0,1,0);
				put(0,2,1);
				
				put(1,0-2);
				put(1,1,0);
				put(1,2,2);
				
				put(2,0,-1);
				put(2,1,0);
				put(2,2,1);
            }
        };
        
		//LAPLACIAN KERNEL
		Mat kernel_laplacian = new Mat(3,3, CvType.CV_32F){
		   {
				put(0,0,0);
				put(0,1,-1);
				put(0,2,0);
				
				put(1,0-1);
				put(1,1,4);
				put(1,2,-1);
				
				put(2,0,0);
				put(2,1,-1);
				put(2,2,0);
		   }
		};

		Imgproc.filter2D(mat, mat2, -1, kernel_laplacian);
		imshow("Filtro (Laplacian)", mat2);
		Imgproc.filter2D(mat, mat2, -1, kernel_sobel);
		imshow("Filtro (Sobel)", mat2);
	}
	
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
}



