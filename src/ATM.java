import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

public class ATM extends JFrame{

	private JPanel contentPane;
	private JButton Button_Op1;
	private JButton Button_Op2;
	private JButton Button_Op3;
	private JButton Button_Op4;
	private JButton Button_1;
	private JButton Button_2;
	private JButton Button_3;
	private JButton Button_4;
	private JButton Button_5;
	private JButton Button_6;
	private JButton Button_7;
	private JButton Button_8;
	private JButton Button_9;
	private JButton Button_0;
	private JButton Button_English;
	private JButton Button_Korean;
	private JButton Button_Cancel;
	private JButton Button_Clear;
	private JButton Button_Enter;
	private JLabel SKKU_ATM;
	private JLabel Logo;
	private JTextArea Message;
	
	private BankAccount found = null; //it is account found by the pin code.
	private BankAccount receiver = null; //it is account of receiver when transferring money
	
	private int state = 0; //it defines what state the user is in the service;
	private String UserInput = ""; //in is user's input using ATM number buttons.
	private boolean PinFound = false; //it is whether pin is found or not
	private double withdraw = 0; //withdraw amount
	private double deposit = 0; //deposit amount
	private double transfer = 0; //transfer amount
	private JButton Button_Forgot;
	private JButton Button_Mail;
	private JButton Button_Info;
	private JButton Button_Chinese;
	private JButton Button_Spanish;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATM frame = new ATM();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public ATM() {
		setTitle("ATM");
		
		ArrayList<BankAccount> bankAccounts = new ArrayList<>();
		User user1 = new User("Firuz");
		User user2 = new User("John");
		User user3 = new User("Eldor");
		
		bankAccounts.add(new BankAccount("200100237898", 1234, 500000.0, user1));
		bankAccounts.add(new BankAccount("110000022033", 4321, 700000.0, user2));
		bankAccounts.add(new BankAccount("111111111111", 2222, 900000.0, user3));
		
		//making valid user bank account
		
		//GUI by design
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 616);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		Logo = new JLabel("");
		Logo.setIcon(new ImageIcon(ATM.class.getResource("/images/woori.png")));
		
		SKKU_ATM = new JLabel("SKKU ATM");
		
		Message = new JTextArea();
		Message.setEditable(false);
		Message.setColumns(5);
		Message.setText("Please, insert your card and Press ENTER...");
		
		Button_English = new JButton("English");
		Button_English.setHorizontalAlignment(SwingConstants.LEFT);
		Button_English.setIcon(new ImageIcon(ATM.class.getResource("/images/eng.png")));
		
		Button_Korean = new JButton("Korean");
		Button_Korean.setHorizontalAlignment(SwingConstants.LEFT);
		Button_Korean.setIcon(new ImageIcon(ATM.class.getResource("/images/kor.png")));
		
		//option buttons, option1 ~ option4
		
