package test;

import org.junit.Test;

import io.github.pleuvoir.migration.Launcher;

public class LauncherTests {

	@Test
	public void test(){
		
		Launcher.main(new String[]{"-m", "migrate", "-e", "dev"});
		//baseline   创建基线  
		//migrate    数据库迁移
		//repair	 撤销
	}
}
