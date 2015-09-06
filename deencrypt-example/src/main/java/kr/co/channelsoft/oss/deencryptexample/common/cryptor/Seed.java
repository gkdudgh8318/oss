package kr.co.channelsoft.oss.deencryptexample.common.cryptor;

import kr.co.channelsoft.oss.aopdeencryptor.itfc.Cryptor;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component("cryptor")
@SuppressWarnings("unused")
public class Seed implements Cryptor {
	/**
	 * SEED의 암호화 시 사용될 Round 상수.
	 */
	static final int ROUND = 16;
	/**
	 * SEED의 암호화 시 사용될 블럭의 크기 상수.
	 */
	static final int BLOCK_SIZE = 16;
	/**
	 * SEED의 Round 상수.
	 */
	static final int KC0 = 0x9e3779b9;
	static final int KC1 = 0x3c6ef373;
	static final int KC2 = 0x78dde6e6;
	static final int KC3 = 0xf1bbcdcc;
	static final int KC4 = 0xe3779b99;
	static final int KC5 = 0xc6ef3733;
	static final int KC6 = 0x8dde6e67;
	static final int KC7 = 0x1bbcdccf;
	static final int KC8 = 0x3779b99e;
	static final int KC9 = 0x6ef3733c;
	static final int KC10 = 0xdde6e678;
	static final int KC11 = 0xbbcdccf1;
	static final int KC12 = 0x779b99e3;
	static final int KC13 = 0xef3733c6;
	static final int KC14 = 0xde6e678d;
	static final int KC15 = 0xbcdccf1b;
	
	/**
	 * SEED의 Key 생성 알고리즘 상수 K 배열 변수.
	 */
	static int [][] K;

	/**
	 * SEED의 S1 Box 배열 상수.
	 */	
	static final byte [] S1 = {
		-87, -123, -42, -45, 84, 29, -84, 37, 93, 67, 
		24, 30, 81, -4, -54, 99, 40, 68, 32, -99, 
		-32, -30, -56, 23, -91, -113, 3, 123, -69, 19, 
		-46, -18, 112, -116, 63, -88, 50, -35, -10, 116, 
		-20, -107, 11, 87, 92, 91, -67, 1, 36, 28, 
		115, -104, 16, -52, -14, -39, 44, -25, 114, -125, 
		-101, -47, -122, -55, 96, 80, -93, -21, 13, -74, 
		-98, 79, -73, 90, -58, 120, -90, 18, -81, -43, 
		97, -61, -76, 65, 82, 125, -115, 8, 31, -103, 
		0, 25, 4, 83, -9, -31, -3, 118, 47, 39, 
		-80, -117, 14, -85, -94, 110, -109, 77, 105, 124, 
		9, 10, -65, -17, -13, -59, -121, 20, -2, 100, 
		-34, 46, 75, 26, 6, 33, 107, 102, 2, -11, 
		-110, -118, 12, -77, 126, -48, 122, 71, -106, -27, 
		38, -128, -83, -33, -95, 48, 55, -82, 54, 21, 
		34, 56, -12, -89, 69, 76, -127, -23, -124, -105, 
		53, -53, -50, 60, 113, 17, -57, -119, 117, -5, 
		-38, -8, -108, 89, -126, -60, -1, 73, 57, 103, 
		-64, -49, -41, -72, 15, -114, 66, 35, -111, 108, 
		-37, -92, 52, -15, 72, -62, 111, 61, 45, 64, 
		-66, 62, -68, -63, -86, -70, 78, 85, 59, -36, 
		104, 127, -100, -40, 74, 86, 119, -96, -19, 70, 
		-75, 43, 101, -6, -29, 29, -79, -97, 94, -7, 
		-26, -78, 49, -22, 109, 95, -28, -16, -51, -120, 
		22, 58, 88, -44, 98, 41, 7, 51, -24, 27, 
		5, 121, -112, 106, 42, -102
	};
	
