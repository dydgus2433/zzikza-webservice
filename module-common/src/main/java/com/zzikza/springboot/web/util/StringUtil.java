package com.zzikza.springboot.web.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {

	/**
	 * 공백 또는 null 여부 체크
	 *
	 * @param str
	 * @return 공백 또는 null 이면 true, 아니면 false
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str) || "null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 왼쪽에 문자열 채우기
	 *
	 * @param str    원본 문자열
	 * @param pad    붙일 문자열
	 * @param length 채워서 만들 문자열 길이
	 * @return
	 */
	public static String lpad(String str, String pad, int length) {
		while (str.length() < length) {
			str = pad + str;
		}
		return str;
	}

	public static String lpad(int num, String pad, int length) {
		return lpad(String.valueOf(num), pad, length);
	}

	/**
	 * 오른쪽에 문자열 채우기
	 *
	 * @param str    원본 문자열
	 * @param pad    붙일 문자열
	 * @param length 채워서 만들 문자열 길이
	 * @return
	 */
	public static String rpad(String str, String pad, int length) {
		while (str.length() < length) {
			str = str + pad;
		}
		return str;
	}

	public static String rpad(int num, String pad, int length) {
		return rpad(String.valueOf(num), pad, length);
	}

	/**
	 * MethodName: nvl 널이거나 blank이면 "", 그렇지 않으면 입력값을 리턴
	 * 
	 * @param Object value
	 * @return String
	 */
	public static String nvl(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	/**
	 * MethodName: nvl 널이거나 blank이면 def값, 그렇지 않으면 입력값을 리턴
	 * 
	 * @param Object value
	 * @return String
	 */
	public static String nvl(Object value, String def) {
		if (value == null || "".equals(value)) {
			return def;
		} else {
			return value.toString();
		}
	}

	/**
	 * MethodName: nvlList 널이면 빈 List 리턴
	 * 
	 * @param Object obj
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	public static List<String> nvlList(Object obj) {
		if (obj == null) {
			return new ArrayList<String>();
		} else {
			return (List<String>) obj;
		}
	}

	/**
	 * class의 모든 필드를 map에 담아서 넘긴다.
	 *
	 * @param aClas
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap<String, String> getAllModelFields(Class inClass) {

		HashMap<String, String> rtnMap = new HashMap();

		List<Field> fields = new ArrayList<>();
		Collections.addAll(fields, inClass.getDeclaredFields());

		for (Field field : fields) {
			try {
				field.setAccessible(true);
				rtnMap.put(field.getName(), field.get(inClass).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return rtnMap;
	}

	/**
	 * 공백 또는 null 여부 체크
	 *
	 * @param str
	 * @return 공백 또는 null 이면 true, 아니면 false
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String)
			return obj == null || "".equals(obj.toString().trim()) || "''".equals(obj.toString().trim());
		else if (obj instanceof List)
			return obj == null || ((List) obj).isEmpty();
		else if (obj instanceof Map)
			return obj == null || ((Map) obj).isEmpty();
		else if (obj instanceof Object[])
			return obj == null || Array.getLength(obj) == 0;
		else
			return obj == null;
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static boolean isEquals(Object sourceObg, Object targetObj) {
		boolean retValue = false;
		if (sourceObg != null) {
			if (sourceObg instanceof String) {
				if (targetObj != null) {
					retValue = sourceObg.toString().equals(targetObj.toString());
				}
			} else if (sourceObg instanceof Double) {
				if (targetObj != null) {
					retValue = ((Double) sourceObg).equals((Double) targetObj);
				}
			} else if (sourceObg instanceof Long) {
				if (targetObj != null) {
					retValue = ((Long) sourceObg).toString().equals((Long) targetObj);
				}
			} else if (sourceObg instanceof Integer) {
				if (targetObj != null) {
					retValue = ((Integer) sourceObg).toString().equals((Integer) targetObj);
				}
			}
		}
		return retValue;
	}

	public static boolean isNotEquals(Object sourceObg, Object targetObj) {
		boolean retValue = false;
		if (sourceObg != null) {
			if (sourceObg instanceof String) {
				if (targetObj != null) {
					retValue = sourceObg.toString().equals(targetObj.toString());
				}
			} else if (sourceObg instanceof Double) {
				if (targetObj != null) {
					retValue = ((Double) sourceObg).equals((Double) targetObj);
				}
			} else if (sourceObg instanceof Long) {
				if (targetObj != null) {
					retValue = ((Long) sourceObg).toString().equals((Long) targetObj);
				}
			} else if (sourceObg instanceof Integer) {
				if (targetObj != null) {
					retValue = ((Integer) sourceObg).toString().equals((Integer) targetObj);
				}
			}
		}
		return !retValue;
	}

	/**
	 * 입력받은 문자열의 주민등록 번호, 휴대폰 번호를 *로 변환하여 돌려준다. MethodName: getMaskedString
	 *
	 * @param String value
	 * @return String
	 */
	public static String getMaskedString(String value) {
		String rtnStr = "";
		String regex1 = "(\\d{6})([-./*+~\\s]*)[1234]\\d{6}";
		rtnStr = value.replaceAll(regex1, "******$2*******");
		// rtnStr = value.replaceAll(regex1, "$1$2*******");

		String regex2 = "(01[0|1|7|8|9])([-./*+~\\s]*)(\\d{3,4})([-./*+~\\s]*)\\d{4}";
		rtnStr = rtnStr.replaceAll(regex2, "***$2****$4****");
		// rtnStr = rtnStr.replaceAll(regex2, "$1$2****$4****");

		return rtnStr;
	}
}
