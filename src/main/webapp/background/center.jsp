<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var centerTabs;// 中间面板
	$(function(){
		centerTabs = $('#maintabs').tabs({
			plain:true,
			border:true,
		});
	});
	
	// 添加选项卡
	function addTab(opts) {
		if (centerTabs.tabs('exists', opts.title)) {
			centerTabs.tabs('select', opts.title);
		} else {
			centerTabs.tabs('add', opts);
		}
	}
	
	// 重新加载该页面
	function refreshTab(title){
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update',{
			tab:tab,
			options : tab.panel('options')
		});
	}
</script>
<div id="maintabs" fit="true" border="false" style="overflow:hidden;">
	<div title="欢迎页" class="easyui-layout" style="overflow: hidden;">
		<img style="width:100%;height:100%;" src="${ctx }/static/images/bg.jpg">
	</div>
</div>


