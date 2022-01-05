package ttCode;

import java.util.ArrayList;

/**
 * Any living object
 */
public class Entity {
	private String name;
	private double health;
	private Tool equippedTool;
    private ArrayList<Tool> tools;
	
	/**
	 * Constructor for objects of class Entity
	 * 
	 * @param  name  Name of entity
	 * @param  health  Health of entity
	 * @param  equippedTool  Equipped tool
	 * @param  tools  Any starting tools
	 */
	public Entity(String name, double health, Tool equippedTool, ArrayList<Tool> tools) {
		this.name = name;
		this.health = health;
		this.equippedTool = equippedTool;
		this.tools = tools;
	}
	public Entity(String name, double health) {
		this(name, health, new Tool("hand", "flesh", "none"), new ArrayList<Tool>());
	}
	
	public String getName() { return name; }
	public double getHealth() { return health; }
	public Tool getEqpTool() { return equippedTool; }
	public ArrayList<Tool> getTools() { return tools; }
	
	public void ow(double amount) { health -= amount; }
	public void heal(double amount) { health += amount; }
	public void setEqpTool(Tool tool) {
		if(tools.contains(tool)) {
			equippedTool = tool;
		} else {
			System.out.println("Entity does not have " + tool);
		}
	}
}
