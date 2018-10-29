package io.github.pleuvoir.migration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 启动器<br>
 * 使用命令行的方式执行数据库迁移<br>
 * 提供命令：<br>
 * <ul>
 * 	<li>-m xxx 执行迁移指令，指令详见flywaydb文档</li>
 * </ul>
 * @author abeir
 *
 */
public class Launcher {
	
	private HelpFormatter formatter = new HelpFormatter();
	private Options opt = new Options();
	
	private String help = "[-m][-e]";
	
	private void printHelp(){
		formatter.printHelp(help, opt);
	}
	
	private void buildOptions(){
		Option flywayOpt = Option.builder("m").desc("Execute flyway migration instructions.").hasArg().numberOfArgs(1).required().build();
		Option envOpt = Option.builder("e").desc("Spring environment.").hasArg().numberOfArgs(1).required().build();
        
        opt.addOption(flywayOpt);
        opt.addOption(envOpt);
	}
	
	public void exec(String[] args) throws ParseException{
        CommandLineParser parser = new DefaultParser();
        CommandLine cl = parser.parse(opt, args, true);
        
        if(cl.hasOption('h')){
        	printHelp();
        	return;
        }
        
        String command = null;
        if(cl.hasOption('m')){
        	command = cl.getOptionValue('m');
        }else{
        	throw new ParseException(null);
        }
        String env = null;
        if(cl.hasOption('e')) {
        	env = cl.getOptionValue('e');
        }else {
        	throw new ParseException(null);
        }
        
        Profile profile = null;
		if(StringUtils.isNotBlank(env)) {
			profile = Profile.toEnum(env);
		}
		if(profile==null) {
			profile = Profile.DEV;
		}
        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.getEnvironment().setActiveProfiles(profile.value());
		context.registerShutdownHook();
		context.load("classpath:config/application-context.xml");
		context.refresh();
		context.start();
        
		Instructions ins = context.getBean(Instructions.class);
    	ins.invoke(command);
    	context.close();
	}
	
	
	
	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.buildOptions();
		try {
			launcher.exec(args);
		} catch (ParseException e) {
			launcher.printHelp();
		}
	}
	
}
