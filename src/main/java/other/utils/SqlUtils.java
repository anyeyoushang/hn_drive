package other.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 操作sql的工具类
 * @author Janesen
 * 
 */
public class SqlUtils {

	/**
	 * 匹配不带别名的操作符的正则表达式
	 */
	private static String operateReg = "(.*)(\\&{1}|\\|{2})([\\=,\\+,\\-,\\*,\\/,\\<,\\>,\\!,%,\\.,i,n,o,t,_,^, ,l,k,e]+)";
	
	/**
	 * 匹配带别名的操作符的正则表达式
	 */
	private static String operateRegAlias = "(.*)(\\.){1}(.*)(\\&{1}|\\|{2})([\\=,\\+,\\-,\\*,\\/,\\<,\\>,\\!,%,\\.,i,n,o,t,_,^, ,l,k,e]+)";
	
	public static void main(String[] args) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("name&>=", 12);
//		map.put("age||<=", 12);
//		SqlEntity entity = appendWhere("select * from user", map);
//		System.out.println(entity.getSqlStr());
//		for(Object obj : entity.getSqlParams()){
//			System.out.println(obj);
//		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("age&<=", 12);
		String[] arr = {"1","2","3"};
		map.put("u.name||notin", arr);
		SqlEntity entity = appendWhere("select * from user u left person p on u.uid = p.pid group by p.pid", map);
		System.out.println(entity.getSqlStr());
		for(Object obj : entity.getSqlParams()){
			System.out.println(obj);
		}
	}
	
	/**
	 * 拼接占位符,添加参数
	 * @author wh
	 * @since 2016年11月29日
	 * @param value
	 * @param values
	 * @return
	 */
	private static String convertInPlaceholder(Object value, List<Object> values) {
		List<String> pStr = new ArrayList<String>();
		if (value instanceof Object[]) {
			Object[] ps = (Object[]) value;
			for (int i = 0; i < ps.length; i++) {
				values.add(ps[i]);
				pStr.add("?");
			}
		} else if (value instanceof Collection<?>) {
			List<?> list = (List<?>) value;
			for (int i = 0; i < list.size(); i++) {
				values.add(list.get(i));
				pStr.add("?");
			}
		}else if(value.toString().contains(",")){
			String[] arrays=value.toString().split(",");
			for (String string : arrays) {
				values.add(string);
				pStr.add("?");
			}
		}else {
			values.add(value);
			pStr.add("?");
		}
		return StringUtils.join(pStr, ",");
	}

	/**
	 * 构建where sql语句
	 * 
	 * @param where
	 *            map
	 * @param Map特殊规则介绍
	 *            ：
	 * @param key规则如下
	 *            ：
	 * @param 【列名+条件符+操作符】
	 * 
	 * @param 列名
	 *            ：表格的列名称
	 * @param 条件符
	 *            ：& 和 || ，翻译成sql语句就是 and 和 or
	 * @param 操作符
	 *            ：就是普通的sql比较符，>=、<=、>、<、= 、in、not in、lk 其中lk表示like
	 * 
	 * @param 例如：
	 * 			key: number&>= value:2 生成后的where语句为 and number >= 2
	 * 			key: name&lk% value:a 生成后的where语句为 and name like 'a%'
	 * @return
	 */
	public static SqlEntity buildWhereSql(Map<String, Object> where) {
		SqlEntity sqlEntity = new SqlEntity();

		String sqlStr = " where 1=1 ";
		// 得到参数的条件集合
		Set<String> keys = where.keySet();
		// 按照规则遍历每个条件
		for (String string : keys) {
			// 如果该条件有内容,并且不已@开头
			if(StringUtils.isNotBlank(string)&&!string.startsWith("@")&&!string.equals("null")){
				// 得到该条件的值
				Object value = where.get(string);
				// 如果该值等于null或者空串
				if (StringUtils.isEmpty(value.toString())){
					continue;
				}
				// 用正则表达式校验该条件
				Matcher matcher = Pattern.compile(operateReg).matcher(string);
				Matcher matcherAlias = Pattern.compile(operateRegAlias).matcher(string);
				if (matcher.find()) {// 不带别名的格式
					String columnName = matcher.group(1);
					if (matcher.group(2).equals("&")) {
						sqlStr += " and " + columnName;
					} else if (matcher.group(2).equals("||")) {
						sqlStr += " or " + columnName;
					}
					String operate = matcher.group(3);
					if (operate.contains("lk")) {
						sqlStr += " like ? ";
						sqlEntity.getListParams().add(
								operate.replaceAll("lk", value.toString())
										.replaceAll(" ", ""));
					} else if (operate.equals("in")) {
						sqlStr += " in ("
								+ convertInPlaceholder(where.get(string),
										sqlEntity.getListParams()) + ") ";
					} else if (operate.contains("not") && operate.contains("in")) {
						sqlStr += " not in ("
								+ convertInPlaceholder(where.get(string),
										sqlEntity.getListParams()) + ") ";
					} else {
						sqlStr += " " + operate + " ? ";
						sqlEntity.getListParams().add(value);
					}
				}else if(matcherAlias.find()){// 带别名的格式
					String alias = matcher.group(1);
					String columnName = matcher.group(2);
					if (matcher.group(3).equals("&")) {
						sqlStr += " and " + alias + "." + columnName;
					} else if (matcher.group(3).equals("||")) {
						sqlStr += " or " + alias + "." + columnName;
					}
					String operate = matcher.group(4);
					if (operate.contains("lk")) {
						sqlStr += " like ? ";
						sqlEntity.getListParams().add(
								operate.replaceAll("lk", value.toString())
										.replaceAll(" ", ""));
					} else if (operate.equals("in")) {
						sqlStr += " in ("
								+ convertInPlaceholder(where.get(string),
										sqlEntity.getListParams()) + ") ";
					} else if (operate.contains("not") && operate.contains("in")) {
						sqlStr += " not in ("
								+ convertInPlaceholder(where.get(string),
										sqlEntity.getListParams()) + ") ";
					} else {
						sqlStr += " " + operate + " ? ";
						sqlEntity.getListParams().add(value);
					}
				}
			}
		}
		sqlEntity.setSqlStr(sqlStr);
		return sqlEntity;
	}

	/**
	 * 追加where判断语句
	 * 
	 * @param sqlStr
	 *            原sql语句
	 * @param where
	 *            判断条件
	 * @return
	 */
	public static SqlEntity appendWhere(String sqlStr, Map<String, Object> where) {
		SqlEntity sqlEntity = new SqlEntity();
		if(where!=null){
			// 构建的where实体类
			SqlEntity whereEntity = buildWhereSql(where);
			// 传入的sql语句
			String sql = sqlStr.toLowerCase();
			// 处理sql语句
			String[] arraySql = sql.split("where");

			String whereSql="";
			if (arraySql.length > 1) {
				// 追加你写的where后面的语句
				whereSql=whereEntity.getSqlStr() + " and "+ arraySql[1];
			} else {
				whereSql=whereEntity.getSqlStr();
			}
			sql = arraySql[0] + whereSql;
			sqlEntity.setSqlStr(sql);
			sqlEntity.getListParams().addAll(whereEntity.getListParams());
		}else{
			sqlEntity.setSqlStr(sqlStr);
		}
		return sqlEntity;
	}
	
}
