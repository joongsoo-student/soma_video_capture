package net.skhu.cam;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class CamManager extends Thread {
	private static CamManager instance;
	private Mat currentFrame;
	
	static { 
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    System.setProperty("java.awt.headless", "true");
	 }
	
	private CamManager() {}
	
	public static CamManager getInstance() {
		if(instance == null) {
			instance = new CamManager();
			instance.currentFrame = new Mat();
		}
		return instance;
	}
	
	@Override
	public void run() {
		VideoCapture cap = new VideoCapture(0);
		 
	    ReentrantLock lock = new ReentrantLock();
	    while (true) {
	    		lock.lock();
	        cap.read(currentFrame);
	        lock.unlock();
	    }
	}
	
	public byte[] getCurrentFrame() {
		int bufferSize = currentFrame.channels()*currentFrame.cols()*currentFrame.rows();
        byte [] b = new byte[bufferSize];
        currentFrame.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(currentFrame.cols(), currentFrame.rows(), BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
		
        return bufferedImageToJPG(image);
	}
	
	private byte[] bufferedImageToJPG(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		boolean foundWriter;
		try {
			foundWriter = ImageIO.write(image, "jpg", baos);
			assert foundWriter;
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		
		return bytes;
	}
}
