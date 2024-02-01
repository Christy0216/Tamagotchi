import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a pet.
 */
public class Pet implements PetModel {

  private int hunger;
  private int hygiene;
  private int social;
  private int sleep;
  private int health;

  private Mood mood;
  private PersonalityStrategy personality;
  private Action preferredAction;

  private long lastInteractedTime;

  private boolean isSleeping;
  private TimeSimulator timeSimulator;
  private ScheduledExecutorService executorService1;
  private ScheduledExecutorService executorService2;
  private boolean personalitySet = false;

  private String message;

  private boolean isDead = false;
  private boolean allowTaskExecution = false;


  /**
   * Constructor for the Pet class.
   */
  public Pet() {
    hunger = 20;
    hygiene = 80;
    social = 80;
    sleep = 20;
    health = 100;
    mood = Mood.HAPPY;

    timeSimulator = new TimeSimulator(this);
    lastInteractedTime = System.currentTimeMillis();
    executorService1 = Executors.newScheduledThreadPool(1);
  }

  /**
   * Start the timer.
   */
  public void startTimer() {
    executorService1.scheduleAtFixedRate(() -> {
      if (allowTaskExecution) {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastInteraction = (currentTime - lastInteractedTime) / 1000;

        if (timeSinceLastInteraction > 1) {
          timeSimulator.startAttributeUpdates();
        }
        update();
      }
    }, 3, 5, TimeUnit.SECONDS);

    if (health <= 0) {
      executorService1.shutdown();
    }
  }

  public void setAllowTaskExecution() {
    allowTaskExecution = true;
  }

  /**
   * Set the hunger level of the pet.
   *
   * @param hunger the hunger level of the pet.
   */
  public void setHunger(int hunger) {
    if (hunger < 0) {
      hunger = 0;
      System.out.println("The minimum hunger level is 0.");
    }
    if (hunger > 100) {
      hunger = 100;
      System.out.println("The maximum hunger level is 100.");
    }
    this.hunger = hunger;
  }

  /**
   * Set the hygiene level of the pet.
   *
   * @param hygiene the hygiene level of the pet.
   */
  public void setHygiene(int hygiene) {
    if (hygiene < 0) {
      hygiene = 0;
      System.out.println("The minimum hygiene level is 0.");
    }
    if (hygiene > 100) {
      hygiene = 100;
      System.out.println("The maximum hygiene level is 100.");
    }
    this.hygiene = hygiene;
  }

  /**
   * Set the social level of the pet.
   *
   * @param social the social level of the pet.
   */
  public void setSocial(int social) {
    if (social < 0) {
      social = 0;
      System.out.println("The minimum social level is 0.");
    }
    if (social > 100) {
      social = 100;
      System.out.println("The maximum social level is 100.");
    }
    this.social = social;
  }

  /**
   * Set the sleep level of the pet.
   *
   * @param sleep the sleep level of the pet.
   */
  public void setSleep(int sleep) {
    if (sleep < 0) {
      sleep = 0;
      System.out.println("The minimum sleep level is 0.");
    }
    if (sleep > 100) {
      sleep = 100;
      System.out.println("The maximum sleep level is 100.");
    }
    this.sleep = sleep;
  }

  /**
   * Set the health level of the pet.
   *
   * @param health the health level of the pet.
   */
  public void setHealth(int health) {
    if (health < 0) {
      health = 0;
      System.out.println("The minimum health level is 0.");
    }
    if (health > 100) {
      health = 100;
      System.out.println("The maximum health level is 100.");
    }
    this.health = health;
  }

  /**
   * Feed th pet, the hunger level will decrease by 10 and the health level will increase by 5.
   */
  @Override
  public void feed() {
    if (isSleeping) {
      message = "Pet is sleeping. Please wait until it wakes up.";
      lastInteractedTime = System.currentTimeMillis();
      return;
    }
    message = "You fed your pet!";
    hunger -= 10;
    if (hunger < 0) {
      hunger = 0;
    }
    health += 5;
    lastInteractedTime = System.currentTimeMillis();
    checkBounds();
  }

  /**
   * Clean the pet, the hygiene level increase by 10 and the health level will increase by 5.
   */
  @Override
  public void clean() {
    if (isSleeping) {
      message = "Pet is sleeping. Please wait until it wakes up.";
      lastInteractedTime = System.currentTimeMillis();
      return;
    }
    message = "You cleaned your pet!";
    hygiene += 10;
    if (hygiene > 100) {
      hygiene = 100;
    }
    health += 5;
    lastInteractedTime = System.currentTimeMillis();
    checkBounds();
  }

