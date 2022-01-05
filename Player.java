package ttCode;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * The player
 */
public class Player extends Entity {
    private Map<String, Integer> stats;

    /**
     * Constructor for objects of class Player
     * 
     * @param  name  The player's name
     * @param  stat  The player's stats, logic in Main
     */
    public Player(String name, List<Integer> stat) {
    	super(name, 100);
        stats = new HashMap<>();
        stats.put("Strength", stat.get(0));
        stats.put("Agility", stat.get(1));
        stats.put("Will", stat.get(2));
        stats.put("Knowledge", stat.get(3));
        stats.put("Persuasion", stat.get(4));
        stats.put("Luck", stat.get(5));
    }
    
    /**
     * Gets a stat
     * @param  s  Name of stat
     * @return stat value
     */
    public int stat(String s) {
    	return stats.get(s);
    }
    
    /**
     * Gets the map of stats
     * @return  stats
     */
    public Map<String, Integer> getStats() {
    	return stats;
    }
}
