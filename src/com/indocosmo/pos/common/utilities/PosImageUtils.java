package com.indocosmo.pos.common.utilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.indocosmo.pos.common.Colorize;


public class PosImageUtils {

	/**
	 * Colorize the image
	 * @param image 
	 * @param red component value
	 * @param green component value
	 * @param blue component value
	 */
	public static void colorize(BufferedImage image, int red, int green, int blue){
		Colorize col=new Colorize(red, green, blue);
		col.doColorize(image);
	}

	public static void colorize(BufferedImage image, double t_hue, double t_sat, double t_bri){
		Colorize col=new Colorize(t_hue, t_sat, t_bri);
		col.doColorize(image);
	}
	
	//encodes an image to base64 format
	public static String encodeToBase64(BufferedImage image,String type) throws IOException{
		BufferedImage iImage =image;
		String base64String=null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ImageIO.write(iImage,type, bout);
		BASE64Encoder encoder = new BASE64Encoder();
		base64String = encoder.encode(bout.toByteArray());
		bout.close();
		return base64String;
	}

	//decodes an image from base64 format.
	public static BufferedImage decodeFromBase64(String imgString) throws IOException{
		String base64String = imgString;
		BufferedImage image;
		byte[] imagebyte;
		BASE64Decoder decoder = new BASE64Decoder();
		imagebyte = decoder.decodeBuffer(base64String);
		ByteArrayInputStream bin = new ByteArrayInputStream(imagebyte);
		image=ImageIO.read(bin);
		bin.close();
		return image;
	}
}
