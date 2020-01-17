package com.itheima.util;

import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;



public class Check_Image_Info {

	private static Pattern pattern = Pattern.compile("[0-9]*");

	/*
	 * 判断arr(长度*高度)的分辨率是否在str(长度*高度*波动范围)的分辨率里面 若在返回true 不在则返回false
	 */
	public static boolean Check_resolution(String arr, String str) {
		boolean result = true;
		String[] temp1 = new String[2];
		String[] temp2 = new String[3];
		temp1 = arr.split("[*]");
		temp2 = str.split("[*]");
		if (Integer.parseInt(temp1[0]) < (Integer.parseInt(temp2[0]) - Integer.parseInt(temp2[2]))
				|| Integer.parseInt(temp1[0]) > (Integer.parseInt(temp2[0]) + Integer.parseInt(temp2[2]))
				|| Integer.parseInt(temp1[1]) < (Integer.parseInt(temp2[1]) - Integer.parseInt(temp2[2]))
				|| Integer.parseInt(temp1[1]) > (Integer.parseInt(temp2[1]) + Integer.parseInt(temp2[2]))) {
			result = false;
		}

		return result;
	}

	/*
	 * 判断arr的采集时间是否在str(开始时间~结束时间)里面 若在返回true 不在则返回false
	 */
	public static boolean Check_acquisition_time(String arr, String str) {
		boolean result = true;
		String[] temp = new String[2];
		temp = str.split("~");
		if (arr.compareTo(temp[0]) < 0 || arr.compareTo(temp[1]) > 0) {
			result = false;
		}
		return result;
	}

	/*
	 * 判断arr的时长是否处于str间(时长*波动范围) 若在返回true 不在则返回false
	 */
	public static boolean Check_acquisition_long(String arr, String str) {
		boolean result = true;
		String[] temp = new String[2];
		temp = str.split("[*]");
		if (Integer.parseInt(arr) < (Integer.parseInt(temp[0]) - Integer.parseInt(temp[1]))
				|| Integer.parseInt(arr) > (Integer.parseInt(temp[0]) + Integer.parseInt(temp[1]))) {
			result = false;
		}
		return result;
	}

	/*
	 * 判断arr的经度在不在str(xW(E)~yW(E)) 若在返回true 不在则返回false
	 */
	public static boolean Check_longitude_range(String arr, String str) {

		boolean result = true;
		String[] ptr1 = new String[2];
		ptr1 = arr.split("~");
		String[] temp1 = new String[4];
		temp1[0] = ptr1[0].substring(0, ptr1[0].length() - 1);
		temp1[1] = ptr1[0].substring(ptr1[0].length() - 1, ptr1[0].length());
		temp1[2] = ptr1[1].substring(0, ptr1[1].length() - 1);
		temp1[3] = ptr1[1].substring(ptr1[1].length() - 1, ptr1[1].length());
		int position_arr_start = Integer.parseInt(temp1[0]);
		if (temp1[1].equals("E")) {
			position_arr_start += 180;
		}
		int position_arr_end = Integer.parseInt(temp1[2]);
		if (temp1[3].equals("E")) {
			position_arr_end += 180;
		}
		if (position_arr_end < position_arr_start) {
			position_arr_end += 360;
		}

		String[] ptr2 = new String[2];
		ptr2 = str.split("~");
		String[] temp2 = new String[4];
		temp2[0] = ptr2[0].substring(0, ptr2[0].length() - 1);
		temp2[1] = ptr2[0].substring(ptr2[0].length() - 1, ptr2[0].length());
		temp2[2] = ptr2[1].substring(0, ptr2[1].length() - 1);
		temp2[3] = ptr2[1].substring(ptr2[1].length() - 1, ptr2[1].length());

		int position_str_start = Integer.parseInt(temp2[0]);
		if (temp2[1].equals("E")) {
			position_str_start += 180;
		}
		int position_str_end = Integer.parseInt(temp2[2]);
		if (temp2[3].equals("E")) {
			position_str_end += 180;
		}
		if (position_str_end < position_str_start) {
			position_str_end += 360;
		}
		if (position_arr_start < position_str_start || position_arr_start > position_str_end
				|| position_arr_end < position_str_start || position_arr_end > position_str_end) {
			result = false;
		}
		return result;
	}

