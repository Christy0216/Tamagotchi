import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class represents the view for the pet game.
 */
public class PetView extends JFrame {

  private JLabel healthLabel;
  private JLabel hungerLabel;
  private JLabel hygieneLabel;
  private JLabel socialLabel;
  private JLabel sleepLabel;
  private JLabel moodLabel;
  private JLabel personalityLabel;
  private JLabel lastInteractedLabel;
  private JButton feedButton;
  private JButton cleanButton;
  private JButton playButton;
  private JButton sleepButton;
  private JButton confirmButton;
  private JButton getPreferredActionButton;
  private JButton performPreferredActionButton;
  private JComboBox<String> personalityComboBox;
  private JTextArea messageArea;
  private PetController controller;
  private boolean hungerAlertShown = false;
  private boolean sleepAlertShown = false;
  private boolean hygieneAlertShown = false;
  private boolean socialAlertShown = false;

  /**
   * Constructor for the view.
   */
  public PetView() {
    setTitle("Tamagotchi Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 600);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    personalityLabel = new JLabel("Choose Personality:");
    personalityComboBox = new JComboBox<>(new String[] {"Happy", "Grumpy", "Normal"});
    confirmButton = new JButton("Confirm");

    panel.add(personalityLabel);
    panel.add(personalityComboBox);
    panel.add(confirmButton);

    // Other attributes initially hidden
    healthLabel = new JLabel("Health:");
    hungerLabel = new JLabel("Hunger:");
    hygieneLabel = new JLabel("Hygiene:");
    socialLabel = new JLabel("Social:");
    sleepLabel = new JLabel("Sleep:");
    moodLabel = new JLabel("Mood:");
    lastInteractedLabel = new JLabel("Last Interaction: ");

    feedButton = new JButton("Feed");
    cleanButton = new JButton("Clean");
    playButton = new JButton("Play");
    sleepButton = new JButton("Sleep");
    getPreferredActionButton = new JButton("Get Preferred Action");
    performPreferredActionButton = new JButton("Perform Preferred Action");

    panel.add(healthLabel);
    panel.add(hungerLabel);
    panel.add(hygieneLabel);
    panel.add(socialLabel);
    panel.add(sleepLabel);
    panel.add(moodLabel);
    panel.add(personalityLabel);
    panel.add(lastInteractedLabel);

    panel.add(feedButton);
    panel.add(cleanButton);
    panel.add(playButton);
    panel.add(sleepButton);
    panel.add(getPreferredActionButton);
    panel.add(performPreferredActionButton);

    // Initially hide other attributes and buttons
    toggleAttributesVisibility(false);
    toggleButtonsVisibility(false);
    togglePersonalitySelection(true);

    // Add a message area to display messages
    messageArea = new JTextArea(10, 30);
    messageArea.setEditable(false); // Make the messageArea read-only
    JScrollPane scrollPane = new JScrollPane(messageArea);
    panel.add(scrollPane);

    add(panel);
  }

  /**
   * This method displays the personality selection dialog.
   */
  public void displayPersonalitySelectionDialog() {
    String[] options = {"Happy", "Grumpy", "Normal"};
    int choice = JOptionPane.showOptionDialog(
        this,
        "Choose the pet's personality:",
        "Personality Selection",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[2]
    );

    if (choice == JOptionPane.CLOSED_OPTION) {
      System.exit(0);
    } else {
      String selectedPersonality = options[choice];
      int confirmation = JOptionPane.showConfirmDialog(
          this,
          "Are you sure you want to select the " + selectedPersonality + " personality?",
          "Confirmation",
          JOptionPane.YES_NO_OPTION
      );

      if (confirmation == JOptionPane.YES_OPTION) {
        notifyControllerOfPersonalityChoice(choice);
        confirmPersonalitySelection();
      } else {
        displayPersonalitySelectionDialog();
      }
    }
  }

  /**
   * This method sets the controller for the view.
   *
   * @param controller the controller to be set
   */
  public void setController(PetController controller) {
    this.controller = controller;
  }

  /**
   * This method notifies the controller of the personality choice.
   *
   * @param choice the index of the personality
   */
  public void notifyControllerOfPersonalityChoice(int choice) {
    PersonalityStrategy selectedPersonality = mapIndexToPersonality(choice);
    controller.handleSelectedPersonality(selectedPersonality);
  }

  /**
   * This method maps the index of the personality to the personality.
   *
   * @param choice the index of the personality
   * @return the personality
   */
  private PersonalityStrategy mapIndexToPersonality(int choice) {
    PersonalityStrategy selectedPersonality;
    switch (choice) {
      case 0:
        selectedPersonality = new HappyPersonality();
        messageArea.append("You have selected the Happy personality.\n");
        break;
      case 1:
        selectedPersonality = new GrumpyPersonality();
        messageArea.append("You have selected the Grumpy personality.\n");
        break;
      case 2:
        selectedPersonality = new NormalPersonality();
        messageArea.append("You have selected the Normal personality.\n");
        break;
      default:
        selectedPersonality = new NormalPersonality();
        messageArea.append("Default Normal Personality is set.\n");
        break;
    }
    return selectedPersonality;
  }

  /**
   * This method appends a message to the message area.
   *
   * @param message the message to be appended
   * @return null
   */
  public TimerTask appendMessage(String message) {
    messageArea.append(message + "\n"); // Append the message to the messageArea
    return null;
  }

  /**
   * This method updates the health status label.
   *
   * @param health the health status to be displayed
   */
  public void updateHealth(int health) {
    healthLabel.setText("Health: " + health);
  }

  /**
   * This method updates the hunger label.
   *
   * @param hunger the hunger status to be displayed
   */
  public void updateHunger(int hunger) {
    hungerLabel.setText("Hunger: " + hunger);
  }

  /**
   * This method updates the hygiene label.
   *
   * @param hygiene the hygiene status to be displayed
   */
  public void updateHygiene(int hygiene) {
    hygieneLabel.setText("Hygiene: " + hygiene);
  }

  /**
   * This method updates the social label.
   *
   * @param social the social status to be displayed
   */
  public void updateSocial(int social) {
    socialLabel.setText("Social: " + social);
  }

  /**
   * This method updates the sleep label.
   *
   * @param sleep the sleep status to be displayed
   */
  public void updateSleep(int sleep) {
    sleepLabel.setText("Sleep: " + sleep);
  }

  /**
   * This method updates the mood label.
   *
   * @param string the mood to be displayed
   */
  public void updateMood(String string) {
    moodLabel.setText("Mood: " + string);
  }

  /**
   * This method updates the personality label.
   *
   * @param personality the personality to be displayed
   */
  public void updatePersonality(String personality) {
    personalityLabel.setText("Personality: " + personality);
  }

  /**
   * This method toggles the visibility of the attributes.
   *
   * @param visible whether the attributes should be visible
   */
  public void toggleAttributesVisibility(boolean visible) {
    healthLabel.setVisible(visible);
    hungerLabel.setVisible(visible);
    hygieneLabel.setVisible(visible);
    socialLabel.setVisible(visible);
    sleepLabel.setVisible(visible);
    moodLabel.setVisible(visible);
    personalityLabel.setVisible(visible);
  }

  /**
   * This method toggles the visibility of the personality selection components.
   *
   * @param visible whether the personality selection components should be visible
   */
  public void togglePersonalitySelection(boolean visible) {
    personalityLabel.setVisible(visible);
    personalityComboBox.setVisible(visible);
    confirmButton.setVisible(visible);
  }

  /**
   * This method toggles the visibility of the buttons.
   *
   * @param visible whether the buttons should be visible
   */
  public void toggleButtonsVisibility(boolean visible) {
    feedButton.setVisible(visible);
    cleanButton.setVisible(visible);
    playButton.setVisible(visible);
    sleepButton.setVisible(visible);
    getPreferredActionButton.setVisible(visible);
    performPreferredActionButton.setVisible(visible);
  }

  /**
   * This method adds a feed listener to the feed button.
   *
   * @param feedListener the listener to be added
   */
  public void addFeedListener(ActionListener feedListener) {
    feedButton.addActionListener(feedListener);
  }

  /**
   * This method adds a clean listener to the clean button.
   *
   * @param cleanListener the listener to be added
   */
  public void addCleanListener(ActionListener cleanListener) {
    cleanButton.addActionListener(cleanListener);
  }

  /**
   * This method adds a play listener to the play button.
   *
   * @param playListener the listener to be added
   */
  public void addPlayListener(ActionListener playListener) {
    playButton.addActionListener(playListener);
  }

  /**
   * This method adds a sleep listener to the sleep button.
   *
   * @param sleepListener the listener to be added
   */
  public void addSleepListener(ActionListener sleepListener) {
    sleepButton.addActionListener(sleepListener);
  }

  /**
   * This method updates the lastInteractedLabel.
   *
   * @param timeInMillis the time in milliseconds
   */
  public void updateLastInteractedTime(long timeInMillis) {
    // Convert the time to a readable format if needed
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedTime = sdf.format(new Date(timeInMillis));

    // Update the lastInteractedLabel in the view
    lastInteractedLabel.setText("Last Interaction: " + formattedTime);
  }

  /**
   * This method disables the personality selection components.
   */
  public void confirmPersonalitySelection() {
    personalityComboBox.setEnabled(false);
    confirmButton.setEnabled(false);
    togglePersonalitySelection(false);
    toggleAttributesVisibility(true);
    toggleButtonsVisibility(true);
  }

  /**
   * This method toggles the availability of the buttons.
   *
   * @param enabled whether the buttons should be enabled
   */
  public void toggleButtonsAvailability(boolean enabled) {
    feedButton.setEnabled(enabled);
    cleanButton.setEnabled(enabled);
    playButton.setEnabled(enabled);
    sleepButton.setEnabled(enabled);
    getPreferredActionButton.setEnabled(enabled);
    performPreferredActionButton.setEnabled(enabled);
  }

  /**
   * This method displays the health status message.
   *
   * @param message the message to be displayed
   */
  public void displayHealthStatusMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Pet Health Status",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * This method adds a preferred action listener to the get preferred action button.
   *
   * @param listener the listener to be added
   */
  public void addPreferredActionListener(ActionListener listener) {
    getPreferredActionButton.addActionListener(listener);
  }

  /**
   * This method adds a perform preferred action listener to the perform preferred action button.
   *
   * @param listener the listener to be added
   */
  public void addPerformPreferredActionListener(ActionListener listener) {
    performPreferredActionButton.addActionListener(listener);
  }

  /**
   * This method displays the preferred action dialog.
   */
  public void displayPreferredActionDialog() {
    Action preferredAction = controller.getPreferredActionFromModel();
    JOptionPane.showMessageDialog(this, "Preferred Action: " + preferredAction, "Preferred Action",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * This method displays the hunger alert.
   */
  public void displayHungerAlert() {
    if (!hungerAlertShown) {
      Object[] options = {"Feed", "Dismiss"};
      int choice = JOptionPane.showOptionDialog(
          null,
          "Your pet is hungry!",
          "Hunger Alert",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE,
          null,
          options,
          options[0]
      );

      if (choice == JOptionPane.YES_OPTION) {
        controller.handleFeedAction(); // Call the method in the controller to feed the pet
      }
      hungerAlertShown = true;
    }
  }

  /**
   * This method displays the hygiene alert.
   */
  public void displayHygieneAlert() {
    if (!hygieneAlertShown) {
      Object[] options = {"Clean", "Dismiss"};
      int choice = JOptionPane.showOptionDialog(
          null,
          "Your pet is dirty!",
          "Hygiene Alert",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE,
          null,
          options,
          options[0]
      );

      if (choice == JOptionPane.YES_OPTION) {
        controller.handleCleanAction(); // Call the method in the controller to clean the pet
      }
      hygieneAlertShown = true;
    }
  }

  /**
   * This method displays the social alert.
   */
  public void displaySocialAlert() {
    if (!socialAlertShown) {
      Object[] options = {"Play", "Dismiss"};
      int choice = JOptionPane.showOptionDialog(
          null,
          "Your pet is lonely!",
          "Social Alert",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE,
          null,
          options,
          options[0]
      );

      if (choice == JOptionPane.YES_OPTION) {
        controller.handlePlayAction(); // Call the method in the controller to play with the pet
      }
      socialAlertShown = true;
    }
  }

  /**
   * This method displays the sleep alert.
   */
  public void displaySleepAlert() {
    if (!sleepAlertShown) {
      Object[] options = {"Sleep", "Dismiss"};
      int choice = JOptionPane.showOptionDialog(
          null,
          "Your pet is sleepy!",
          "Sleep Alert",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.WARNING_MESSAGE,
          null,
          options,
          options[0]
      );

      if (choice == JOptionPane.YES_OPTION) {
        controller.handleSleepAction(); // Call the method in the controller to put the pet to sleep
      }
      sleepAlertShown = true;
    }
  }

  /**
   * This method resets the hygiene alert.
   */
  public void resetHygieneAlert() {
    hygieneAlertShown = false;
  }

  /**
   * This method resets the hunger alert.
   */
  public void resetHungerAlert() {
    hungerAlertShown = false;
  }

  /**
   * This method resets the sleep alert.
   */
  public void resetSleepAlert() {
    sleepAlertShown = false;
  }

  /**
   * This method resets the social alert.
   */
  public void resetSocialAlert() {
    socialAlertShown = false;
  }

  /**
   * This method resets the view.
   */
  public void reset() {
    messageArea.setText("");

    toggleAttributesVisibility(false);
    toggleButtonsVisibility(false);

    personalityLabel.setVisible(false);
    personalityComboBox.setVisible(false);
    confirmButton.setVisible(false);

    personalityComboBox.setSelectedIndex(0);
    personalityComboBox.setEnabled(true);
    confirmButton.setEnabled(true);

    togglePersonalitySelection(true);

    resetHygieneAlert();
    resetHungerAlert();
    resetSleepAlert();
    resetSocialAlert();

    displayPersonalitySelectionDialog();
  }

  /**
   * This method displays the preferred action change message.
   */
  public void displayPreferredActionChangeMessage() {
    JOptionPane.showMessageDialog(this, "The pet's preferred action is changing.", "Action Change",
        JOptionPane.INFORMATION_MESSAGE);
  }

}