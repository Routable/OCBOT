import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {
	
    public void onMessageReceived(MessageReceivedEvent event) {
		
        User author = event.getAuthor();                  
        Message message = event.getMessage();           
        String msg = message.getContent();                                            
 
        if (event.isFromType(ChannelType.TEXT)) {         
        	
            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!
            String name = member.getEffectiveName();    //This will either use the Member's nickname if they have one, otherwise it will default to their username. (User#getName())
                                                        

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg); //NOTE: This will display the user information in the IDE as: (Server)[Channel]<name>:message -SB
        }
        
        else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {
    
            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
        }
    }	
    
}