  /**
   * Play with the pet, the social level will increase by 10 and the health level will increase by
   * 5.
   */
  @Override
  public void play() {
    if (isSleeping) {
      message = "Pet is sleeping. Please wait until it wakes up.";
      lastInteractedTime = System.currentTimeMillis();
      return;
    }
    message = "You played with your pet!";
    social += 10;
    if (social < 0) {
      social = 0;
    }
    health += 5;
    lastInteractedTime = System.currentTimeMillis();
    checkBounds();
  }

  /**
   * Put the pet to sleep, the sleep level will decrease by 10 and the health level will increase by
   * 5.
   */
  @Override
  public void sleep() throws IllegalStateException {
    executorService2 = Executors.newScheduledThreadPool(1);
    if (!isSleeping) {
      isSleeping = true;
      message = "You put your pet to sleep!";
      executorService2.schedule(this::wakeUp, 10, TimeUnit.SECONDS);
    } else {
      throw new IllegalStateException("Pet is already sleeping");
    }
  }

  /**
   * Wake the pet up.
   */
  private void wakeUp() {
    isSleeping = false;
    message = "Your pet woke up!";
    lastInteractedTime = System.currentTimeMillis();
    sleep -= 10;
    if (sleep > 100) {
      sleep = 100;
    }
    health += 5;
    checkBounds();
  }

  /**
   * Get the current hunger level of the pet.
   */
  @Override
  public int getHunger() {
    checkBounds();
    return hunger;
  }

  /**
   * Get the current hygiene level of the pet.
   */
  @Override
  public int getHygiene() {
    checkBounds();
    return hygiene;
  }

  /**
   * Get the current social level of the pet.
   */
  @Override
  public int getSocial() {
    checkBounds();
    return social;
  }

  /**
   * Get the current sleep level of the pet.
   */
  @Override
  public int getSleep() {
    checkBounds();
    return sleep;
  }

  /**
   * Get the current health level of the pet.
   */
  @Override
  public int getHealth() {
    checkBounds();
    return health;
  }

  /**
   * Get the personality of the pet.
   */
  @Override
  public PersonalityStrategy getPersonality() {
    return personality;
  }

  /**
   * Get the last time the pet was interacted with.
   */
  @Override
  public long getLastInteractedTime() {
    return lastInteractedTime;
  }

  /**
   * Get the message.
   *
   * @return the message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Display the attributes: hunger, clean, sleep, social, of the pet.
   */
  @Override
  public String displayStates() {
    checkBounds();
    String[] states = new String[] {
        "Hunger: " + hunger,
        "Hygiene: " + hygiene,
        "Social: " + social,
        "Sleep: " + sleep,
        "Health: " + health,
        "Mood: " + mood,
        "Personality: " + personality.getClass().getSimpleName()
    };
    return String.join("\n", states);
  }

  /**
   * Assign a personality to the pet.
   */
  public void assignBehavior() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Choose the pet's personality:");
    System.out.println("1. Happy");
    System.out.println("2. Grumpy");
    System.out.println("3. Normal");
    System.out.print("Enter your choice:\n");

    int choice = scanner.nextInt();

