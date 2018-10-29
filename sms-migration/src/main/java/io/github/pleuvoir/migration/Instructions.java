package io.github.pleuvoir.migration;

import org.flywaydb.core.Flyway;

public class Instructions {

	private Flyway flyway;

	
	public void setFlyway(Flyway flyway) {
		this.flyway = flyway;
	}
	
	public void invoke(String command) throws IllegalArgumentException{
		switch (command) {
		case "migrate":
			migrate();
			break;
		case "clean":
			clean();
			break;
		case "info":
			info();
			break;
		case "validate":
			validate();
			break;
		case "baseline":
			baseline();
			break;
		case "repair":
			repair();
			break;
		default:
			throw new IllegalArgumentException("command:"+command);
		}
	}
	
	public void migrate(){
		flyway.migrate();
	}
	
	
	public void clean(){
		flyway.clean();
	}
	
	
	public void info(){
		flyway.info();
	}
	
	
	public void validate(){
		flyway.validate();
	}
	
	
	public void baseline(){
		flyway.baseline();
	}
	
	
	public void repair(){
		flyway.repair();
	}
}
