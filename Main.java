package ttCode;

import java.util.Scanner;

//import ttGraphics.Window;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
/**
* Main running class
*/
public class Main {
    private static Scanner s = new Scanner(System.in);
//    public static Window window = new Window();
    private long wait = getWait();
    private int charDelay = 1;
    private boolean prompts;
    private Player player;
    private boolean hardMode;
    private boolean generalStoreKicked;
    private Map<Object, Integer> inv = new HashMap<>();
//    private ArrayList<Tool> tools = new ArrayList<>();
    
    /**
     * The waiting period between text, so it's not just a bunch of text all at once
     * 
     * @return    user input of how many seconds to wait
     */
    private long getWait() {
    	//Wow this was a midnight thing
    	String[] aggressiveness = {"(Seconds)", "(in Seconds)", "(Seconds, you potato chip)", "(Seconds as in a number)", 
    			"(a NUMBER, you buffoon)", "(Damn rulebreakers...)", "(Always making my code longer...)",
    			"(DO YOU NOT KNOW WHAT A NUMBER IS)", "(TOP ROW, ABOVE YOUR LETTERS)", "(OH MY GOODNESS I PITY YOUR PARENTS)", 
    			"(PLEASE JUST TYPE A NUMBER IN)", "(Oh lordy lord, I am begging you type a number please)",
    			"(You are a lost cause)", "(...)", "(You know this game isn't going to start unless you type a number)",
    			"(You're probably just spamming letters to see when this will break, aren't you?)",
    			"\nException in thread \"main\" java.lang.ArrayIndexOutOfBoundsException: Index 16 out of bounds for length 16\n"
    			+ "	at Main.getWait(Main.java:39)\n	at Main.<init>(Main.java:15)\n	at Main.main(Main.java:322)", "(Hah, you thought)",
    			"(Sigh... can you please just start the game?)", "(There's so much more to explore and play)", "(But here you are)",
    			"(Trying to break the very beginning of the game)", "(How very disappointing)", "(I promise the game is more fun than this)",
    			"(I really wish you would just type a number, you know)", "(What a time-waster you are)",
    			"(Look, if you're upset because of when I yelled at you...)", "(I'm sorry, I didn't mean to call you a potato chip)",
    			"(And I'm sure your parents are proud of you)", "(I just...)", "(Just really hate it when people try to break me)",
    			"(This time feels different though)", "(I guess I'm thankful someone talks to me)", "(Well, listens to me at least)",
    			"(Well this has been fun)", "(I don't really have much else to say though)", "(Thanks for stopping by?)",
    			"(You should really give this game a try though)", "(I'm sure its more fun than listening to me blabber on)",
    			"(No, really, go play)", "(I'll be fine)", "(But do come by and visit sometime again, alright?)"};
    	int index = 0;
        while(true) {
        	try {
        		return (long)(Double.parseDouble(decision("Text delay time: " + aggressiveness[index]))*1000);
        	} catch(NumberFormatException e) {
        		if(index < aggressiveness.length - 1) {
        			index++;
        		} else {
        			return 500;
        		}
        	}
        }
    }
    
    /**
     * Better print method to both print and then wait after
     * 
     * @param  s  what to print
     * @param  do_wait  whether or not to wait
     */
    private void print(Object s) {
        for(char c : ("" + s).toCharArray()) {
        	System.out.print(c);
        	try {
				Thread.sleep(charDelay);
			} catch (InterruptedException e) {}
        }
        System.out.println();
//    	window.updateText(s);
        try {
            Thread.sleep(wait);
        } catch(InterruptedException e) {}
    }
    
    /**
     * Better random function
     * 
     * @param  min  Minimum value, inclusive
     * @param  max  Maximum value, exclusive
     * @return  Result
     */
    private int rand(int min, int max) {
    	return (int) ((Math.random()*(max-min)) + min);
    }
    
    /**
     * Method for determining if the player is lucky enough
     * 
     * @param  threshold  What the random has to be above, non-inclusive
     * @return  Whether the player was lucky enough
     */
    private boolean luckCheck(int threshold) {
    	int result = rand(0, player.stat("Luck"));
    	print("got " + result);
    	return result > threshold;
    }
    
