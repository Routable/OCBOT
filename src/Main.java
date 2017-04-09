
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;


/* Main class that runs OC Discord Bot. Created by Steven Bucholtz & Shayne Doyle. 
 * 
 */

public class Main extends ListenerAdapter { 
	
	public static void main(String[] args) {
		
		 try
	        {
	            JDA jda = new JDABuilder(AccountType.BOT)
	            		
	              //OC BOT TOKEN - PRIVATE!!! DO NOT REVEAL!!! -SB
	                    .setToken("Mjc0MDYyNzA5NTI2MzY0MTYw.C8Gj5w.BOVFNLH-6BQ9Cb7cKR4BsDBoUvU")          
	                    .addListener(new BotListener()) //Listener prints useful information in plain English on IDE. 
	                    .addListener(new LanguageFilter()) //Checks for naughty words, acts accordingly. 
	                    .addListener(new BaseCommands())
	                    .addListener(new GuildJoin())
	                    .buildBlocking();  
	        }
	        catch (LoginException e)
	        {
	        	 //Catching authentication based issues. -SB
	            e.printStackTrace();    
	        }
	        catch (InterruptedException e)
	        {
	        	//Catches in case of buildBlocking does not wait for JDA to fully load and becomes interrupted. Unlikely scenario, but recommended to keep. -SB
	            e.printStackTrace();	
	        }
	        catch (RateLimitedException e)
	        {
	        	//Logic process is rate limited. If rapid logins cause us to hit the rate limit, you will see this exception. -SB
	            e.printStackTrace();	
	        }
	}

	
	
	
	
	
	
	
}