	/*
	 * 判断arr的纬度在不在str(xN(S)~yN(S)) 若在返回true 不在则返回false
	 */
	public static boolean Check_latitude_range(String arr, String str) {

		boolean result = true;
		String[] ptr1 = new String[2];
		ptr1 = arr.split("~");
		int[] temp = new int[2];
		temp[0] = Integer.parseInt(ptr1[0].substring(0, ptr1[0].length() - 1));
		if (ptr1[0].substring(ptr1[0].length() - 1, ptr1[0].length()).equals("N")) {
			temp[0] += 90;
		} else if (ptr1[0].substring(ptr1[0].length() - 1, ptr1[0].length()).equals("S")) {
			temp[0] = 90 - temp[0];
		}
		temp[1] = Integer.parseInt(ptr1[1].substring(0, ptr1[1].length() - 1));
		if (ptr1[1].substring(ptr1[1].length() - 1, ptr1[1].length()).equals("N")) {
			temp[1] += 90;
		} else if (ptr1[1].substring(ptr1[1].length() - 1, ptr1[1].length()).equals("S")) {
			temp[1] = 90 - temp[1];
		}
		if (temp[0] > temp[1]) {
			int x = temp[0];
			temp[0] = temp[1];
			temp[1] = x;
		}

		String[] ptr2 = new String[2];
		ptr2 = str.split("~");
		int[] temp2 = new int[2];
		temp2[0] = Integer.parseInt(ptr2[0].substring(0, ptr2[0].length() - 1));
		if (ptr2[0].substring(ptr2[0].length() - 1, ptr2[0].length()).equals("N")) {
			temp2[0] += 90;
		} else if (ptr2[0].substring(ptr2[0].length() - 1, ptr2[0].length()).equals("S")) {
			temp2[0] = 90 - temp2[0];
		}
		temp2[1] = Integer.parseInt(ptr2[1].substring(0, ptr2[1].length() - 1));
		if (ptr2[1].substring(ptr2[1].length() - 1, ptr2[1].length()).equals("N")) {
			temp2[1] += 190;
		} else if (ptr2[1].substring(ptr2[1].length() - 1, ptr2[1].length()).equals("S")) {
			temp2[1] = 90 - temp2[1];
		}
		if (temp2[0] > temp2[1]) {
			int x = temp2[0];
			temp2[0] = temp2[1];
			temp2[1] = x;
		}

		if (temp[0] < temp2[0] || temp[0] > temp2[1] || temp[1] < temp2[0] || temp[1] > temp2[1]) {
			result = false;
		}

		return result;
	}

//----------------------------------------------------------------------------------------------------------
	/*
	 * 上面的检测是在arr str都格式正确的情况下。进行的范围比较 下面用来检验基本的格式是否正确
	 */

	/*
	 * 判断arr(YYYY.MM.DD)采集时间的正确性
	 */
	public static boolean Check_acquisition_time(String arr) {
		 boolean result = true;
		    
		    if (arr.length() - arr.replace(".", "").length() != 2) {
		      System.out.println("1");
		      return false;
		    } 
		    
		    String[] temp = new String[3];
		    temp = arr.split("\\.");
		    if (temp.length != 3 || temp[0].trim().length() != 4 || temp[1].trim().length() != 2 || temp[2].trim().length() != 2) {
		      System.out.println("2");
		      return false;
		    } 
		    for (int i = 0; i < temp.length; i++) {
		      if (!pattern.matcher(temp[i]).matches()) {
		        System.out.println("3");
		        return false;
		      } 
		    } 
		    
		    Date date = new Date();
		    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		    String NowTime = format.format(date);
		    if (arr.compareTo(NowTime) > 0) {
		      System.out.println("4");
		      return false;
		    } 
		    return result;
	}

	
	/*
	 * 判断arr(起始~截止)采集时间的正确性
	 * */
	public static boolean Check_acquisition_time_of_compare(String arr) {
		
		boolean result=true;
		if(arr.length()-arr.replace("~", "").length()!=1) {
			return false;
		}
		String []temp=arr.split("~");
		
		if(temp.length!=2 || Check_acquisition_time(temp[0])==false || Check_acquisition_time(temp[1])==false) {
			return false;
		}
		
		return result;
		
	}
	
	
	
	
	/*
	 * 判断采集时长arr(全是数据)是否正确
	 */
	public static boolean Check_acquisition_long(String arr) {
		
		boolean result = true;
		if(pattern.matcher(arr).matches() == false) {
		   return false;
		}
		return result;
	}

	
	/*
	  * 判断采集时长arr(时长*波动范围是否正确)
	 * */
	public static boolean Check_acquisition_long_with_range(String arr) {
		boolean result=true;
		
		if(arr.length()-arr.replace("*", "").length()!=1) {
			return false;
		}
		String []temp=arr.split("\\*");
		
		if(temp.length!=2 || pattern.matcher(temp[0]).matches()==false || pattern.matcher(temp[1]).matches()==false) {
			return false;
		}
		
		return result;
	}
	