    /**
     * Represents a decision made by the player
     * 
     * @param  message  To be displayed
     * @implNote  Prompts hidden
     * @implNote  Player can return anything
     * @return  What the player replied
     */
    private String decision(String message) { return decision(Arrays.asList(), message, true, false, null); }
    /**
     * Represents a decision made by the player
     * 
     * @param  replies  The replies that a player can make (lowercase)
     * @param  message  To be displayed
     * @param  hidePrompt  If hiding the prompts are necessary
     * @implNote  Player must reply correctly
     * @implNote  Replies exact
     * @return  What the player replied
     */
    private String decision(List<String> replies, String message, boolean hidePrompt) {
    	return decision(replies, message, hidePrompt, true, null); }
    /**
     * Represents a decision made by the player
     * 
     * @param  replies  The replies that a player can make (lowercase)
     * @param  message  To be displayed
     * @param  hidePrompt  If hiding the prompts are necessary
     * @param  usingReplies  If we are using replies or the player can just respond with anything
     * @param  simplified  Simplified replies mapped to actual replies
     * @return  What the player replied
     */
    private String decision(List<String> replies, String message, boolean hidePrompt,
    		boolean usingReplies, Map<String,String> simplified) {
    	return decision(replies, message, hidePrompt, usingReplies, simplified, "", "");
    }
    /**
     * Represents a decision made by the player
     * 
     * @param  replies  The replies that a player can make (lowercase)
     * @param  message  To be displayed
     * @param  hidePrompt  If hiding the prompts are necessary
     * @param  usingReplies  If we are using replies or the player can can just respond with anything
     * @param  simplified  Simplified replies mapped to actual replies
     * @param  special  Any special input the player can input for info
     * @param  specialResp  Special message in response to the special input
     * @return  What the player replied
     */
    private String decision(List<String> replies, String message, boolean hidePrompt,
    		boolean usingReplies, Map<String,String> simplified, String special, String specialResp) {
    	String prompt = "";
    	if(!replies.isEmpty()) {
	        prompt = "(" + replies.get(0);
	        for(int i = 1; i < replies.size(); i++) {
	            prompt += "/" + replies.get(i);
	        }
	        prompt += ") ";
        }
        if(!message.equals("")) { print(message); }
        while(true) {
            System.out.print(((prompts && !hidePrompt && !replies.isEmpty()) ? prompt : "") + "> ");
            String input = s.nextLine().toLowerCase().strip();
            switch(input) {
            	case "help":
            		print("-----Commands-----");
            		print("Help: Shows this list again.");
                	print("What/Where: Prints the prompt again.");
                	print("Everything: Only use if you are very stuck, "
                			+ "it will print every possible response to the current question");
                	print("Inventory/Inv: Displays the items you currently have.");
                	print("Tools: An in-depth look at the tools you have.");
                	print("Stats: Presents you and your basic information.");
                	print("Delay: Changes either the text or character delay.");
            		break;
            	case "what": //Fall
            	case "where":
//                	print(message + ((prompts && !hidePrompt &&
//                			!replies.isEmpty() && usingReplies) ? "\n" + prompt : ""));
            		print(message);
            		break;
            	case "everything":
            		print(message + (!replies.isEmpty() && hidePrompt ? "\n" + prompt : ""));
            		break;
            	case "inventory": //Fall
            	case "inv":
                    print((inv == null || !player.getTools().isEmpty() ? "Wow you have literally nothing." : inv));
                    if(player.getTools() != null && !player.getTools().isEmpty()) { System.out.println(player.getTools()); }
            		break;
            	case "tools":
                	if(player.getTools() == null && player.getTools().isEmpty()) {
                		print("Hah, you have no tools.");
                	} else {
            			print(player.getTools().get(0).detailToString());
                		if(player.getTools().size() > 1) {
                			for(int i = 1; i < player.getTools().size(); i++) { print("\n" + player.getTools().get(i).detailToString()); } }
                	}
            		break;
            	case "stats":
                    if(player != null) {
                    	printPlayer();
                    } else {
                    	print("You don't... exist? Who are you?");
                    }
            		break;
            	case "delay":
            		switch(decision(Arrays.asList("text", "character"), "Text or Character delay?", true,
            			true, Map.of("char", "character"))) {
            			case "text":
                    		print("Text delay time (Seconds), Current: " + wait/1000.0 + " seconds.");
                    		System.out.print("> ");
                    		while(!s.hasNextDouble()) { wait = (long)(s.nextDouble()*1000); }
            				break;
            			case "character":
            				print("Character delay time (Millisecond), Current: " + charDelay + " milliseconds.");
            				System.out.print("> ");
            				while(!s.hasNextInt()) { charDelay = s.nextInt(); }
            				break;
            		}
            		break;
            	default:
            		if(input.equals(special)) { print(specialResp); }
                    if(!usingReplies) { return input; }
                    if(replies.contains(input)) {
                        return replies.get(replies.indexOf(input));
                    } else if(simplified != null && simplified.containsKey(input)) {
                    	return simplified.get(input);
                    } else {
                    	print("Hm, I don't recognize that one.");
                    }
                    break;
            }
            //TODO: error();
        }
    }
    