	/**
	 * SEED의 S2 Box 배열 상수.
	 */
	static final byte [] S2 = {
		56, -24, 45, -90, -49, -34, -77, -72, -81, 96, 
		85, -57, 68, 111, 107, 91, -61, 98, 51, -75, 
		41, -96, -30, -89, -45, -111, 17, 6, 28, -68, 
		54, 75, -17, -120, 108, -88, 23, -60, 22, -12, 
		-62, 69, -31, -42, 63, 61, -114, -104, 40, 78, 
		-10, 62, -91, -7, 13, -33, -40, 43, 102, 122, 
		39, 47, -15, 114, 66, -44, 65, -64, 115, 103, 
		-84, -117, -9, -83, -128, 31, -54, 44, -86, 52, 
		-46, 11, -18, -23, 93, -108, 24, -8, 87, -82, 
		8, -59, 19, -51, -122, -71, -1, 125, -63, 49, 
		-11, -118, 106, -79, -47, 32, -41, 2, 34, 4, 
		104, 113, 7, -37, -99, -103, 97, -66, -26, 89, 
		-35, 81, -112, -36, -102, -93, -85, -48, -127, 15, 
		71, 26, -29, -20, -115, -65, -106, 123, 92, -94, 
		-95, 99, 35, 77, -56, -98, -100, 58, 12, 46, 
		-70, 110, -97, 90, -14, -110, -13, 73, 120, -52, 
		21, -5, 112, 117, 127, 53, 16, 3, 100, 109, 
		-58, 116, -43, -76, -22, 9, 118, 25, -2, 64, 
		18, -32, -67, 5, -6, 1, -16, 42, 94, -87, 
		86, 67, -123, 20, -119, -101, -80, -27, 72, 121, 
		-105, -4, 30, -126, 33, -116, 27, 95, 119, 84, 
		-78, 29, 37, 79, 0, 70, -19, 88, 82, -21, 
		126, -38, -55, -3, 48, -107, 101, 60, -74, -28, 
		-69, 124, 14, 80, 57, 38, 50, -124, 105, -109, 
		55, -25, 36, -92, -53, 83, 10, -121, -39, 76, 
		-125, -113, -50, 59, 74, -73
	};

	/**
	 * 시스템 SEEDKey를 사용하여 암호화를 수행하는 SeedManager 생성자 
	 */
	Seed() throws Exception {
		K = new int[16][2];
		String key = "fhk5Kl7d4f59sa97";
		createKey(key);
	}
	
	/**
	 * 입력받은 SEEDKey를 사용하여 암호화를 수행하는 SeedManager 생성자 
	 */
	Seed(String key) throws Exception {
		K = new int[16][2];
		createKey(key);
	}
	
	/**
	 * F 함수
	 *
	 * @param i, j, k
	 * @return iValue
	 */
	private int[] functionF(int i, int j, int k) {
		int [] iValue = new int[2];
		int l = j ^ K[i][0];
		int i1 = k ^ K[i][1];
		int j1 = functionG(l ^ i1);
		int k1 = functionG(j1 + i1);
		iValue[0] = functionG(k1 + j1);
		iValue[1] = iValue[0] + k1;
		
		return iValue;
	}

	/**
	  * G 함수
	  * (참고) byte 자료형이 무조건 signed 자료형입니다.
	  * 따라서, 코드값 128을 넘어가는 것은 모두 음수로 처리됩니다.
	  * 즉, toHexString 메쏘드의 인자의 자료형은 int인데,
	  * 바이트 자료형을 넘겨주면, 형변환이 발생하여 내부적으로
	  * 부호 확장을 한 int값으로 전달됩니다.
	  * 이와 같이 전달된 int값을 toHexString()으로 출력하면 아주
	  * 커다란 수로 나오게 됩니다.
	  * 그래서, 확장된 부호값을 잘라내기 위하여, 
	  * 0xFF와 & 연산을 한 결과를 넘겨준 것입니다.
	  * 
	  * @param i
	  * @return j
	  */
	private int functionG(int i) {
		byte byte3 = (byte)i;
		byte byte2 = (byte)(i >> 8);
		byte byte1 = (byte)(i >> 16);
		byte byte0 = (byte)(i >> 24);
		byte byte4 = S2[byte0 & 0xff];
		byte byte5 = S1[byte1 & 0xff];
		byte byte6 = S2[byte2 & 0xff];
		byte byte7 = S1[byte3 & 0xff];
		int j = byte4;
		
		j = j << 8 | byte5 & 0xff;
		j = j << 8 | byte6 & 0xff;
		j = j << 8 | byte7 & 0xff;
		j = j << 4 | j >>> 28;
		
		return j;
	}
	
