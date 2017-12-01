package bloomfilter;

public class HashFunction {
	
	public static long hasFunction1(String str, int s) {
		int strIndex3 = 0;
		int h1;
		for(h1 = 0; strIndex3 < str.length(); strIndex3++) 
		{
			h1 = 157 * h1 + (0xFF & (int)str.charAt(strIndex3));
		}

		int strIndex = 0;
		long h;
		for(h = Integer.toUnsignedLong(0); strIndex < str.length(); strIndex++) {
			h = Integer.toUnsignedLong(157) * h + (Integer.toUnsignedLong(0xFF) & (long)str.charAt(strIndex));
		}
		return Long.remainderUnsigned(h+h1, Integer.toUnsignedLong(s));
		
	}
		
		public static long hasFunction2(String str, int s) {
		
		int hash = str.length();
		for (int i = 0; i < str.length(); i++) {
		    hash = Math.abs(hash*31 + str.charAt(i));
		}
		return hash%s;
		

	}
		
		public static long hasFunction3(String str, int s) {
			
			int hash = 0;
			for (int i = 0; i < str.length(); i++) {
			  hash = ((hash << 5) - hash + str.charAt(i))%s;
			}
			return hash;
	}
		
		
	public static long hasFunction4(String str, int s) {
		 int intLength = str.length() / 4;
	     long sum = 0;
	     for (int j = 0; j < intLength; j++) {
	       char c[] = str.substring(j * 4, (j * 4) + 4).toCharArray();
	       long mult = 1;
	       for (int k = 0; k < c.length; k++) {
		 sum += c[k] * mult;
		 mult *= 256;
	       }
	     }

	     char c[] = str.substring(intLength * 4).toCharArray();
	     long mult = 1;
	     for (int k = 0; k < c.length; k++) {
	       sum += c[k] * mult;
	       mult *= 256;
	     }

	     return(Math.abs(sum)%s);
	 }

}