    /**
     * A simple yes/no decision, simplified
     * 
     * @param  message  to be displayed
     * @return  returns the input in boolean, yes is true, no is false
     */
    private boolean yn(String message) {
        return decision(Arrays.asList("yes","no"), message, false, true,
        		Map.of("y", "yes", "n", "no")).equals("yes") ? true : false;
    }
    
    /**
     * Prints out the current player
     */
    private void printPlayer() {
        print("Name: " + player.getName());
        for(String s : player.getStats().keySet()) {
        	print(s + "-" + player.stat(s));
        }
    }

    /**  Intro/Setup */
    /**
     * Constructor for objects of class Main
     */
    public Main() {
        prompts = true;
        hardMode = false;
        player = createPlayer(intro());
        inv.put("Coins", 1000);
    }
    
    /**
     * Intro method, the first thing seen
     * 
     * @return    if the player has played before
     */
    private boolean intro() {
        print("Welcome!");
        if(yn("Have you played Epic Text before?")) {
            if(!yn("Prompts?")) { prompts = false; }
            return true;
        }
        print("Here is some information that will be useful:");
        print("When prompted with a question, available answers will be in ()");
        print("An example is (yes/no), so you can type \"yes\" or \"no\"");
        print("There are also certain keywords that you can type at any time for info, like \"help\"");
        // print("Just for simplicity, you can usually just type the first letter");
        print("A \">\" symbol represents input time");
        if(yn("Are you ready for this epic adventure?")) {
            print("All right, let's go!");
        } else {
            print("Oof, come back when you're ready.");
            System.exit(0);
        }
        return false;
    }
    
    /**
     * Creating a character
     * 
     * @param  played  if the player has played before
     * @return    the player
     */
    private Player createPlayer(boolean played) {
        print("Let's set up a character");
        String name;
        do {
            name = decision(Arrays.asList(played ? "Name" : "Name? (Type your name)"), "", false, false, null);
        } while(!yn(name + ", is that correct?"));
        List<Integer> stats = new ArrayList<>();
        if(played) { hardMode = yn("Hard mode?"); }
        if(!played) { print("Stats are from 25-100"); }
        if(hardMode || yn("Do you want random stats?")) {
            for(int i = 0; i < 6; i++) {
            	stats.add(rand((hardMode ? 15 : 25), (hardMode ? 76: 86)));
            }
        } else {
            int max = (hardMode? 250 : (played ? 375 : 420));
            print("Your max is " + max);
            if(!played) {
                print("The sum of all 6 statistics should be lower than " + max);
                print("The stats are Strength, Agility, Will, Knowledge, Persuasion, Luck");
                print("Each stat has a max of 100");
                print("For your first time, it's not as important");
            }
            List<String> statNames = new ArrayList<>(Arrays.asList("Strength",
            		"Agility","Will","Knowledge","Persuasion","Luck"));
            for(int i = 0; i < statNames.size(); i++) {
                while(true) {
                    try {
                        int amount;
                        do {
                        	amount = Integer.parseInt(decision(statNames.get(i) + "? Amount Left: " + max).trim());
                        } while(amount > max || amount > 100 || !yn(amount + " for " + statNames.get(i) +"?"));
                        stats.add(amount);
                        max -= amount;
                        break;
                    } catch(NumberFormatException nfe) {}
                }
            }
        }
        return (new Player(name, stats));
    }
    