	/**
	 * 복호화 method
	 * 
	 * @param byte0 암호화된 byte [] value.
	 * @return byte1 복호화된 byte [] value.
	 */
    private byte [] decrypt(byte [] byte0) {
		int [] iValue0 = new int[17];
		int [] iValue1 = new int[17];
		int [] iValue2 = new int[17];
		int [] iValue3 = new int[17];
		int [] iValue4;
		int [] iValue5;
		int [] iValue6;
		int [] iValue7;
		byte [] byte1 = new byte[16];
		
		for(int inx=15; inx>=12; inx--)
			iValue0[16] = iValue0[16] << 8 | byte0[inx] & 0xff;

		for(int jnx=11; jnx>=8; jnx--)
			iValue1[16] = iValue1[16] << 8 | byte0[jnx] & 0xff;

		for(int knx=7; knx>=4; knx--)
			iValue2[16] = iValue2[16] << 8 | byte0[knx] & 0xff;

		for(int lnx=3; lnx>=0; lnx--)
			iValue3[16] = iValue3[16] << 8 | byte0[lnx] & 0xff;

		iValue4 = new int[2];
		for(int inx=16; inx>0; inx--) {
			if(inx%2 == 0) {
				iValue2[inx-1] = iValue2[inx];
				iValue3[inx-1] = iValue3[inx];
				iValue5 = functionF(inx-1, iValue3[inx], iValue2[inx]);
				iValue0[inx-1] = iValue0[inx] ^ iValue5[1];
				iValue1[inx-1] = iValue1[inx] ^ iValue5[0];
			} else
			if(inx%2 == 1 && inx != 1) {
				iValue0[inx-1] = iValue0[inx];
				iValue1[inx-1] = iValue1[inx];
				iValue6 = functionF(inx-1, iValue1[inx], iValue0[inx]);
				iValue2[inx-1] = iValue2[inx] ^ iValue6[1];
				iValue3[inx-1] = iValue3[inx] ^ iValue6[0];
			} else {
				iValue2[inx-1] = iValue0[inx];
				iValue3[inx-1] = iValue1[inx];
				iValue7 = functionF(inx-1, iValue1[inx], iValue0[inx]);
				iValue0[inx-1] = iValue2[inx] ^ iValue7[1];
				iValue1[inx-1] = iValue3[inx] ^ iValue7[0];
			} // if
		} // for

		byte1[15] = (byte)(iValue0[0] >>> 24 & 0xff);
		byte1[14] = (byte)(iValue0[0] >>> 16 & 0xff);
		byte1[13] = (byte)(iValue0[0] >>> 8 & 0xff);
		byte1[12] = (byte)(iValue0[0] & 0xff);
		byte1[11] = (byte)(iValue1[0] >>> 24 & 0xff);
		byte1[10] = (byte)(iValue1[0] >>> 16 & 0xff);
		byte1[9] = (byte)(iValue1[0] >>> 8 & 0xff);
		byte1[8] = (byte)(iValue1[0] & 0xff);
		byte1[7] = (byte)(iValue2[0] >>> 24 & 0xff);
		byte1[6] = (byte)(iValue2[0] >>> 16 & 0xff);
		byte1[5] = (byte)(iValue2[0] >>> 8 & 0xff);
		byte1[4] = (byte)(iValue2[0] & 0xff);
		byte1[3] = (byte)(iValue3[0] >>> 24 & 0xff);
		byte1[2] = (byte)(iValue3[0] >>> 16 & 0xff);
		byte1[1] = (byte)(iValue3[0] >>> 8 & 0xff);
		byte1[0] = (byte)(iValue3[0] & 0xff);
		
		return byte1;
	}

