import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.Route.Roles;

public class GuildJoin extends ListenerAdapter {
	Member name;
	User nm;
	String usersName;
	Roles roles;
	boolean prompt;
	boolean password = false; 
	boolean cosc; // COSC student identifier used for conversation placement
	boolean notcosc; // !COSC student identfier used for conversation placement
	boolean userExists = false; // Have we seen this person before? False until
								// proven true.

	public void onGuildMemberJoin(GuildMemberJoinEvent event) {

		try {

			name = event.getMember();
			nm = name.getUser();
			Scanner input = new Scanner(new File("res/users.txt"));

			userExists = false; // we assume we haven't seen the user each time
								// they log on, until we prove otherwise in the
								// while loop below.

			while (input.hasNext()) {
				String beenHere = input.nextLine();
				if (name.getEffectiveName().equals(beenHere)) {
					System.out.println("User Exists - Ignore");
					userExists = true;
				}
			} 
			
			input.close();

			if (!userExists) {
				prompt = true; // enabled prompt to be true - this allows the
								// user to enter permission based commands that
								// the bot will respond to.

				nm.openPrivateChannel().queue(channel -> {
					channel.sendMessage("Hello " + name.getEffectiveName()
							+ "! Welcome to the OC Computer Science Discord Server! We recognize this as your first visit here. Please select one of the options provided below: \n \n"

							+ "1) I am an Okanagan College CIS/BCIS student, and wish to access course channels applicable to the programming department. \n \n"
							+ "2) I am a non-CIS/BCIS student, and wish to access course channels applicable to the programming department. \n \n"
							+ "3) I am a guest, and do not require classroom specific permissions.").queue();
				});
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onMessageReceived(MessageReceivedEvent event) {
		if (!userExists) {
			prompt = true; // enabled prompt to be true - this allows the user
							// to enter permission based commands that the bot
							// will respond to.
			//System.out.println("PRE PROMPT TEST");
			if (prompt) {
				//System.out.println("PROMPT TEST");
				try {

					nm = name.getUser();
					User author = event.getAuthor();
					boolean bot = author.isBot();
					Guild guild = name.getGuild();
					GuildController gc = new GuildController(guild);
					Role role = guild.getRoleById("299370904973082626"); 															
					Role guest = guild.getRoleById("300002543600271364"); 
																			
					Message message = event.getMessage();
					String msg = message.getContent();

					if (event.isFromType(ChannelType.PRIVATE) && !(bot)) {
						
						//System.out.println("User test 1");
						if (msg.equals("1") && !(bot) && !password) {
						//	System.out.println("Yup, it works!");
							nm.openPrivateChannel().queue(channel -> {
								channel.sendMessage(
										"Please enter the password that was provided to you, or type 'return' to return to the previous menu")
										.queue();
							});
							password = true;
							cosc = true;
						}

						if (msg.equalsIgnoreCase("COSC2017") && !(bot) && password && cosc) {
							promoteMessage();
							gc.addRolesToMember(name, role).queue();
							writeUser();
							prompt = false;

						} else if (msg.equals("return") && !(bot)) {
							password = false; cosc = false; notcosc = false; 

							nm.openPrivateChannel().queue(channel -> {
								channel.sendMessage("Hello " + name.getEffectiveName()
										+ "! Welcome to the OC Computer Science Discord Server! We recognize this as your first visit here. Please select one of the options provided below: \n \n"

										+ "1) I am an Okanagan College CIS/BCIS student, and wish to access course channels applicable to the programming department. \n \n"
										+ "2) I am a non-CIS/BCIS student, and wish to access course channels applicable to the programming department. \n \n"
										+ "3) I am a guest, and do not require classroom specific permissions").queue();
							});
						}

						if (msg.equals("2") && !(bot) && !password) {
							nm.openPrivateChannel().queue(channel -> {
								channel.sendMessage(
										"Please enter the password that was provided to you, or type 'return' to return to the previous menu")
										.queue();
							});
							password = true;
							notcosc = true;
						}

						if (msg.equalsIgnoreCase("OC2017") && !(bot) && notcosc) {

							promoteMessage();
							prompt = false;
							writeUser();

						}
						if (msg.equals("3") && !(bot) && !password) {
							promoteMessage();
							prompt = false;
							gc.addRolesToMember(name, guest).queue();
							writeUser();
						}
						prompt = false;
					}

				} catch (NullPointerException ex) {

					System.out.println("[Null Pointer Exception: Trying to getName() of BOT user!]");

				}
			}

		}
	}

	public void writeUser() {
		FileWriter fw;
		try {
			fw = new FileWriter("res/users.txt", true);
			PrintWriter printWriter = new PrintWriter(fw);
			printWriter.println(name.getEffectiveName());
			printWriter.close();
			fw.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void promoteMessage() {
		nm.openPrivateChannel().queue(channel -> {
			channel.sendMessage("Your account has been automatically promoted. Thanks for registering!").queue();
		});
	}
}
