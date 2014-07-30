package com.examw.netplatform.service.test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class HN_VerifyCode {
	static int SW = 30; // ��ֵ
	static int iw = 6; // ��ͼ���
	static int ih = 9; // ��ͼ�߶�
	static int[][] vlaue = {
	// num 0; 6*9 ��ģ
			{ 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1,
					1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1 },
			// num 1
			{ 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0,
					1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0 },
			// num2
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1,
					1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1 },
			// num3
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0,
					1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, },
			// num4
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0,
					0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1 },
			// num5
			{ 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1,
					0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1 },
			// num6
			{ 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1,
					1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1 },
			// num7
			{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1,
					1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
			// num8
			{ 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1,
					1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1 },
			// num9
			{ 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0,
					1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1 } };

	public static String getValidate(InputStream im) {
		int pix[];
		String valicode = "";
		BufferedImage img;
		try {
			img = ImageIO.read(im);
			// ImageIO.write(img, "BMP", new File("C:\\b.bmp"));
			// 4��6*9����
			BufferedImage newim[] = new BufferedImage[4];
			newim[0] = img.getSubimage(5, 2, iw, ih);
			newim[1] = img.getSubimage(12, 2, iw, ih);
			newim[2] = img.getSubimage(19, 2, iw, ih);
			newim[3] = img.getSubimage(26, 2, iw, ih);

			for (int k = 0; k < 4; k++) {
				// ��ֵ��
				pix = new int[iw * ih];
				for (int i = 0; i < ih; i++) {
					for (int j = 0; j < iw; j++)
						pix[i * iw + j] = (newim[k].getRGB(j, i) & 0xff) > SW ? 1 : 0;
				}
				valicode += getMatchNum(pix);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valicode;
	}

	public static int getMatchNum(int[] pix) {
		int result = -1;
		int temp = 100;
		int x;
		for (int k = 0; k <= 9; k++) {
			x = 0;
			for (int i = 0; i < iw * ih; i++)
				x = x + Math.abs(pix[i] - vlaue[k][i]);
			if (x < temp) {
				temp = x;
				result = k;
			}
		}
		return result;
	}
}