	/**
	 * 암호화 method 
	 * 
	 * @param byte0 암호화되지 않은 byte [] value.
	 * @return byte1 암호화된 byte [] value. 
	 */
	private byte [] encrypt(byte [] byte0) {
		int [] iValue = new int[17];
		int [] iValue1 = new int[17];
		int [] iValue2 = new int[17];
		int [] iValue3 = new int[17];
		int [] iValue4;
		int [] iValue5;
		int [] iValue6;
		int [] iValue7;
		byte [] byte1 = new byte[16];
		
		for(int inx=15; inx>=12; inx--)
			iValue[0] = iValue[0] << 8 | byte0[inx] & 0xff;

		for(int jnx=11; jnx>=8; jnx--)
			iValue1[0] = iValue1[0] << 8 | byte0[jnx] & 0xff;

		for(int knx=7; knx>=4; knx--)
			iValue2[0] = iValue2[0] << 8 | byte0[knx] & 0xff;

		for(int lnx=3; lnx>=0; lnx--)
			iValue3[0] = iValue3[0] << 8 | byte0[lnx] & 0xff;

		iValue4 = new int[2];
		for(int inx=0; inx<16; inx++) {
			if(inx%2 == 0) {
				iValue2[inx+1] = iValue2[inx];
				iValue3[inx+1] = iValue3[inx];
				iValue5 = functionF(inx, iValue3[inx], iValue2[inx]);
				iValue[inx+1] = iValue[inx] ^ iValue5[1];
				iValue1[inx+1] = iValue1[inx] ^ iValue5[0];
			} else
			if(inx%2 == 1 && inx != 15) {
				iValue[inx+1] = iValue[inx];
				iValue1[inx+1] = iValue1[inx];
				iValue6 = functionF(inx, iValue1[inx], iValue[inx]);
				iValue2[inx+1] = iValue2[inx] ^ iValue6[1];
				iValue3[inx+1] = iValue3[inx] ^ iValue6[0];
			} else {
				iValue2[inx+1] = iValue[inx];
				iValue3[inx+1] = iValue1[inx];
				iValue7 = functionF(inx, iValue1[inx], iValue[inx]);
				iValue[inx+1] = iValue2[inx] ^ iValue7[1];
				iValue1[inx+1] = iValue3[inx] ^ iValue7[0];
			} // if
		} // for

		byte1[15] = (byte)(iValue[16] >>> 24 & 0xff);
		byte1[14] = (byte)(iValue[16] >>> 16 & 0xff);
		byte1[13] = (byte)(iValue[16] >>> 8 & 0xff);
		byte1[12] = (byte)(iValue[16] & 0xff);
		byte1[11] = (byte)(iValue1[16] >>> 24 & 0xff);
		byte1[10] = (byte)(iValue1[16] >>> 16 & 0xff);
		byte1[9] = (byte)(iValue1[16] >>> 8 & 0xff);
		byte1[8] = (byte)(iValue1[16] & 0xff);
		byte1[7] = (byte)(iValue2[16] >>> 24 & 0xff);
		byte1[6] = (byte)(iValue2[16] >>> 16 & 0xff);
		byte1[5] = (byte)(iValue2[16] >>> 8 & 0xff);
		byte1[4] = (byte)(iValue2[16] & 0xff);
		byte1[3] = (byte)(iValue3[16] >>> 24 & 0xff);
		byte1[2] = (byte)(iValue3[16] >>> 16 & 0xff);
		byte1[1] = (byte)(iValue3[16] >>> 8 & 0xff);
		byte1[0] = (byte)(iValue3[16] & 0xff);
		
		return byte1;
	}

