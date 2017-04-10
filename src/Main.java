
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;


/* Main class that runs OC Discord Bot.
 * 
 */

public class Main extends ListenerAdapter { 
	
	public static void main(String[] args) {
		
		 try
	        {
	            JDA jda = new JDABuilder(AccountType.BOT)
	            		
	              //OC BOT TOKEN - PRIVATE!!! DO NOT REVEAL!!! -SB
	                    .setToken("THIS TOKEN IS PRIVATE REMOVED FOR SECURITY")          
	                    .addListener(new BotListener()) //Listener prints useful information in plain English on IDE. 
	                    .addListener(new LanguageFilter()) //Checks for naughty words, acts accordingly. 
	                    .addListener(new BaseCommands())
	                    .addListener(new QuizBotPrivate())
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
