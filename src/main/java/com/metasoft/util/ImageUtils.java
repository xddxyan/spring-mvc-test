package com.metasoft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 图片处理工具类，主要提供图片裁减功能。
 * 
 * @author Wangej
 * 
 */
public class ImageUtils {

	/**
	 * 图像处理，先裁剪成正方形，然后按比列缩放。
	 * 
	 * @param stream
	 *            原始图片二进制流
	 * @param path
	 *            图片存放物理路径
	 * @param id
	 *            图片ID，和page对应。
	 */
	public static void resize(BufferedImage srouceImage, int x, int y,
			int length, String dest, String id) {
		try {
			Integer[] sizes = new Integer[] { 150, 80 };
			for (int size : sizes) {
				File file = new File(dest, id + "-" + size + ".jpg");
				if (file.exists()) {
					file.delete();
				}
				Thumbnails.of(srouceImage).sourceRegion(x, y, length, length)
						.size(size, size).outputQuality(1).toFile(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片处理
	 * @param srouceImage 原始图片二进制流
	 * @param x 裁剪起始点x坐标
	 * @param y 裁剪起始点y坐标
	 * @param width 裁剪宽度
	 * @param height 裁剪高度
	 * @param targetWidth 裁剪后图片宽度
	 * @param targetHeight 裁剪后图片高度
	 * @param dest 图片存放目录
	 * @param name 图片name
	 * @param type 图片格式
	 */
	public static void resize(BufferedImage srouceImage, int x, int y,
			int width, int height, int targetWidth, int targetHeight, String dest, String name, String type) {
		try {
			File file = new File(dest, name + "." + type);
			if (file.exists()) {
				file.delete();
			}
			Thumbnails.of(srouceImage).sourceRegion(x, y, width, height)
					.size(targetWidth, targetHeight).outputQuality(1).toFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean resize(File src, File dest, int height) {
		try {
			if (dest.exists()) {
				dest.delete();
			}
			BufferedImage srcBuffer = ImageIO.read(src);
			if (srcBuffer.getWidth() / srcBuffer.getHeight() > 2) {
				return false;
			}
			if (srcBuffer.getWidth() > 200 && srcBuffer.getHeight() > 200) {
				int width = srcBuffer.getWidth() * height
						/ srcBuffer.getHeight();
				Thumbnails.of(srcBuffer).size(width, height).outputQuality(1)
						.toFile(dest);
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean resize(File src, OutputStream os, int width,
			int height) {
		try {
			BufferedImage srcBuffer = ImageIO.read(src);
			if (srcBuffer.getWidth() > 600) {
				Thumbnails.of(src).size(600, 10000).outputQuality(1)
						.toOutputStream(os);
			} else {
				FileUtils.copyFile(src, os);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
