package other.interceptor;

import javax.servlet.http.HttpServletRequest;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class GlobalActionInterceptor implements Interceptor {
	public void intercept(Invocation inv) {
		try {
			HttpServletRequest request = inv.getController().getRequest();
			// 不是访问后台的action直接放行
			String uri = request.getRequestURI();
			
			// /hn_farm/mobile/user/register
			// 如果是移动端访问的接口
			if(uri.contains("mobile")){
				if(inv.getController().getSessionAttr("user") != null){
					inv.invoke();
				}else{
					inv.getController().redirect("/userpage/login.jsp");
				}
			} else {
				if(inv.getController().getSessionAttr("adminUser") != null){
					inv.invoke();
				}else{
					inv.getController().redirect("/background/backLogin.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
