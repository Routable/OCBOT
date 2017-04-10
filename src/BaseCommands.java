import java.util.Random;

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
			// Scanner input = new Scanner(new File("res/filter.txt"));
			// Guild guild = event.getGuild();
			// TextChannel textChannel = event.getTextChannel();
			Member member = event.getMember();
			// String name = member.getEffectiveName();
			boolean bot = author.isBot();

			if (msg.equals("/help") && !(bot)) { // help menu- EVENT IS FROM
													// TYPE CHECK NOT WORKING
													// NEED TO FIX!

				channel.sendMessage(
						member.getAsMention() + "```Below are a list of commands recognized by the system: \n \n"
								+ "/help		Displays a list of recognized functions, and instructions on how to use them.\n"
								+ "/quiz		Begins an automatic quizlet session with the user.\n"
								+ "/eightball  The magic eightball has an answer for everything. \n"
								+ "/roll        Randomly generates a number between 1 and 100. No gambling! \n"
								+ "/uptime		How long since the server has been online between restarts." + "```")
						.queue();
			}

			if (msg.equals("/uptime") && !(bot)) { // server uptime
				long end = System.currentTimeMillis();
				long endF = end - start;
				int seconds = (int) (endF / 1000) % 60;
				int minutes = (int) ((endF / (1000 * 60)) % 60);
				int hours = (int) ((endF / (1000 * 60 * 60)) % 24);

				channel.sendMessage("OCBot has been operational for " + hours + " hours, " + minutes + " minutes, and "
						+ seconds + " seconds.").queue();

			}

			if (msg.equalsIgnoreCase("Ken Chidlow") && !(bot)) { // easter egg
				channel.sendMessage(
						"Open the market! \n https://cdn.discordapp.com/attachments/231104205509754880/272866367789596694/Untitled-7.jpg")
						.queue();
			}

			if (msg.equalsIgnoreCase("/roll") && !(bot)) { // easter egg
				Random rand = new Random();
				int n = rand.nextInt(100) + 1;
				channel.sendMessage("Your roll: " + n).queue();
			}

			if (msg.equalsIgnoreCase("/eightball") && !(bot)) { // easter egg
				Random rand = new Random();
				int n = rand.nextInt(20) + 1;
				String eightball = "";

				switch (n) {
				case 1:
					eightball = "It is certain";
					break;
				case 2:
					eightball = "It is decidedly so";
					break;
				case 3:
					eightball = "Without a doubt";
					break;
				case 4:
					eightball = "Yes definitely";
					break;
				case 5:
					eightball = "You may rely on it";
					break;
				case 6:
					eightball = "As I see it, yes";
					break;
				case 7:
					eightball = "Most likely";
					break;
				case 8:
					eightball = "Outlook good";
					break;
				case 9:
					eightball = "Yes";
					break;
				case 10:
					eightball = "Signs point to yes";
					break;
				case 11:
					eightball = "Reply hazy try again";
					break;
				case 12:
					eightball = "Ask again later";
					break;
				case 13:
					eightball = "Better not tell you now";
					break;
				case 14:
					eightball = "Cannot predict now";
					break;
				case 15:
					eightball = "Ask again later";
					break;
				case 16:
					eightball = "Better not tell you now";
					break;
				case 17:
					eightball = "Cannot predict now";
					break;
				case 18:
					eightball = "Ask again later";
					break;
				case 19:
					eightball = "Better not tell you now";
					break;
				case 20:
					eightball = "Cannot predict now";
					break;
				default:
					eightball = "Better not tell you now";
					break;
				}

				channel.sendMessage(eightball).queue();
			}

		} catch (Exception ex) {
			System.out.println("[Error: Not Reachable Statement]");
		}
	}
}
