package other.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * sql语句实体类
 * @author Janesen
 *
 */
public class SqlEntity {
	/**
	 * sql语句
	 */
	private String sqlStr;
	
	/**
	 * sql语句的参数集合，例如在使用 insert into user values(?,?)  参数集合为 1,2
	 */
	private List<Object> sqlParams=new ArrayList<Object>();
	public String getSqlStr() {
		return sqlStr;
	}
	
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}
	
	public Object[] getSqlParams(){
		return sqlParams.toArray(new Object[]{});
	}
	
	public void setSqlParams(List<Object> sqlParams) {
		this.sqlParams = sqlParams;
	}
	
	public List<Object> getListParams() {
		if(sqlParams==null)sqlParams=new ArrayList<Object>();
		return sqlParams;
	}
	
	/**
	 * 转换成静态的sql语句，即没有占位符 ？ 的sql语句
	 * @return
	 */
	public SqlEntity toStaticSql(){
		List<Object> parmas=getListParams();
		for (int i = 0; i < parmas.size(); i++) {
			Object value =parmas.get(i);
			if(parmas.get(i) instanceof String){
				value="'"+parmas.get(i).toString().replaceAll("'", "\\\\'").replaceAll("\"", "\\\\\"")+"'";
			}
			setSqlStr(getSqlStr().replaceFirst("\\?",value+""));
		}
		return this;
	}

}