	/**
	 * Round Key 생성 method 
	 * 
	 * @param key 암호화에 사용될 Round Key를 생성하기 위한 SEEDKey.
	 */
	private void createKey(String key) throws Exception {
		byte [] byte0 = validateKey(key);
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		
		for(int knx1=3; knx1>=0; knx1--)
			i = i << 8 | byte0[knx1] & 0xff;

		for(int lnx1=7; lnx1>=4; lnx1--)
			j = j << 8 | byte0[lnx1] & 0xff;

		for(int inx2=11; inx2>=8; inx2--)
			k = k << 8 | byte0[inx2] & 0xff;

		for(int jnx2=15; jnx2>=12; jnx2--)
			l = l << 8 | byte0[jnx2] & 0xff;

		K[0][0] = functionG((i + k) - KC0);
		K[0][1] = functionG((j + KC0) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[1][0] = functionG((i + k) - KC1);
		K[1][1] = functionG((j + KC1) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
		K[2][0] = functionG((i + k) - KC2);
		K[2][1] = functionG((j + KC2) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[3][0] = functionG((i + k) - KC3);
		K[3][1] = functionG((j + KC3) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
		K[4][0] = functionG((i + k) - KC4);
		K[4][1] = functionG((j + KC4) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[5][0] = functionG((i + k) - KC5);
		K[5][1] = functionG((j + KC5) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
		K[6][0] = functionG((i + k) - KC6);
		K[6][1] = functionG((j + KC6) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[7][0] = functionG((i + k) - KC7);
		K[7][1] = functionG((j + KC7) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
		K[8][0] = functionG((i + k) - KC8);
		K[8][1] = functionG((j + KC8) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[9][0] = functionG((i + k) - KC9);
		K[9][1] = functionG((j + KC9) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
		K[10][0] = functionG((i + k) - KC10);
		K[10][1] = functionG((j + KC10) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[11][0] = functionG((i + k) - KC11);
		K[11][1] = functionG((j + KC11) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
		K[12][0] = functionG((i + k) - KC12);
		K[12][1] = functionG((j + KC12) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[13][0] = functionG((i + k) - KC13);
		K[13][1] = functionG((j + KC13) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
		K[14][0] = functionG((i + k) - KC14);
		K[14][1] = functionG((j + KC14) - l);
		i1 = j;
		j = j >>> 8 | i << 24;
		i = i >>> 8 | i1 << 24;
		K[15][0] = functionG((i + k) - KC15);
		K[15][1] = functionG((j + KC15) - l);
		j1 = l;
		l = l << 8 | k >>> 24;
		k = k << 8 | j1 >>> 24;
	}

	/**
	 * base64Decode method 
	 * 
	 * @param str base64 decoding할 String value.
	 * @return byte[] base64 decoding된 byte [] value.
	 * @throws Exception decoding error가 발생할 경우. 
	 */
	private byte[] base64Decode(String str) throws Exception {
		if (str == null) return null;
		return Base64.decodeBase64(str);
	}
	
	/**
	 * base64Encode method 
	 * 
	 * @param byte[] base64 encoding할 byte [] value.
	 * @return  String base64 encoding된 String value.
	 * @throws Exception encoding error가 발생할 경우. 
	 */
	private String base64Encode(byte[] data) throws Exception {
		if (data == null) return null;
		return Base64.encodeBase64String(data);
	}
	
	/**
	 * 암호화할 String의 크기가 16보다 작은 경우 크기를 16으로 
	 * 만들기 위해 space를 채울 때 사용하는 method 
	 * 
	 * @param src 원래 String value.
	 * @param size  늘이려는 문자열의 길이 int value의 size.
	 *  @param padStr size만큼 채울 문자열 String value.
	 * @return pattern 길이가 size인 String value.
	 */
	private String rPad(String src, int size, String padStr) {
		if (src == null) return null; 
		
		String pattern = src;
//		int patternLen = pattern.length();
		int patternLen = pattern.getBytes().length;
		for (int inx = patternLen; inx < size; inx++) {
			pattern = pattern + padStr;
		}
		
		return pattern;
	}

	/**
	 * key와 data를 validate하기 위해 특정 pattern과 비교시 사용하는 method 
	 * 
	 * @param target 비교할 String value.
	 * @param pattern target과 비교할 String value.
	 * @return pattern과 비교한 결과 boolean value(일치하는 것이 있으면 true). 
	 */
	private boolean match(String target, String pattern) {
		boolean matchFlag = true; 
		
		if ((target != null && target.length() > 0) && (pattern != null && pattern.length() > 0)) {
			int matchResult = 0;
			target = target.toLowerCase();
			for (int inx=0; inx < target.length() && matchFlag == true; inx++) {
				matchResult = pattern.indexOf(target.substring(inx, inx+1));
				if (matchResult < 0) matchFlag = false;
			}
		}
		
		return matchFlag;
	}

	/**
	 * 데이터를 암호화 하는 method 
	 * 
	 * @param orgStr 원시 String value.
	 * @return encStr 암호화된 String value.
	 */
	public String encrypt(String orgStr) throws Exception {
		String encStr = null;
		
		try {
			validateData(orgStr);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null; 
		}
		
		//암호화할 String의 크기를 16으로 만든다.
		orgStr = rPad(orgStr, 16, " ");
		
		byte [] orgBytes = orgStr.getBytes();		
		byte [] encBytes = encrypt(orgBytes);
		
		try {
			encStr = new String(base64Encode(encBytes));			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Base64 Encondig Exception Occurred.");
		}

		return encStr;
	}
	
	/**
	 * 데이터를 복호화하는 method 
	 * 
	 * @param encStr 암호화된 String value.
	 * @return decStr 복호화된 String value.
	 */
	public String decrypt(String encStr) throws Exception {
		if (encStr == null || encStr.length() == 0 || encStr.length() < 24) 
			throw new Exception("Encrypted String must be not null and not empty string.");
		
		String decStr = null;
		byte [] encBytes = null;

		try {
			encBytes = base64Decode(encStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Base64 Decondig Exception Occurred.");
		}
		
		byte [] decBytes = decrypt(encBytes);
		
		//String 크기를 맞추기 위해 추가했던 Space는 제거
		decStr = new String(decBytes).trim();
				
		return decStr;
	}

	/**
	 * 암호화된 data와 입력된 data가 동일한 data인지 비교하는 method 
	 * 
	 * @param targetStr 비교할 원시 String value.
	 * @param encStr 비교될 암호화된 String value. 
	 * @return boolean 비교결과 boolean value.
	 */
	boolean compareData(String targetStr, String encStr) throws Exception {
		boolean resultFlag = false;
		String encTargetStr = null;

		try {
			validateData(targetStr);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}
		
		//암호화할 String의 크기를 16으로 만든다.
		targetStr = rPad(targetStr, 16, " ");
		
		byte [] orgBytes = targetStr.getBytes();		
		byte [] encBytes = encrypt(orgBytes);
		
		try {
			encTargetStr = base64Encode(encBytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Base64 Encondig Exception Occurred.");
		}
		
		resultFlag = encTargetStr.equals(encStr);
				
		return resultFlag;
	}

	/**
	 * data를 검증하는 method 
	 * 
	 * @param target 검증할 대상의 String value.
	 * @throws Exception Validation error가 발생할 경우. 
	 */
	private void validateData(String target) throws Exception {
		if (target == null || target.getBytes().length > 16) {
			throw new Exception("Invalid Data length Exception Occurred. Maximum Data length must be 16 with no space.");
		} else if (!match(target, "0123456789abcdefghijklmnopqrstuvwxyz")) {
			throw new Exception("Invalid Data Exception Occurred. Data must be alphanumeric characters.");
		}
	}
	
	/**
	 * key를 검증하는 method 
	 * 
	 * @param keyStr 검증할 대상의 String value.
	 * @return keyBytes keyStr의 byte [] value.
	 * @throws Exception Validation error가 발생할 경우. 
	 */
	private byte [] validateKey(String keyStr) throws Exception {
		byte []  keyBytes = null;
		if (keyStr.length() != 16){
			throw new Exception("Invalid SEEDKey Exception Occurred. SEEDKey's length must be 16 with no space.");
		} else {
			if (match(keyStr, "0123456789abcdefghijklmnopqrstuvwxyz")) {
				keyBytes = keyStr.getBytes();
			} else {
				throw new Exception("Invalid SEEDKey Exception Occurred. SEEDKey must be alphanumeric characters.");
			}
			if (keyBytes == null || keyBytes.length != 16) {
				throw new Exception("Invalid SEEDKey Exception Occurred. SEEDKey's length must be 16 with no space.");
			}
		}
		return keyBytes;
	}

	public static void main(String[] args) {

		try {
//		    args = new String[] {"e", "0123456789abcdef", "1234567890ab"};
		    args = new String[] {"d", "0123456789abcdef", "91WMrZS8rwNjPkMfeaFOrQ=="};
		    
			if (args == null || args.length < 2) {
				System.err.println("# 사용법   : java -Dlaf.home=[lafj 디렉토리] -cp ./common.jar lgcns.crypto.seed.SeedManager option param1 param2");
				System.err.println(" - option   : b[키 복호화]/c[키 암호화]/d[데이터 복호화]/e[데이터 암호화]");
				System.err.println(" - param1 : 암호화 또는 복호화 할 키값");
				System.err.println(" - param2 : 암호화 또는 복호화 할 데이터\n");
				System.err.println("# Example");
				System.err.println(" - 키 암호화 파라미터     : c 0123456789abcdef");
				System.err.println(" - 키 복호화 파라미터     : b MZU7ZlAkxzFlVWoQ4ITzOg==");
				System.err.println(" - 데이터 암호화 파라미터 : e 0123456789abcdef 0000");
				System.err.println(" - 데이터 복호화 파라미터 : d 0123456789abcdef MZU7ZlAkxzFlVWoQ4ITzOg==\n\n");
				System.exit(0);
			}
			
			Seed seedMgr = null;
			String keyStr = null;
			String encKeyStr = null;
			String encStr = null;
			String decStr = null;
			
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("b")) {//Decrypt Key
					seedMgr = new Seed();
					keyStr = seedMgr.decrypt(args[1]);
				} else if (args[0].equalsIgnoreCase("c")) {//Encrypt Key
					seedMgr = new Seed();
					encKeyStr = seedMgr.encrypt(args[1]);
				}				
			} else if (args.length == 3) {
				if (args[0].equalsIgnoreCase("d")) {//Decrypt Data
					seedMgr = new Seed(args[1]);
					decStr = seedMgr.decrypt(args[2]);
				} else if (args[0].equalsIgnoreCase("e")) {//Encrypt Data
					seedMgr = new Seed(args[1]);
					encStr = seedMgr.encrypt(args[2]);
				}
			}
			
			System.out.println("# 사용법   : java -Dlaf.home=[lafj 디렉토리] -cp ./common.jar lgcns.crypto.seed.SeedManager option param1 param2");
			System.out.println(" - option   : b[키 복호화]/c[키 암호화]/d[데이터 복호화]/e[데이터 암호화]");
			System.out.println(" - param1 : 암호화 또는 복호화 할 키값");
			System.out.println(" - param2 : 암호화 또는 복호화 할 데이터\n");
			System.out.println("# Example");
			System.out.println(" - 키 암호화 파라미터     : c 0123456789abcdef");
			System.out.println(" - 키 복호화 파라미터     : b MZU7ZlAkxzFlVWoQ4ITzOg==");
			System.out.println(" - 데이터 암호화 파라미터 : e 0123456789abcdef 0000");
			System.out.println(" - 데이터 복호화 파라미터 : d 0123456789abcdef MZU7ZlAkxzFlVWoQ4ITzOg==\n\n");			
			System.out.println("# 수행결과: ");
			if (encKeyStr != null) 
				System.out.println("> 키 암호화[" + args[1] +"] ==> ["+encKeyStr+"]");
			if (keyStr != null) 
				System.out.println("> 키 복호화[" + args[1] +"] ==> ["+keyStr+"]");
			if (encStr != null)
				System.out.println("> 데이터 암호화[" + args[2] + "] ==> ["+encStr+"]");
			if (decStr != null)
				System.out.println("> 데이터 복호화[" + args[2] + "] ==> ["+decStr+"]");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
}