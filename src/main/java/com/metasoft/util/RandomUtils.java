package com.metasoft.util;

import java.math.BigInteger;
import java.util.Random;

public class RandomUtils {
	public static final int BASE = 10000;
	private static final Random random = new Random(System.currentTimeMillis());
	//private static final Random random = new SecureRandom();

	private static Random[] randoms = new Random[10];
	
	static {
		for (int i = 0; i < randoms.length; i++)
			randoms[i] = new Random(System.currentTimeMillis() ^ random.nextInt(Integer.MAX_VALUE));
	}

	private static Random getRandom() {
		int randomIndex = random.nextInt(randoms.length);
		return randoms[randomIndex];
	}

	public static int nextInt(int limit) {
		Random random = getRandom();
		return random.nextInt(limit);
	}

	public static int nextInt(int min, int max) {
		Random random = getRandom();
		int value = max - min + 1;
		return random.nextInt(value <= 0 ? 1 : value) + min;
	}

	public static boolean randomHit(int limit, int value) {
		if (value <= 0)
			return false;
		if (value >= limit)
			return true;
		int randomValue = nextInt(1, limit);
		return randomValue <= value;
	}

	public static int randomHit(int limit, int[] values) {
		int random = nextInt(1, limit);
		int rate = 0;
		for (int i = 0; i < values.length; i++) {
			rate += values[i];
			if (random <= rate) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 生成最高130位的随机字串
	 * @return
	 */
	public static String GetRandomString(){
		return new BigInteger(130, random).toString(32);//radix 进制
	}
	public static String GetRandomString(int numBits, int radix){
		return new BigInteger(numBits, random).toString(radix);//numBits power of 2 -1, radix 进制
	}
	
/*	private String getRandom(int length) {
		byte bytes[] = new byte[length];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return Hex.encodeHexString(bytes);
	}*/
	public static void main(String[] args) {
		for(int i=0; i<100; i++){
			System.out.println(RandomUtils.GetRandomString().substring(0, 3));
		}
	}
}