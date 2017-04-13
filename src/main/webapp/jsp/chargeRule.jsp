<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE >
<html>
<head>
    <title>计费规则</title>
    <jsp:include page="/base.jsp"/>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no;"/>
    <link rel="stylesheet" href="${ctx }/jsp/guize.css"/>
</head>
<body>
	<%-- <h3>1,接客费用说明：0-${rule.acceptUserDistance }公里免费，${rule.acceptUserDistance }-8公里${rule.acceptUserMoney }元打车费</h3>
	<h3>2,报单订单不在奖励范围内</h3>
	<h3>3,司机账户必须有余额才可以接单</h3>
	<h3>4,计费规则：05:00-22:00 （10公里内39元，超过按照20元/5公里计算）
            22:00-05:00（10公里内59元，超过按照20元/5公里计算）</h3>
	<h3>5,路桥费由司机与乘客协商</h3>
	<h3>6,等时费：当司机到达目的地10分钟内免费，10分钟后按照 1元/分钟 计费</h3>
	<h3>7,保险费用每笔订单2元</h3>
	<h3>8,平台收取司机师傅费用每笔订单20%</h3>
	ddddd<fmt:formatNumber type="number" value="5" pattern="0.00"/> --%>
<div class="jd_layout">
    <div class="guize_box">
        <div class="feiyong_box">
            <ul>
                <li class="first">行驶时间</li>
                <li class="second">费用</li>
                <div style="clear: both"></div>
            </ul>
            <ul class="jsuan">
                <li class="first">
                    <p>
						${rule.dayStartTime }:00
                    </p>
                    <p>|</p>
                    <p>
                    	${rule.dayEndTIme }:00
                    </p>
                </li>
                <li class="second">
                    <p>
                        <span class="price">
                        	<fmt:formatNumber value="${rule.DayDistanceGradeOneMoney }" type="currency"/>
                        </span>
                        <span>(含${rule.DayDistanceGradeOne }公里)</span>
                    </p>
                    <p class="qian">超过后，每${rule.DayDistanceGradeTwo }公里${rule.DayDistanceGradeTwoMoney }元</p>
                    <p class="qian">不足${rule.DayDistanceGradeTwo }公里按${rule.DayDistanceGradeTwo }公里计算</p>
                </li>
                <div style="clear: both;"></div>
            </ul>

            <ul  class="jsuan">
                <li class="first">
                    <p>
                    	${rule.nightStartTime }:00
                    </p>
                    <p>|</p>
                    <p>
                    	${rule.nightEndTime }:00
                    </p>
                </li>
                <li class="second">
                    <p>
                        <span class="price">
                        	<fmt:formatNumber value="${rule.nightDistanceGradeOneMoney }" type="currency"/>
                        </span>
                        <span>(含${rule.nightDistanceGradeOne }公里)</span>
                    </p>
                    <p  class="qian">超过后，每${rule.nightDistanceGradeTwo }公里${rule.nightDistanceGradeTwoMoney }元</p>
                    <p  class="qian">不足${rule.nightDistanceGradeTwo }公里按${rule.nightDistanceGradeTwo }公里计算</p>
                </li>
                <div style="clear: both;"></div>
            </ul>
        </div>
        <div class="jieshao">
        	<div>
                <p class="jieshao_hou">接客费：</p>
                <p class="jieshao_xiang">司机免费接客距离为${rule.acceptUserDistance }公里、超过后收取接客费${rule.acceptUserMoney }元。</p>
            </div>
            <div>
                <p class="jieshao_hou">等候费：</p>
                <p class="jieshao_xiang">司机到达后、司机可以免费等待${rule.waitTime }分钟、超过后每分钟收取${rule.waitChargeMoney }元。</p>
            </div>
            <div>
                <p class="jieshao_hou">其他费用：</p>
                <p class="jieshao_xiang">保险费用每笔订单2元。</p>
                <p class="jieshao_xiang">平台收取司机师傅费用每笔订单20%(余额扣除)。</p>
                <p class="jieshao_xiang">代驾服务过程中产生的路桥费、停车费、由车主承担。</p>
            </div>
            
        </div>
    </div>
</div>
</body>
</html>
