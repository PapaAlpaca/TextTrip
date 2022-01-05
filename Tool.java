package ttCode;

import java.util.Map;
/**
 * A tool, sold by the lovely Wade
 */
public class Tool {
    private String name;
    private String material;
    private String benefit;
    private int dmg;
    private int durability;
    private boolean hasBenefit;
    private boolean poisoned;

    /**
     * Constructor for objects of class Tool
     * 
     * @param  tools  Name of tool, i.e. Hoe
     * @param  material  Material tool is made of
     * @param  benefit  Benefit the tool has (if any)
     */
    public Tool(String tools, String material, String benefit) {
        this.name = tools;
        this.material = material;
        this.benefit = benefit;
        this.poisoned = benefit.equals("poisoned");
        this.hasBenefit = !benefit.equals("none");
        Map<String, Integer> toolDmg = Map.of("hand", 1, "hoe", 1, "shovel", 2, "axe", 4, "pickaxe", 3,
        		"basic sword", 7, "battle axe", 10, "great sword", 15);
        Map<String, Integer> toolDby = Map.of("hoe", 20, "shovel", 20, "axe", 25, "pickaxe", 30, 
        	"basic sword", 50, "battle axe", 60, "great sword", 75, "hand", 999999999);
        Map<String, Integer> metalDmg = Map.of("copper", 3, "iron", 4, "steel", 6, "titanium", 10, "gold", 100, "flesh", 1);
        Map<String, Integer> metalDby = Map.of("copper", 1, "iron", 2, "steel", 4, "titanium", 8, "gold", 6, "flesh", 999999999);
        this.durability = toolDby.get(tools) * metalDby.get(material) * (benefit.equals("durable") ? 2 : 1);
        this.dmg = toolDmg.get(tools) + metalDmg.get(material) + (poisoned ? 10 : 0) + (benefit.equals("sharp") ? 15 : 0);
    }
    
    /** @return  The tool's name */
    public String getName() { return name; }
    /** @return  The material the tool is made of */
    public String getMaterial() { return material; }
    /** @return  The benefit the tool has (if any) */
    public String getBenefit() { return benefit; }
    /** @return  The amount of damage the tool can deal */
    public int getDamage() { return dmg; }
    /** @return  The health of the tool */
    public int getDurability() { return durability; }
    /** @return  If the tool is poisoned */
    public boolean isPoisoned() { return poisoned; }
    
    /** This tool has been used, decrease durability by 1 */
    public void use() { use(1); }
    /**
     * This tool has been used in a special way
     * 
     * @param  wear  Amount to decrease durability by
     */
    public void use(int wear) { durability -= wear; }
    
    public String toString() {
    	String out = (!benefit.equals("none") ? 
    			(benefit.substring(0,1).toUpperCase() + benefit.substring(1) + " " + material) :
    				(material.substring(0,1).toUpperCase() + material.substring(1))) + " " + name;
        return out;
    }
    
    public String detailToString() {
    	return ((!hasBenefit && material.substring(0,1).matches("[aeiou]")) ||
    			(hasBenefit && benefit.substring(0,1).matches("[aeiou]")) ?
    			"An " : "A ") + (hasBenefit ? benefit + " " : "") + material + " " + name 
    			+ "\nLethality: " + dmg + "\nDurability: " + durability + (poisoned ? "\n|Poisoned|" : "");
    }
}

