package controller.mobile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import config.anno.Controller;
import controller.tool.BaseController;
import model.dao.Comment;
import model.dao.Comment.OrderCommentStateEnum;
import model.dao.Order;

/**
 * 评论表
 * @Description 
 * @author wh
 * @version 1.0
 * @since 2016-12-21
 */
@Controller("/mobile/comment")
public class CommentController extends BaseController {
	
	/**
	 * 订单完成之后进行评论
	 * @author wh
	 * @since 2016-12-21
	 */
	@Before(Tx.class)
	public void orderComment(){
		if (checkPara("orderId", "grade")) {
			Integer orderId = Integer.valueOf(getPara("orderId"));
			Order order = Order.dao.findById(orderId);
			if(order.getCommentState() == 1){
				renderJsonError(RequestError, "该订单已评论");
				return;
			}
			// 改变订单状态
			order.setCommentState(OrderCommentStateEnum.已评论.ordinal());
			order.update();
			// 添加评论
			Comment comment = new Comment();
			comment.setCommentId(null);
			comment.setOrderId(orderId);
			comment.setGrade(getParaToInt("grade"));
			comment.save();
			renderJsonError(RequestNormal, "评论成功！");
		}
	}
	
	
	public void login(){
		String name = getPara("name");
		String password = getPara("password");
		if(name.equals("admin")&& password.equals("admin")){
			setSessionAttr("name", name);
			System.out.println(getSessionAttr("name"));
			renderJsonError(RequestNormal, "success");
		}else{
			renderJsonError(RequestError, "success");
		}
	}
	
	public void index(){
		Object name = getSessionAttr("name");
		renderJsonError(RequestNormal, name);
	}
	
	
	public void test(){
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		System.out.println(request.getAuthType());
		System.out.println(request.getContextPath());
		Cookie[] cookies = request.getCookies();
		renderText("success");
	}
	
	
	
	
	
	
	
	
	
}