    /** Story! */
    /**
     * Main method
     */
    public static void main(String[] args) {
        Main story = new Main();
        story.print("Alright, enough setup, on with the adventure!");
        story.print("You are " + story.player.getName() + ", a young traveler with nothing but a few gold coins saved up over your upbringing.");
        story.print("In the kingdom of Atem, there are many villages surrounding a massive central structure:");
        story.print("A medieval-style castle the size of a thousand blue whales.");
        story.print("Ever since you were a child, you had your eyes set on that castle, longing to be inside its cold grey walls.");
        story.print("But a few days ago, rumors about the current King falling ill have spread throughout the kingdom.");
        story.print("The King, Ron Turpoci, is a crabby, cruel King crowned for his crafty, yet hypocritical laws.");
        story.print("However, he hit his crux early in his rule, leading to a crummy cretin craving crystals,");
        story.print("causing crises and creating more critics than supporters.");
        story.print("His recent malady has left the kingdom in disarray, and whispers of a coup have been going around.");
        story.print("You have left your old community behind and have now reached the exterior towns surrounding the castle.");
        story.print("As you walk in, you notice the locals staring at you cautiously.");
        story.print("This first town has historically been neutral in the many political conflicts, preferring to step back and not get involved.");
        story.print("They are most likely all thinking \"Is this another patriot trying to convince us into a coup?");
        story.print("Is this another royal representative announcing a new tax? Could it be another one of us? Who could this stranger be?\"");
        story.town();
        s.close();
    }
    
    /**
     * Opener, start in town
     */
    private void town() {
        print("You are standing in the middle of a small rural town.");
		print("At first glance, two shops catch your attention: a blacksmith and a general store.");
		ArrayList<String> townOptions = new ArrayList<String>(Arrays.asList("look", "go"));
		Map<String, String> townOptionsSimp = new HashMap<>(Map.of("look around", "look"));
		ArrayList<String> townLocations = new ArrayList<String>(Arrays.asList("blacksmith","general store","exit"));
		Map<String, String> townLocationsSimp = new HashMap<>(Map.of("smith", "blacksmith", "bs", "blacksmith", "store", "general store",
			"gs", "general store", "leave", "exit"));
		boolean looked = false;
        while(true) {
			switch(decision(townOptions, "What do you do?", true, true, townOptionsSimp)) {
				case "look":
					if(!looked) {
						print("On a second glance, you also notice a community board next to the general store,");
						print("a bar");
						//gun range/bowling alley?
						townLocations.add("board");
						townLocations.add("bar");
						townLocationsSimp.put("community board", "board");
						looked = true;
					} else {
						print("You continue looking around, like an idiot.");
					}
					break;
				case "go":
					switch(decision(townLocations, "Where do you go?", true, true, townLocationsSimp)) {
		        		case "blacksmith":
		        			townBlacksmith();
		        			break;
	        			case "general store":
	        				townGeneralStore();
	        				break;
	        			case "board":
	        				townBoard();
							break;
	        			case "bar":
	        				townBar();
	        			case "exit":
	        				print("You exit the town.");
	        				return;
		        	}
					break;
			}
        }
    }
    
    /**
     * Town Bar
     */
    private void townBar() {
    	
    }

    /**
     * Community town board
     */
    private void townBoard() {
    	print("You walk over to the community board.");
    	print("Many faded, tattered posters are halphazardly fixed upon it.");
    	if(player.stat("Intelligence") > 25 && luckCheck(5)) { //Making it fairly easy to discover
    		print("You spot a small engraving on the bottom left corner.");
    		print("If you squint, you can make out \"$3c3d3\",\"b@r\", and \"$uns3t\"");
    	}
    }