		Button_Op1 = new JButton("Option 1"); //when option 1 is selected
		Button_Op1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Op1) {
					if (state == 2) {
						Message.setText("User: " + found.getBankUser().get_name() + "\n"
								+ "Balance: " + found.getBalance() + "\n"
								+ "Press ENTER..."); //show the user's balance
						state = 3; //change state to 3
					}
				}
			}
		});
		Button_Op1.setIcon(new ImageIcon(ATM.class.getResource("/images/arr.png")));
		
		Button_Op2 = new JButton("Option 2");
		Button_Op2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Op2) {
					if (state == 2) {
						state = 20; //change state to 20, option 2 withdraw mode
						Message.setText("Enter Withdrawal Amount: ");
					}
				}
			}
		});
		Button_Op2.setIcon(new ImageIcon(ATM.class.getResource("/images/arr.png")));
		
		Button_Op3 = new JButton("Option 3");
		Button_Op3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Op3) {
					if (state == 2) {
						state = 30; //change state to 30, option 3 deposit mode
						Message.setText("Enter Deposit Amount: ");
					}
				}
			}
		});
		Button_Op3.setIcon(new ImageIcon(ATM.class.getResource("/images/arr.png")));
		
		Button_Op4 = new JButton("Option 4");
		Button_Op4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Op4) {
					if (state == 2) {
						state = 40; //change state to 40, option 4 transfer mode
						Message.setText("Enter Account Number (Reciever): ");
					}
				}
			}
		});
		Button_Op4.setIcon(new ImageIcon(ATM.class.getResource("/images/arr.png")));
		
		//number buttons are activated in following states
		//state 1: input PIN number
		//state 20: option2, input withdraw amount
		//state 30: option3, input deposit amount
		//state 40, state 41: option4, input transferring account and input transferring amount
		//state 99, 100: special cases, wrong pin and find pin
		
		Button_1 = new JButton("");
		Button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_1) {
					//states when number button is activated
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) { 
						Message.append("1"); //append 1 in shown message
						UserInput += "1"; //append 1 in UserInput variable
					}
				}
			}
		});
		Button_1.setIcon(new ImageIcon(ATM.class.getResource("/images/1.png"))); //icon for Button 1
		//same for other number buttons
		
		Button_2 = new JButton("");
		Button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_2) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("2");
						UserInput += "2";
					}
				}
			}
		});
		Button_2.setIcon(new ImageIcon(ATM.class.getResource("/images/2.png")));
		
		Button_3 = new JButton("");
		Button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_3) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("3");
						UserInput += "3";
					}
				}
			}
		});
		Button_3.setIcon(new ImageIcon(ATM.class.getResource("/images/3.png")));
		
		Button_4 = new JButton("");
		Button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_4) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("4");
						UserInput += "4";
					}
				}
			}
		});
		Button_4.setIcon(new ImageIcon(ATM.class.getResource("/images/4.png")));
		
		Button_5 = new JButton("");
		Button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_5) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("5");
						UserInput += "5";
					}
				}
			}
		});
		Button_5.setIcon(new ImageIcon(ATM.class.getResource("/images/5.png")));
		
		Button_6 = new JButton("");
		Button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_6) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("6");
						UserInput += "6";
					}
				}
			}
		});
		Button_6.setIcon(new ImageIcon(ATM.class.getResource("/images/6.png")));
		
		Button_7 = new JButton("");
		Button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_7) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("7");
						UserInput += "7";
					}
				}
			}
		});
		Button_7.setIcon(new ImageIcon(ATM.class.getResource("/images/7.png")));
		
		Button_8 = new JButton("");
		Button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_8) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("8");
						UserInput += "8";
					}
				}
			}
		});
		Button_8.setIcon(new ImageIcon(ATM.class.getResource("/images/8.png")));
		
		Button_9 = new JButton("");
		Button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_9) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("9");
						UserInput += "9";
					}
				}
			}
		});
		Button_9.setIcon(new ImageIcon(ATM.class.getResource("/images/9.png")));
		
		Button_0 = new JButton("");
		Button_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_0) {
					if (state == 1 || state == 20 || state == 30 || state == 40 || state == 41 || state == 99 || state == 100) {
						Message.append("0");
						UserInput += "0";
					}
				}
			}
		});
		Button_0.setIcon(new ImageIcon(ATM.class.getResource("/images/0.png")));
		
		//cancel operation with cancel button
		Button_Cancel = new JButton("Cancel");
		Button_Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Cancel) {
					//set every variable to default when canceling process
					UserInput = "";
					found = null;
					receiver = null;
					state = 4;
					Message.setText("Process is canceled by user!\nPlease press ENTER...");
				}
			}
		});
		Button_Cancel.setHorizontalAlignment(SwingConstants.LEFT);
		Button_Cancel.setIcon(new ImageIcon(ATM.class.getResource("/images/cancel.png")));
		
		//clear button clears user input while typing information
		Button_Clear = new JButton("Clear");
		Button_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Clear) {
					UserInput = "";
					if (state == 1) {
						Message.setText("PIN: "); //clear when input PIN
					}
					if (state == 99) {
						Message.setText("Wrong pin! Try Again:\nPIN: "); //clear when input PIN
					}
					if (state == 20) {
						Message.setText("Enter Withdrawal Amount: "); //clear when input withdrawal amount
					}
					if (state == 30) {
						Message.setText("Enter Deposit Amount: "); //clear when input deposit amount
					}
					if (state == 40) {
						Message.setText("Enter Account Number (Reciever): "); //clear when input receiver account
					}
					if (state == 41) {
						Message.setText("Transfer Account: "+receiver.getBankUser().get_name()
				        		+ "\nEnter Transfer Ammount: "); //clear when input transfer amount
					}
					if (state == 100) {
						Message.setText("Please enter your phone number: ");
					}
 				}
			}
		});
		Button_Clear.setHorizontalAlignment(SwingConstants.LEFT);
		Button_Clear.setIcon(new ImageIcon(ATM.class.getResource("/images/clear.png")));
		
		
		//function of enter button is different depending on the state
		Button_Enter = new JButton("Enter");
		Button_Enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Enter) {
					
					//state 0 is beginning of the service
					if (state == 0) {
						Message.setText("PIN: ");
						state = 1;
					}
					
					//state 1 is set automatically after state 0
					//at state 1, number buttons are activated to enter PIN
					//at state 99, user is trying to input PIN again after error
					if (state == 1 || state == 99) {
						
						PinFound = false; //set PinFound false for default.
						
						while (true) {
						
							for (BankAccount account : bankAccounts) {
								//changes UserInput to Integer, and finds BankAccount with same pin code
							    if (account.getPinCode() == Integer.parseInt(UserInput)) {
							        found = account; //found BankAccount with same pin code
							        Message.setText("Welcome "+found.getBankUser().get_name()
							        		+"\nPlease choose options:\n"
							        		+"OPTION 1: Balance Checking\n"
							        		+"OPTION 2: Withdrawing Money\n"
							        		+"OPTION 3: Deposit\n"
							        		+"OPTION 4: Transfer");
							        state = 2; //option buttons are activated at state 2
							        PinFound = true;
							        break;
							    }
							}
							
							if (PinFound)
							{
								UserInput = ""; //empty user input for later use
								break;
							}
							
							else
							{
								Message.setText("Wrong pin! Try Again:\nPIN: ");
								UserInput = ""; //empty user input for later use
								state = 99; //error state for wrong pin
							}
						}
					}
					
					//state 3 is when certain option service is ended
					if (state == 3) {
						Message.setText("Thank you for banking with us!\n Press ENTER...");
						state = 4;
						found = null; //found Account is set null after service
					}
					
					//state 4 is same with state 0
					//user comes to state 0 when clicking enter at the beginning screen
					//user comes to state 4 when clicking enter after certain service ends  
					if (state == 4) {
						Message.setText("PIN: ");
						UserInput = ""; //make UserInput empty for after use
						state = 1;
					}
					
					//option 2 state
					if (state == 20) {
						withdraw = Double.parseDouble(UserInput); //UserInput to withdraw when clicking enter in state 20
						
						//when withdraw is unable due to amount
						if (withdraw > found.getBalance()) {
							Message.setText("Not enough money!\nPress ENTER...");
							state = 4;
							found = null;
						}
						//when withdraw is able
						else {
							found.setBalance(found.getBalance() - withdraw); //subtract withdraw from balance
							Message.setText("Success:)\nUser: "+ found.getBankUser().get_name()
							+ "\nWithdrawal Amount: " + withdraw
							+ "\nCurrent Balance: " + found.getBalance()
							+ "\nPress Enter...");
							state = 4;
							found = null;
						}
					}
					
					if (state == 30) {
						deposit = Double.parseDouble(UserInput); //UserInput to withdraw when clicking enter in state 30
						found.setBalance(found.getBalance() + deposit); //add deposit from balance
						Message.setText("Success:)\nUser: "+ found.getBankUser().get_name()
								+ "\nDeposit Amount: " + deposit
								+ "\nCurrent Balance: " + found.getBalance()
								+ "\nPress Enter...");
								state = 4;
								found = null;
					}
					
					if (state == 40) {
						
						for (BankAccount account : bankAccounts) {
							//find BankAccount with same bank account number
							
						    if (account.getBankNumber().equals(UserInput)) {
						    	state = 41;
						    	UserInput = "";
						        receiver = account; //receiver BankAccount with same pin code
						        Message.setText("Transfer Account: "+receiver.getBankUser().get_name()
						        		+ "\nEnter Transfer Ammount: ");
						    }
						}
						
						if (receiver == null) //if receiver account could not be found
						{
							Message.setText("You entered the wrong account number!\nPress ENTER...");
							UserInput = ""; //empty user input for later use
							state = 4;
						}
					}
					
					if (state == 41) {
						
						transfer = Double.parseDouble(UserInput); //UserInput to transfer
						
						if (transfer > found.getBalance()) {
							Message.setText("Not enough money!\nPress ENTER...");
							state = 4;
							found = null;
						}
						
						else {
							found.setBalance(found.getBalance() - transfer); //subtract money of sender
							receiver.setBalance(receiver.getBalance() + transfer); //add money of receiver
							
							Message.setText("Transfer Amount: "+ transfer
									+ "\nCurrent Balance: " + found.getBalance()
									+ "\nPress Enter...");
									state = 4;
									found = null;
									receiver = null; //set receiver to null for later use
						}
						
					}
					
					if (state == 100) {
						Message.setText("Calling...click mail button after voice authentication.");
						state = 101;
					}
					
				}
			}
		});
		Button_Enter.setHorizontalAlignment(SwingConstants.LEFT);
		Button_Enter.setIcon(new ImageIcon(ATM.class.getResource("/images/enter.png")));
		
		Button_Forgot = new JButton("");
		Button_Forgot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Forgot) {
					if (state == 1 || state == 99) {
						state = 100; //another special state
						Message.setText("Please enter your phone number: ");
					}
				}
			}
		});
		Button_Forgot.setIcon(new ImageIcon(ATM.class.getResource("/images/forgotPIN.png")));
		
		Button_Mail = new JButton("");
		Button_Mail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Mail) {
					if (state == 101) {
						UserInput = ""; //empty UserInput before log in
						Message.setText("PIN mail sent to your phone.\nPIN: ");
						state = 1;
					}
				}
			}
		});
		Button_Mail.setIcon(new ImageIcon(ATM.class.getResource("/images/mail.png")));
		
		Button_Info = new JButton("Info");
		Button_Info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Button_Info) {
					if (state == 0) {
						Message.setText("Information to use ATM\n\n"
								+ "1. Enter PIN number to log in.\n"
								+ "2. If you forgot PIN, click Lock button.\n"
								+ "3. After entering, you can use following services.\n"
								+ "(1) Balance Check.\n"
								+ "(2) Withdraw & Deposit Money.\n"
								+ "(3) Tranfer Money.\n"
								+ "Press ENTER to continue...");
					}
				}
			}
		});
		Button_Info.setHorizontalAlignment(SwingConstants.LEFT);
		Button_Info.setIcon(new ImageIcon(ATM.class.getResource("/images/question.png")));
		
		Button_Chinese = new JButton("Chinese");
		Button_Chinese.setHorizontalAlignment(SwingConstants.LEFT);
		Button_Chinese.setIcon(new ImageIcon(ATM.class.getResource("/images/china.png")));
		
		Button_Spanish = new JButton("Spanish");
		Button_Spanish.setHorizontalAlignment(SwingConstants.LEFT);
		Button_Spanish.setIcon(new ImageIcon(ATM.class.getResource("/images/spain.png")));
		
		//layout using design
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Logo, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(Button_Forgot, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
										.addComponent(Button_Op2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(Button_Op3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(Button_Op1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(Button_Op4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
														.addComponent(Button_7, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
															.addComponent(Button_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
															.addComponent(Button_1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))
													.addPreferredGap(ComponentPlacement.RELATED, 12, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(Button_Mail)
													.addPreferredGap(ComponentPlacement.RELATED)))
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(Button_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(Button_2, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
												.addComponent(Button_8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(Button_0, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
													.addGroup(gl_contentPane.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(Button_3, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
													.addComponent(Button_6, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
												.addComponent(Button_9, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)))
										.addComponent(Message, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(Button_Spanish, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(Button_Chinese, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(Button_Korean, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(Button_Enter, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(Button_Clear, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(Button_Cancel, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(Button_English, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
										.addComponent(Button_Info, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(231)
							.addComponent(SKKU_ATM)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(Logo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(SKKU_ATM)
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(Button_Op1)
							.addGap(14)
							.addComponent(Button_Op2)
							.addGap(15)
							.addComponent(Button_Op3)
							.addGap(12)
							.addComponent(Button_Op4))
						.addComponent(Message, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(Button_English)
							.addGap(4)
							.addComponent(Button_Korean)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Button_Chinese)
							.addGap(11)
							.addComponent(Button_Spanish)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(Button_Clear)
										.addComponent(Button_1)
										.addComponent(Button_2))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(Button_Cancel)
										.addComponent(Button_4)
										.addComponent(Button_5)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(Button_3)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(Button_6)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Button_9)
								.addComponent(Button_Enter)
								.addComponent(Button_8)
								.addComponent(Button_7))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(Button_Info, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(Button_Mail, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(Button_0, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addComponent(Button_Forgot, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
					.addGap(54))
		);
		contentPane.setLayout(gl_contentPane);
	}

}
