import java.io.File;
import java.util.Scanner;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class LanguageFilter extends ListenerAdapter {
	
	public void onMessageReceived(MessageReceivedEvent event) { 
		                 
		try {
			
			User author = event.getAuthor();                  
		    Message message = event.getMessage();            
		    String msg = message.getContent();  
		    Scanner input = new Scanner(new File("res/filter.txt"));
		    String toFilter = msg.toLowerCase();
		   
		
		while(input.hasNextLine()) {
			String line = input.nextLine();
			
			if(toFilter.contains(line) && event.isFromType(ChannelType.TEXT)) { //deletes message for only public channels. Bot can't control private messages. 
			message.delete().queue(); 
			
			author.openPrivateChannel().queue(channel -> {
				channel.sendMessage("Your most recent comment has automatically been removed for containing the word \""+ line +"\". Racism and personal attacks will NOT be tolerated."
				+ " Continued comments will result in your account being temporarily muted from public channels.").queue();	
			});
			
			break; //If someone is feeling like a sailor and has multiple words found, the bot will try to re-delete a line that has already been removed.
			}
		}	
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
	}
}