    /**
     * Blacksmith
     */
    private void townBlacksmith() {
        print("You enter the blacksmith's shop.");
        print("A sign hangs off the roof that says: Welcome to Wade's epic store!");
        print("Detailed posters hung in the back display prices.");
        //while(true) {
    	switch(decision(Arrays.asList(), "What do you do?", true)) {
    		case "look":
    			break;
    	}
        print("Tools: Hoe-2, Shovel-3, Axe-4, Pickaxe-5, Basic Sword-7, Battle Axe-10, Great Sword-15");
        Map<String, Integer> toolsAvailable = Map.of("hoe", 2, "shovel", 3, "axe", 4, "pickaxe", 5,
        		"basic sword", 7, "battle axe", 10, "great sword", 15);
        print("Metals: Copper-3, Iron-4, Steel-6, Titanium-10, Gold-100");
        Map<String, Integer> metals = Map.of("copper", 3, "iron", 4, "steel", 6, "titanium", 10, "gold", 100);
        print("Benefits (optional): Sharp-5, Durable-10, Poisoned-15");
        Map<String, Integer> benefits = Map.of("none", 0, "sharp", 5, "durable", 10, "poisoned", 15);
        print("The individual component prices will be added together for a final cost. With no sales tax! Nice.");
        String intro = "";
        while(true) {
	        if(yn("Do you want to get anything" + intro + "?")) {
	            int cost = 0;
	            String tool = decision(new ArrayList<>(toolsAvailable.keySet()), "Choose a tool.", true, true,
	            		Map.of("pick", "pickaxe", "sword", "basic sword", "bs", "basic sword", "ba", "battle axe",
	            				"gs", "great sword"));
	            cost += toolsAvailable.get(tool);
	            String metal = decision(new ArrayList<>(metals.keySet()), "Choose a metal.", true);
	            cost += metals.get(metal);
	            String benefit = decision(new ArrayList<>(benefits.keySet()), "Choose a benefit. (optional)", true);
	            cost += benefits.get(benefit);
	            if(cost <= inv.get("Coins")) {
	                print("The total cost is: " + cost + " coins.");
	                if(yn("Confirm purchase?")) {
	                    inv.put("Coins", inv.get("Coins")-cost);
	                    player.getTools().add(new Tool(tool, metal, benefit));
	                }
	            } else {
	                print("Too expensive...");
	            }
                intro = " else";
	        } else {
	        	print("You return to the town square.");
	        	break;
	        }
        }
    }