    switch (choice) {
      case 1:
        setPersonality(new HappyPersonality());
        System.out.println("Happy personality set.");
        break;
      case 2:
        setPersonality(new GrumpyPersonality());
        System.out.println("Grumpy personality set.");
        break;
      case 3:
        setPersonality(new NormalPersonality());
        System.out.println("Normal personality set.");
        break;
      default:
        System.out.println("Invalid choice. Setting default personality.");
        setPersonality(new NormalPersonality());
        break;
    }
    scanner.close();
  }

  /**
   * Increase the hunger level of the pet.
   */
  public void increaseHunger() {
    hunger += 5;
    if (hunger > 100) {
      hunger = 100;
    }
  }

  /**
   * Increase the sleep level of the pet.
   */
  public void increaseSleep() {
    sleep += 5;
    if (sleep > 100) {
      sleep = 100;
    }
  }

  /**
   * Decrease the hygiene level of the pet.
   */
  public void decreaseHygiene() {
    hygiene -= 2;
    if (hygiene < 0) {
      hygiene = 0;
    }
  }

  /**
   * Decrease the social level of the pet.
   */
  public void decreaseSocial() {
    social -= 2;
    if (social < 0) {
      social = 0;
    }
  }

  /**
   * Check if the pet's attributes are within the bounds.
   */
  public void checkBounds() {
    hunger = Math.min(Math.max(hunger, 0), 100);
    hygiene = Math.min(Math.max(hygiene, 0), 100);
    social = Math.min(Math.max(social, 0), 100);
    sleep = Math.min(Math.max(sleep, 0), 100);
    health = Math.min(Math.max(health, 0), 100);
  }

  /**
   * Get the mood of the pet.
   */
  @Override
  public Mood getMood() {
    if (hunger > 60 | hygiene < 40 | social < 40 | sleep > 60) {
      mood = Mood.SAD;
    } else {
      mood = Mood.HAPPY;
    }
    return mood;
  }

  /**
   * Perform the preferred action.
   */
  @Override
  public void performPreferredAction() {
    if (preferredAction != null) {
      if (preferredAction == Action.SLEEP && isSleeping) {
        message = "Pet is already sleeping.";
        return;
      }
      switch (preferredAction) {
        case FEED -> feed();
        case PLAY -> play();
        case SLEEP -> sleep();
        case CLEAN -> clean();
        default -> checkBounds();
      }
      applyBonus();
    }
  }

  private void applyBonus() {
    int bonus = 5;
    if (preferredAction != null) {
      switch (preferredAction) {
        case PLAY:
          social += bonus;
          health += bonus / 3; // Adjust health based on the bonus
          checkBounds();
          message = "You played with your pet!\nYou received a bonus for playing!\n";
          break;
        // Add similar cases for other actions if needed
        case SLEEP:
          sleep -= bonus;
          health += bonus / 3; // Adjust health based on the bonus
          checkBounds();
          message = "Your put your pet to sleep! You received a bonus for sleeping!\n";
          break;
        case CLEAN:
          hygiene += bonus;
          health += bonus / 3; // Adjust health based on the bonus
          checkBounds();
          message = "You cleaned your pet! You received a bonus for cleaning!\n";
          break;
        case FEED:
          hunger -= bonus;
          health += bonus / 3; // Adjust health based on the bonus
          checkBounds();
          message = "You fed your pet! You received a bonus for feeding!\n";
          break;
        default:
          checkBounds();
          break;
      }
    }
  }

  /**
   * Set the preferred action.
   *
   * @param action the preferred action.
   */
  public void setPreferredAction(Action action) {
    this.preferredAction = action;
  }

  /**
   * Get the preferred action.
   */
  @Override
  public Action getPreferredAction() {
    return preferredAction;
  }

  /**
   * Check if the pet is dead.
   */
  @Override
  public boolean checkDeath() {
    isDead = (health <= 0);
    return isDead;
  }

  /**
   * Check the health status of the pet.
   */
  @Override
  public String checkPetHealthStatus() {
    if (health > 70) {
      return "Your pet is healthy!";
    } else if (health > 30) {
      return "Your pet is fine!";
    } else {
      return "Your pet is sick!";
    }
  }

  /**
   * Adjust the behavior of the pet.
   *
   * @param pet the pet.
   */
  public void adjustBehavior(Pet pet) {
    personality.adjustBehavior(pet);
  }

  /**
   * Set the personality of the pet.
   *
   * @param personality the personality of the pet.
   */
  public void setPersonality(PersonalityStrategy personality) {
    if (!personalitySet) {
      this.personality = personality;
      personalitySet = true;
    } else {
      System.out.println("Personality has already been set and cannot be changed.");
      // You can throw an exception or handle it as per your application's flow
    }
  }

  /**
   * Update the pet's attributes.
   */
  private void update() {
    long currentTime = System.currentTimeMillis();
    long timeSinceLastInteraction = (currentTime - lastInteractedTime) / 1000;

    if (timeSinceLastInteraction > 0.00001) {
      if (hunger > 70 || hygiene < 30 || social < 30 || sleep > 70) {
        health -= 5;
      }
    }
  }

  /**
   * Reset the pet model.
   */
  public void reset() {
    hunger = 20;
    hygiene = 80;
    social = 80;
    sleep = 20;
    health = 100;
    mood = Mood.HAPPY;

    // Reset any other attributes or flags as needed
    isDead = false;
    isSleeping = false;
    personalitySet = false;
    personality = null;
    message = null; // Reset any message

    // Reset the last interacted time to the current time
    lastInteractedTime = System.currentTimeMillis();

    // Re-create or restart any necessary services or threads
    if (executorService1 != null) {
      executorService1.shutdownNow();
      executorService1 = Executors.newScheduledThreadPool(1);
    }

    // Reset the personality to null or a default one if applicable
    personality = null; // Or set it to a default personality
  }
}