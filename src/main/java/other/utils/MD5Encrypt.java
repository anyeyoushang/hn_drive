package other.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * MD5加密
 * 
 */
public class MD5Encrypt {

	/**
	 * 對字符串進行MD5加密
	 * 
	 * @param s
	 * @return 加密後的字符串
	 */
	public final static String MD5(String s) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			if (s != null && !"".equals(s.trim())) {
				byte[] strTemp = s.getBytes();
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(strTemp);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} else {
				return "";
			}
		} catch (Exception e) {
			return null;
		}

	}

	public static void main(String[] args) throws Exception {
		// 000000 : 670b14728ad9902aecba32e22fa4f6bd
		//System.out.print(MD5Encrypt.MD5("000000"));
		//999999: 52c69e3a57331081823331c4e69d3f2e
		//System.out.print(MD5Encrypt.MD5("1"));
//		System.out.println("010101"+String.format("%02d", 2+1));
		
		//System.out.println(MD5("fJKKKonaa12ASDfdfdQW"));
		
		String str = "w我";
		byte[] bytes = str.getBytes();
		String s = new String(bytes, "utf-8");
		System.out.println(s);
		System.out.println(bytes.toString());
	}

}