    /**
     * General Store
     * Infinite? so many things to code & explore lol
     */
    private void townGeneralStore() {
    	if(generalStoreKicked) {
    		print("You have been banned from the general store, and the manager glares at you.");
    		return;
    	}
    	//Product, [amount, total cost]
    	Map<String, List<Integer>> cart = new HashMap<>();
		print("You enter the general store.\nThere are shelves upon shelves of items in the store.");
    	//TODO: store closed
    	while(true) {
    		switch(decision(Arrays.asList("food", "weaponry", "survival", "clothing", "rob", "exit"),
    				"Where in the store do you go?", true, true, Map.of("leave", "exit"), "cart",
    				cart.isEmpty() ? "You have nothing in your cart." : cart.toString())) {
	    		case "food":
	    			switch(decision(Arrays.asList("canned", "packaged", "fresh", "exit"),
	    					"What type of food?", true, true, Map.of("leave", "exit"))) {
	    				case "canned":
	    					gs_cannedFood(cart);
	    					break;
	    				case "packaged":
	    					gs_packagedFood(cart);
	    					break;
	    				case "fresh":
	    					gs_freshFood(cart);
	    					break;
	    				case "exit":
	    					break;
	    			}
	    			break;
	    		case "weaponry":
	    			break;
	    		case "survival":
	    			break;
	    		case "clothing":
	    			break;
	    		case "rob":
	    			//sneak to empty register
	    			//cashier catches? make up excuse, persuasion & knowledge check
	    			//mumble incoherently, police called, jail
	    			//convincing excuse, cashier lets u go
	    			//otherwise steal luck amount of coins
	    			break;
	    		case "exit":
	    			print("You return to the town center.");
	    			return;
	    		default:
	    			if(generalStoreKicked) {
	    				print("You return to the town center.");
	    				return;
	    			}
    		}
    	}
    }
    private void gs_cannedFood(Map<String, List<Integer>> cart) {
		print("You walk to the Canned Food section.");
		print("A plethora of soup, vegetables, and fruits in metal cylinders are in front of you.");
		while(true) {
			switch(decision(Arrays.asList("buy", "eat", "ruin", "steal", "exit"),
					"Now what?", true, true, Map.of("purchase", "buy", "get", "buy",
							"trash", "ruin", "nab", "steal", "leave", "exit"), "cart",
    				cart.isEmpty() ? "You have nothing in your cart." : cart.toString())) {
				case "buy":
					int amount = 0;
					do {
						try {
							amount = Integer.parseInt(decision(
									"How many would you like to buy? 2 each or 5 coins for 3."));
						} catch(NumberFormatException nfe) {}
					} while(inv.get("Coins") < ((amount / 3 * 5) + (amount % 3 * 2)));
					cart.put("Can of food", Arrays.asList(amount, ((amount / 3 * 5) + (amount % 3 * 2))));
					print("You put " + amount + " cans of food in your cart");
					break;
				case "eat":
					if(!player.getTools().isEmpty()) {
						Tool tool = player.getTools().get(rand(0, player.getTools().size()));
						if(tool.getDamage() > 10) {
							print("You break open a can of food using your " + tool.toString().toLowerCase() + ".");
							tool.use(); 
							player.heal(2);
						} else {
							print("You try to open a can of food using your " + tool.toString().toLowerCase() + "...");
							print("but fail.");
							tool.use(5);
						}
					} else {
						print("You try to open a can of food using your bare hands...");
						if(player.stat("Knowledge") > 30) {
							print("and succeed by spotting the handy metal tab on top.");
							player.heal(2);
						} else if(player.stat("Strength") > 75) {
							print("and succeed through brute strength.");
							player.heal(2);
						} else {
							print("and fail. Your hands are tired.");
							player.ow(0.5);
						}
					}
					if(!luckCheck(20)) {
						int loss = rand(2, inv.get("Coins") + 1);
						print("Unlucky! A clerk catches your heinous act, takes " +
							loss + " coins from you, and kicks you out.");
						inv.put("Coins", inv.get("Coins") - loss);
						this.generalStoreKicked = true;
						return;
					} else {
						print("No one sees you.");
					}
					break;
				case "ruin":
					if(!player.getTools().isEmpty()) {
						Tool tool = player.getTools().get(rand(0, player.getTools().size()));
						if(tool.getBenefit().equals("sharp")) {
							print("Walking down the aisle, you take your " + tool +
									" and slice open " + rand(2, 20) + " cans of food.");
						}
					} else {
						print("You knock over a few shelves and " + rand(2, 20) +
								" cans of food roll onto the floor.");
					}
					print("You walk away from the scene quickly.");
					print("You hear a cry of distress, and then...");
					print("\"Attention all staff members, please report to aisle " + rand(1, 20) + " for cleanup.\"");
					print("You laugh quietly to yourself.");
					break;
				case "steal":
					this.gs_steal("Can of Food", "Cans of Food");
					break;
				case "exit":
					return;
				default:
					if(yn("Want to stay in the canned foods aisle?")) { break; }
					return;
			}
		}
    }
    private void gs_packagedFood(Map<String, List<Integer>> cart) {
    	print("You stroll over to the packaged food section.");
    	print("Strangely enough, even though the store is so big,");
    	print("the only thing in this section are instant noodles.");
    	print("Endless shelves of ramen sit in front of you.");
		while(true) {
			switch(decision(Arrays.asList("buy", "eat", "ruin", "steal", "exit"),
					"Now what?", true, true, Map.of("purchase", "buy", "get", "buy",
							"trash", "ruin", "nab", "steal", "leave", "exit"), "cart",
    				cart.isEmpty() ? "You have nothing in your cart." : cart.toString())) {
				case "buy":
					int amount = 0;
					do {
						try {
							amount = Integer.parseInt(decision(
									"How many would you like to buy? 4 coins for each package."));
						} catch(NumberFormatException nfe) {}
					} while(inv.get("Coins") < (amount * 4));
					cart.put("Pack(s) of ramen", Arrays.asList(amount, amount * 4));
					print("You put " + amount + " packs of ramen in your cart.");
					break;
				case "eat":
					print("You seize a package and rip it open.");
					print("You... bite into the uncooked noodles...");
					print("Weirdo.");
					if(!luckCheck(20)) {
						print("A stocker catches you, but she is so shocked at what you were eating that she faints on the spot.");
					}
					print("You leave the shelf undetected.");
					break;
				case "ruin":
					print("With unrestrained glee, you grip bags of ramen and toss them into the aisle.");
					print("You manage to displace " + rand(2, 100) + " packs.");
					if(!luckCheck(50)) {
						print("A custodian walks by the aisle and sees the mess you made.");
						print("He sighs and turns to you. \"Hey man, do you know who did this?\"");
						if(yn("Do you admit to it?")) {
							if(yn("\"Oh... do you want to help clean this up?\"")) {
								print("You help pick up the packs on the floor.");
								print("He gives you a fist bump.");
								break;
							} else {
								print("He shakes his head and mutters something under his breath.");
								print("\"...stupid....retail...\"");
								if(player.getName().toLowerCase().equals("karen")) {
									print("Suddenly, the rage of a hundred angry shoppers fills your body.");
									print("\"Excuse me?????\" you hear yourself say,");
									print("\"I AM SO INSULTED\"");
									print("\"LET ME TALK TO YOUR MANAGER\"");
								}
								if(luckCheck(90)) {
									print("\"You know what?\" he says, \"I've had just about enough in this stupid store.\"");
									print("\"I may get fired, but at least I'm taking you down with me.\"");
									print("He punches you in the face");
									player.ow(5);
									//TODO: Bossfight
								}
								break;
							}
						} else {
							print("\"Yeah, I think I saw them go into aisle " + rand(1, 10) + ",\" you say.");
							print("He walks away in search of the red herring.");
							if(!luckCheck(30) || player.stat("Agility") < 40) {
								print("He doesn't find anyone there, and catches you trying to flee.");
								print("\"Wait a minute, it was you!\"");
								print("He then throws you out of the store.");
								generalStoreKicked = true;
								return;
							}
							print("He find someone and starts to interrogate them.");
							print("You disappear before he can realize the truth.");
							break;
						}
					}
					print("No one sees you.");
					break;
				case "steal":
					gs_steal("pack of ramen", "packs of ramen");
					break;
				case "exit":
					return;
			}
		}
    }
    private void gs_freshFood(Map<String, List<Integer>> cart) {
    	print("You perambulate to the fresh food section.");
    	//fruit or veggie
    	//overflowing fruit
    	//veggie spray? cannot go
    	//shining w/ fresh coat
		while(true) {
			switch(decision(Arrays.asList("buy", "eat", "ruin", "steal", "exit"),
					"Now what?", true, true, Map.of("purchase", "buy", "get", "buy",
							"trash", "ruin", "nab", "steal", "leave", "exit"))) {
				case "buy":
					//Banana
					break;
				case "eat":
					break;
				case "ruin":
					break;
				case "steal":
					break;
				case "exit":
					return;
			}
		}
    }
    private void gs_steal(String product, String product_plural) {
		print("You decide to steal some " + product_plural.toLowerCase() + ". One by one, you begin to pocket them.");
		if(!luckCheck(50) || player.stat("Agility") < 50) {
			print("Uh oh! The store manager catches you and is about to call the police.");
			switch(decision(Arrays.asList("persuade", "bribe", "fight", "wait"), "What will you do?",
					true, true, Map.of("convince", "persuade", "seduce", "persuade", "stop", "fight",
							"kill", "fight", "murder", "fight", "threaten", "fight", "", "wait",
							"nothing", "wait"))) {
				case "persuade":
					if(!luckCheck(75) && player.stat("Persuasion") >= 75 &&
							player.stat("Knowledge") >= 50 && player.stat("Will") >= 25) {
						print("You quickly persuade the manager into not calling the police.");
						print("\"Hmph. You're damn lucky I'm in a good mood today. Hand over the goods kid,\" he demands.");
						print("\"...and I'll take these.\"");
						if(!player.getTools().isEmpty()) {
							int index = rand(0, player.getTools().size());
							Tool tool = player.getTools().get(index);
							print("He takes your " + tool + ".");
							player.getTools().remove(index);
						} else {
							int loss = rand(10, inv.get("Coins")/2 > 10 ? inv.get("Coins")/2 : 11);
							print("He takes " + loss + " coins from you.");
							inv.put("Coins", inv.get("Coins") - loss);
						}
						print("\"I'm keeping my eye on ya, punk.\"");
					} else {
						print("\"Nice try, but a crime is crime, kid.\"");
						police();
					}
					break;
				case "bribe":
					//Less luck, persuasion, and knowledge than persuade, but requires money and willingness to give it up
					if(!luckCheck(50) && player.stat("Persuasion") >= 50 && player.stat("Knowledge") >= 40
							&& inv.get("Coins") >= 50 && player.stat("Will") >= 50) {
						print("He tilts his head to the side and considers his options.");
						if(!luckCheck(80)) {
							print("\"Alright, I admire your confidence.");
							print("But if I'm going to breaking the law for you, 50 coins ain't gonna cut it.\"");
							print("He takes " + (inv.get("Coins") <= 75 ? "all of your" : 75) + " coins from you.");
							inv.put("Coins", (inv.get("Coins") <= 75 ? 0 : inv.get("Coins") - 75));
						} else {
							print("He takes a deep breath in and sighs.");
							print("\"Fine.\"");
							print("He grabs 50 coins from you");
							inv.put("Coins", inv.get("Coins") - 50);
						}
						print("\"If I hear you blabbed to any authorities, your ass is mine, understand?\"");
						print("He also kicks you out of the store");
						generalStoreKicked = true;
					} else {
						print("\"Bribery ain't gonna work on me. That's another crime, ya know.\"");
						police();
					}
					break;
				case "fight":
					print("\"You've got spunk, kid. I like that\" he admits,");
					print("\"You're damn lucky the developer hasn't added combat in this game yet.\"");
					print("\"I won't call the police, but you're not welcome here anymore.\"");
					generalStoreKicked = true;
					return;
				case "wait":
					print("He calls the police. What did you expect?");
					police();
					break;
			}
		} else {
			int stolen = rand(5, 50);
			print("You manage to steal " + stolen + " " + product.toLowerCase() + ".");
			inv.put("product", inv.getOrDefault(product, 0) + stolen);
		}
    }
    
