import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * This class represents the controller for the pet game. It connects the model and the view.
 */
public class PetController {

  private final Pet model;
  private final PetView view;
  private String previousHealthStatus = "";
  private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
  private Action previousPreferredAction = null;
  private boolean isFirstActionSet = true;

  /**
   * Constructor for the controller.
   *
   * @param model the model
   * @param view  the view
   */
  public PetController(Pet model, PetView view) {
    this.model = model;
    this.view = view;

    view.setController(this);

    view.displayPersonalitySelectionDialog();
    if (model.getPersonality() != null) {
      model.setAllowTaskExecution();
    }

    model.startTimer();
    Timer timer = new Timer();

    // Schedule a task for periodic view updates
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (model.checkDeath()) {
          timer.cancel();
        }
        updateView();
      }
    }, 0, 5000);

    view.addFeedListener(e -> handleFeedAction());
    view.addCleanListener(e -> handleCleanAction());
    view.addPlayListener(e -> handlePlayAction());
    view.addSleepListener(e -> handleSleepAction());
    view.addPreferredActionListener(e -> getPreferredActionButton());
    view.addPerformPreferredActionListener(e -> performPreferredActionButton());
  }

  /**
   * This method updates the view.
   */
  private void updateView() {
    view.updateHealth(model.getHealth());
    view.updateHunger(model.getHunger());
    view.updateHygiene(model.getHygiene());
    view.updateSocial(model.getSocial());
    view.updateSleep(model.getSleep());
    view.updateMood(model.getMood().toString());

    PersonalityStrategy personality = model.getPersonality();

    if (personality != null) {
      view.updatePersonality(personality.getClass().getSimpleName());
    } else {
      view.updatePersonality("No Personality Set");
    }

    long lastInteractedTime = model.getLastInteractedTime();
    view.updateLastInteractedTime(lastInteractedTime);

    updateHealthStatus();
    checkForPreferredActionChange();

    if (model.checkDeath()) {
      handlePetDeath();
    } else {
      if (model.getHunger() > 60) {
        view.displayHungerAlert();
      } else {
        view.resetHungerAlert();
      }

      if (model.getHygiene() < 40) {
        view.displayHygieneAlert();
      } else {
        view.resetHygieneAlert();
      }

      if (model.getSocial() < 40) {
        view.displaySocialAlert();
      } else {
        view.resetSocialAlert();
      }

      if (model.getSleep() > 60) {
        view.displaySleepAlert();
      } else {
        view.resetSleepAlert();
      }
    }
  }

  /**
   * This method updates the health status of the pet.
   */
  public void updateHealthStatus() {
    String currentHealthStatus = model.checkPetHealthStatus();

    if (!currentHealthStatus.equals(previousHealthStatus)) {
      view.displayHealthStatusMessage(currentHealthStatus);
      previousHealthStatus = currentHealthStatus;
    }
  }

  /**
   * This method checks if the preferred action has changed.
   */
  private void checkForPreferredActionChange() {
    Action currentPreferredAction = model.getPreferredAction();
    if (!isFirstActionSet && currentPreferredAction != previousPreferredAction) {
      view.displayPreferredActionChangeMessage();
    }

    if (previousPreferredAction != currentPreferredAction) {
      previousPreferredAction = currentPreferredAction;
      isFirstActionSet = false;
    }
  }

  /**
   * This method restarts the game.
   */
  private void restartGame() {
    // Reset the model
    model.reset();

    // Reset any other necessary states or flags
    previousHealthStatus = "";
    isFirstActionSet = true;

    // Update view to reflect the reset model
    view.reset();

    // Update the view to reflect changes in the model
    updateView();
    view.setController(this);

    if (model.getPersonality() != null) {
      model.setAllowTaskExecution();
    }

    model.startTimer();
    Timer timer = new Timer();

    // Schedule a task for periodic view updates
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (model.checkDeath()) {
          timer.cancel();
        }
        updateView();
      }
    }, 0, 5000);
  }

  /**
   * This method handles the selection of personality.
   *
   * @param choice the personality choice
   */
  public void handleSelectedPersonality(PersonalityStrategy choice) {
    // Set the personality of the pet
    model.setPersonality(choice);
    model.setAllowTaskExecution();
  }

  /**
   * This method handles the feed action.
   */
  public void handleFeedAction() {
    model.feed();
    updateView();
    view.appendMessage(model.getMessage());
  }

  /**
   * This method handles the clean action.
   */
  public void handleCleanAction() {
    model.clean();
    updateView();
    view.appendMessage(model.getMessage());
  }

  /**
   * This method handles the sleep action.
   */
  public void handleSleepAction() {
    try {
      model.sleep();
    } catch (IllegalStateException e) {
      view.appendMessage(e.getMessage());
      return;
    }
    view.appendMessage(model.getMessage());
    executorService.schedule(() -> {
      SwingUtilities.invokeLater(() -> {
        view.appendMessage(model.getMessage()); // 一分钟后执行
        updateView();
      });
    }, 1, TimeUnit.MINUTES);
  }

  /**
   * This method handles the play action.
   */
  public void handlePlayAction() {
    model.play();
    updateView();
    view.appendMessage(model.getMessage());
  }

  /**
   * This method gets the preferred action from the model.
   *
   * @return the preferred action
   */
  public Action getPreferredActionFromModel() {
    return model.getPreferredAction();
  }

  /**
   * This method performs the preferred action.
   */
  public void getPreferredActionButton() {
    view.displayPreferredActionDialog();
  }

  /**
   * This method performs the preferred action.
   */
  public void performPreferredActionButton() {
    model.performPreferredAction();
    Action preferredAction = model.getPreferredAction();

    if (preferredAction != null && preferredAction.equals(Action.SLEEP)) {
      view.appendMessage(model.getMessage());
      executorService.schedule(() -> {
        SwingUtilities.invokeLater(() -> {
          view.appendMessage("Your pet woke up!"); // 在一分钟后执行
          updateView();
        });
      }, 1, TimeUnit.MINUTES);
    } else {
      view.appendMessage(model.getMessage());
      updateView();
    }
  }

  /**
   * This method handles the death of the pet.
   */
  public void handlePetDeath() {
    Object[] options = {"End Game", "Restart"};
    int choice = JOptionPane.showOptionDialog(
        view,
        "Your pet has died. What would you like to do?",
        "Pet Death",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]
    );

    if (choice == JOptionPane.YES_OPTION) {
      view.toggleAttributesVisibility(true);
      view.toggleButtonsAvailability(false);
      view.appendMessage("Game Over");
    } else if (choice == JOptionPane.NO_OPTION) {
      restartGame();
    }
  }
}