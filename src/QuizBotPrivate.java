/*  This bot plays a quiz to a student in a private channel
*   To start the quiz, the student types /quiz into any channel or a private message with the bot.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class QuizBotPrivate extends ListenerAdapter {
	String answer = "";								  //**I had to declare a lot of variables as instance. 
	int correctAnswers = 0;							  //**Because each event is a separate call of the method
	Boolean waiting = false;						  //**Variables keep resetting unless instance level.
	Boolean running = false;
	static ArrayList<Questions> questions = new ArrayList<Questions>();
	int numberOfQuestions = questions.size();
	int questionCounter = numberOfQuestions - questions.size();
	Questions currentQ;

	public void onMessageReceived(MessageReceivedEvent event) {
		User author = event.getAuthor();      			//**The user that sent the message
		boolean bot = author.isBot(); 					//**This boolean is useful to determine if the User that sent the Message is a BOT or not!
		Message message = event.getMessage(); 			//**The message that was received.
		MessageChannel msgchannel = event.getChannel(); //**This is the MessageChannel that the message was sent to. This could be a TextChannel,PrivateChannel, or Group!
		String msg = message.getContent(); 				//**This returns a human readable version of the Message. Similar to what you would see in the client.
		String toFilter = msg.toLowerCase();			//**Message that user sends converted to lower
		int roll;										//**Random Variable
		
		if (!(bot)) {
			if (toFilter.contains("/quiz") && event.isFromType(ChannelType.TEXT)) {
				initQuestions();
				running = true;
				correctAnswers = 0;                     //**Resets Users Score
				author.openPrivateChannel().queue(privChannel -> {
					privChannel
							.sendMessage(
									"***Welcome to the Quiz Bot***\n**New Game Started**\n\n **Use the following commands to run a quiz:**\n    /n ----- Get Next Question \n    /s ------Skip Question \n    /score- Current Score\n    /r ------Reset Your Score \n    /e ------Ends the quiz \n    /qhelp-Lists these options again \n    **/quiz--This will restart the quiz at any time**\n **Please Make Your Selection**")
								.queue();
				});
			}
			else if (event.isFromType(ChannelType.PRIVATE)) {
				if (toFilter.contains("/quiz")) {
					initQuestions();
					running = true;
					author.openPrivateChannel().queue(privChannel -> {
						privChannel
								.sendMessage(
										"***Welcome to the Quiz Bot***\n**New Game Started**\n\n **Use the following commands to run a quiz:**\n    /n ----- Get Next Question \n    /s ------Skip Question \n    /score- Current Score\n    /r ------Reset Your Score \n    /e ------Ends the quiz \n    /qhelp-Lists these options again \n    **/quiz--This will restart the quiz at any time**\n **Please Make Your Selection**")
								.queue();
					});
				} else if (msg.equals("/qhelp")) {
					author.openPrivateChannel().queue(privChannel -> {
						privChannel
							.sendMessage(
									"**Use the following commands to run a quiz**\n/n ----- Get Next Question \n/s ------Skip Question \n/score- Your Score\n/r ------Reset Your Score \n/e ------Ends the quiz \n /qhelp-Lists these options again \n\n **Please Make Your Selection**")
							.queue(); 
					});
				}
				else if((running)&&(questions.size() >= 0 )){
					questionCounter = questions.size();
					
					if ((msg.equals("/n")) || (msg.equals("/s"))) {  //**Next question or skip question
						Random rand = new Random();
						roll = rand.nextInt(questions.size());       //**This pulls a random question out of the array
						currentQ = questions.get(roll);
						questions.remove(roll);
						questionCounter = questions.size();
						author.openPrivateChannel().queue(privChannel -> {
							privChannel.sendMessage("***CHOOSE A LETTER*** \nFOR TESTING PURPOSES THE ANSWER IS : " + currentQ.getAnswer()).queue();
							msgchannel.sendMessage(currentQ.toString()).queue();
						});
						waiting = true;								//**Waiting for the user to answer the question
					} else if ((waiting == true)) {
										if (msg.equals(currentQ.getAnswer())) {  //**User enters right answer and gets point
							correctAnswers++;
							author.openPrivateChannel().queue(privChannel -> {
								privChannel
									.sendMessage("Correct Answer!! Congratulations!!" )
									.queue();
							});
							waiting = false;
						} else if (msg.equals("/skip")) {		           //**User skips question, stops waiting until user loads new question
							author.openPrivateChannel().queue(privChannel -> {
								privChannel.sendMessage("Answer Skipped - Type /n for next question.").queue();
							});
							waiting = false;
						} else if (!msg.equals(currentQ.getAnswer())) {   //**Wrong answer, lose a point
							correctAnswers--;
							author.openPrivateChannel().queue(privChannel -> {
								privChannel
									.sendMessage("Wrong Answer lose 1 point.\n*Try Again(chance losing another point?)*\n Or type /skip to go to next question")
									.queue();
							});
						}
						author.openPrivateChannel().queue(privChannel -> { //**Gives user information on current quiz
							privChannel
								.sendMessage("Your Score is : " + correctAnswers + "\nQuestions Left : " + questionCounter)
								.queue();
						});
						if(questions.size() == 0){          //**No questions left - gives final score
							author.openPrivateChannel().queue(privChannel -> {
								privChannel.sendMessage("You answered all the questions in this set. \nYour Final Score is " + correctAnswers).queue();
								running = false;
								waiting = false;
							});
						}
					}else if (msg.equals("/score")) {      //**Displays User Score
						author.openPrivateChannel().queue(privChannel -> {
							privChannel.sendMessage("*Your Score : " + correctAnswers + "*").queue(); 
						});
					} else if (msg.equals("/e")) {         //**Ends Quiz
						author.openPrivateChannel().queue(privChannel -> {
							privChannel.sendMessage("***Thank you for playing Quiz Bot*** \n**Your Final Score : "
								+ correctAnswers + "**" + "\n***Type /quiz to start the next quiz***").queue(); 
						});
						correctAnswers = 0;
						running = false;
					} else if (msg.equals("/r")) {         //**Resets Score
						correctAnswers = 0;
						author.openPrivateChannel().queue(privChannel -> {
							privChannel.sendMessage("Your Score has been reset to : " + correctAnswers).queue();
						});
					}
				}//****End IfRunning
			} // ******End Private Messages
	}//****************End IfBot
}// *******************End onMessage Received

	public static void initQuestions() {
		//***This reads all the questions from a file into an arrayList called questions.
		String tempString = "";
		String answer;
		int qNum = 0;
		String question = "";
		String choices = "";
		Questions tempQuestion;
		File qFile = new File("res/questions.txt");
		questions.clear();   //**Initialize the array as blank first
		
		try {				 //**Fill the array from the file
			Scanner input = new Scanner(qFile);
			while (input.hasNextLine()) {
				tempString = input.nextLine();
				answer = tempString.substring(0, tempString.indexOf(","));
				qNum = Integer
						.parseInt(tempString.substring((tempString.indexOf(",") + 1), (tempString.indexOf(",") + 2)));
				question = (tempString.substring(tempString.indexOf(",") + 3, tempString.length() - 1));
				choices = input.nextLine();
				tempQuestion = new Questions(answer, qNum, question, choices);
				questions.add(tempQuestion);
			}
			input.close();
		} catch (FileNotFoundException ex) {
			System.out.println("ERROR : No Questions File Found");  //**Error is sent to Eclipse console, not to the user
		}
	}// ---------End getQuestions Method
}// **********End Class

class Questions {
	private String answer;
	private String question;
	private String choices;
	private int qNum;

	public Questions(String a, int qN, String q, String c) {
		answer = a;
		qNum = qN;
		question = q;
		choices = c;
	}

	public String getAnswer() {
		return answer;
	}

	public int getqNum() {
		return qNum;
	}

	public String getQuestion() {
		return question;
	}

	public String getChoices() {
		return choices;
	}

	public String toString() {
		return question + "\n" + choices;
	}
}