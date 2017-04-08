import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BaseCommands extends ListenerAdapter {
	
	long start = System.currentTimeMillis();

	
	public void onMessageReceived(MessageReceivedEvent event) { 
		                 
		try {
			
			User author = event.getAuthor();                  
		    Message message = event.getMessage();
		    MessageChannel channel = event.getChannel();
		    String msg = message.getContent();  
		    //Scanner input = new Scanner(new File("res/filter.txt"));
		   // Guild guild = event.getGuild();  
	       // TextChannel textChannel = event.getTextChannel();
	        Member member = event.getMember();       
	       // String name = member.getEffectiveName();  
	        boolean bot = author.isBot();    

			if(msg.equals("/help") && !(bot)) { //help menu-  EVENT IS FROM TYPE CHECK NOT WORKING NEED TO FIX!

				channel.sendMessage(member.getAsMention() + "```Below are a list of commands recognized by the system: \n \n"
						+ "/help		Displays a list of recognized functions, and instructions on how to use them.\n"
						+ "/quiz		Begins an automatic quizlet session with the user.\n"
						+ "/uptime		How long since the server has been online between restarts."	
						+ "```").queue();
				}
			
			if(msg.equals("/uptime") && !(bot)) { //server uptime
				long end = System.currentTimeMillis();
				long endF = end - start; 
				int seconds = (int) (endF / 1000) % 60 ;
				int minutes = (int) ((endF / (1000*60)) % 60);
				int hours   = (int) ((endF / (1000*60*60)) % 24);

				channel.sendMessage("OCBot has been operational for " + hours + " hours, " + minutes + " minutes, and " + seconds + " seconds.").queue();

			}
			
			if(msg.equalsIgnoreCase("Ken Chidlow") && !(bot)) { //easter egg
				channel.sendMessage("Open the market! \n https://cdn.discordapp.com/attachments/231104205509754880/272866367789596694/Untitled-7.jpg").queue();
			}
			
		
			
		} catch (Exception ex) {
			System.out.println("[Error: Not Reachable Statement]");
		} 
	}
}