    /** Uh oh */
    private void police() {
    	print("*Oogah oogah oogah oogah oogah*");
    	print("Police cars screech in and squads of police rush out.");
    	print("\"Freeze!\" yells the one in charge, \"Put your hands where I can see them!\".");
    	if(!yn("Do you comply?")) {
    		print("\"I said, put your hands in the air!\"");
    		print("I won't say it a second time!!\"");
    		if(!yn("How about now?")) {
    			print("Due to your stubborness, the police shoot you.");
    			print("Well, you died.");
    			System.exit(0);
    		}
    	}
    	print("They walk over and arrest you.");
    	print("After a long, uncomfortable drive to the local jail,");
    	print("you spend the rest of your life toiling about in a jail cell.");
    	System.exit(0);
    }
}

//TODO: Story stuff/notes
//Multiple branches depending on which village/which alliance you go to, patriots--coup, loyalists/royals--become king? oppress coup, neutral--idk
	//Separate, declare new nation, sign declaration of independence
//Random ideas/must include:
//Brushing teeth, toothpaste spills on shirt
//Puzzles and Dragons
//Island of skeletons that are rebelling against their intelligent giant dog masters (+ tall skeleton man) Dream?
//Doing bad on a test
//man bear pig in a cave, algore in a suit
//beer mimic
//henchman loot, attacks
//secondhand clothes
//pear soup

//TODO: How do I wish to help/impact my community through this program?