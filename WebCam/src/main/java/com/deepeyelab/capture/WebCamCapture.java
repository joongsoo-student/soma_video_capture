package com.deepeyelab.capture;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class WebCamCapture extends Thread implements Capture {
	private static WebCamCapture instance;
	private Mat currentFrame;
	
	static { 
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    System.setProperty("java.awt.headless", "true");
	 }
	
	private WebCamCapture() {}
	
	public static WebCamCapture getInstance() {
		if(instance == null) {
			instance = new WebCamCapture();
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
	        try {
				sleep(30);
			} catch (InterruptedException e) {
			}
	    }
	}
	
	@Override
	public byte[] getCurrentFrame() {
		int bufferSize = currentFrame.channels()*currentFrame.cols()*currentFrame.rows();
        byte [] b = new byte[bufferSize];
        currentFrame.get(0,0,b);
        BufferedImage image = new BufferedImage(currentFrame.cols(), currentFrame.rows(), BufferedImage.TYPE_3BYTE_BGR);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
		
        return bufferedImageToJPG(image);
	}
	
	private byte[] bufferedImageToJPG(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", baos);
		} catch (IOException e) {
		}
		byte[] bytes = baos.toByteArray();
		
		return bytes;
	}
}