	/*
	 * 判断经度的格式arr(xW(E)~yW(E))是否正确
	 * */
	public static boolean Check_longitude_range(String arr ) {

		 boolean result = true;
		    
		    if (arr.length() - arr.replace("~", "").length() != 1) {
		      return false;
		    }
		    String[] temp = new String[2];
		    temp = arr.split("~");
		    if (temp.length != 2 || temp[0].trim().length() == 0 || temp[1].trim().length() == 0)
		    {
		      return false;
		    }
		    if ((!temp[0].substring(temp[0].length() - 1, temp[0].length()).equals("E") && 
		      !temp[0].substring(temp[0].length() - 1, temp[0].length()).equals("W")) || (
		      !temp[1].substring(temp[1].length() - 1, temp[1].length()).equals("E") && 
		      !temp[1].substring(temp[1].length() - 1, temp[1].length()).equals("W"))) {
		      
		      System.out.println(temp[0].substring(temp[0].length() - 1, temp[0].length()));
		      
		      System.out.println(temp[1].substring(temp[1].length() - 1, temp[1].length()));
		      return false;
		    } 
		    String[] str = new String[2];
		    str[0] = temp[0].substring(0, temp[0].length() - 1);
		    str[1] = temp[1].substring(0, temp[1].length() - 1);
		    if (!pattern.matcher(str[0]).matches() || !pattern.matcher(str[1]).matches())
		    {
		      return false;
		    }
		    if (Integer.parseInt(str[0]) > 180 || Integer.parseInt(str[0]) < 0 || Integer.parseInt(str[1]) > 180 || Integer.parseInt(str[1]) < 0) {
		      return false;
		    }
		    
		    return result;
	}
	
	/*
	  * 判断纬度的格式arr(xS(N)~yS(N))是否正确
	 * */
	public static boolean Check_latitude_range(String arr) {

		 boolean result = true;
		    
		    if (arr.length() - arr.replace("~", "").length() != 1) {
		      return false;
		    }
		    String[] temp = new String[2];
		    temp = arr.split("~");
		    if (temp.length != 2 || temp[0].trim().length() == 0 || temp[1].trim().length() == 0) {
		      return false;
		    }
		    if ((!temp[0].substring(temp[0].length() - 1, temp[0].length()).equals("S") && 
		      !temp[0].substring(temp[0].length() - 1, temp[0].length()).equals("N")) || (
		      !temp[1].substring(temp[1].length() - 1, temp[1].length()).equals("S") && 
		      !temp[1].substring(temp[1].length() - 1, temp[1].length()).equals("N"))) {
		      return false;
		    }
		    String[] str = new String[2];
		    str[0] = temp[0].substring(0, temp[0].length() - 1);
		    str[1] = temp[1].substring(0, temp[1].length() - 1);
		    if (!pattern.matcher(str[0]).matches() || !pattern.matcher(str[1]).matches()) {
		      return false;
		    }
		    if (Integer.parseInt(str[0]) > 90 || Integer.parseInt(str[0]) < 0 || Integer.parseInt(str[1]) > 90 || Integer.parseInt(str[1]) < 0) {
		      return false;
		    }
		    
		    return result;
	}
	
	/*
	 * 判断arr(x*y)分辨率的格式是否正确
	 * */
	public static boolean Check_resolution(String arr) {

		boolean result=true;
	    if(arr.length()-arr.replace("*","").length()!=1) {
	    	return false;
	    }
	    String []temp=new String[2];
	    temp=arr.split("//*");
	    if(temp.length!=2 || pattern.matcher(temp[0]).matches()==false || pattern.matcher(temp[1]).matches()==false) {
	    	return false;
	    }
		return result;
	}
	
	public static boolean Check_resolution_with_range(String arr) {
		boolean result=true;
	    if(arr.length()-arr.replace("*","").length()!=2) {
	    	return false;
	    }
	    String []temp=new String[3];
	    temp=arr.split("//*");
	    if(temp.length!=3 || pattern.matcher(temp[0]).matches()==false || pattern.matcher(temp[1]).matches()==false || pattern.matcher(temp[2]).matches()==false) {
	    	return false;
	    }
	    
		return result;
	}
}
