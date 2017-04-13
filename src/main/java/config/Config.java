package config;

import java.util.List;

import model.dao._MappingKit;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import config.anno.ClassSearcher;
import config.anno.Controller;

public class Config extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
//		PropKit.use("PropKitTest.txt");
//		System.out.println(PropKit.get("name") + " " + PropKit.get("age"));
	}

	@Override@SuppressWarnings({ "unchecked", "rawtypes" })
	public void configRoute(Routes routes) {
		List<Class> controllerClass;
		try {
			controllerClass = ClassSearcher.findControllerClass();
			Controller cb = null;
			for(Class model:controllerClass){
				cb=(Controller)model.getAnnotation(Controller.class);
				if(cb!=null){
					routes.add(cb.value(), model, "/");
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void configPlugin(Plugins me) {
		loadPropertyFile("config.properties");
		C3p0Plugin cp = new C3p0Plugin(getProperty("url"), getProperty("username"), getProperty("password"));
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		// 使用_MappingKit文件配置model映射
		_MappingKit.mapping(arp);
		// 显示sql语句
		arp.setShowSql(true);
		me.add(arp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// 全局的控制层拦截器
		// me.addGlobalActionInterceptor(new GlobalActionInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		
	}
	
	
	@Override
	public void afterJFinalStart() {
//		try {
//			SchedulerFactory sf = new StdSchedulerFactory();
//			Scheduler sched = sf.getScheduler();
//			// 每天0点的时候清除施肥的树木,和喂食的神兽
//			JobDetail job = JobBuilder.newJob(ClearRich.class).withIdentity("job1", "group1").build();
//			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(
//					CronScheduleBuilder.cronSchedule("0 0 0 * * ?")).build();
//			sched.scheduleJob(job, trigger);
//			// 5秒检测一次删除结够了150课果实的果树!
//			job = JobBuilder.newJob(deleteFruitTree.class).withIdentity("job2", "group1").build();
//			trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").withSchedule(
//		    		CronScheduleBuilder.cronSchedule("0/15 * * * * ?")).build();
//		    sched.scheduleJob(job, trigger);
////			sched.start();
//			System.out.println("定时器开启成功!");
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
	}
	
	
	@Override
	public void beforeJFinalStop() {
		System.out.println("jfinal关闭之后!");
		// super.beforeJFinalStop();
	}
}
