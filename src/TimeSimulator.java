import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for updating the pet's attributes every minute.
 */
public class TimeSimulator {

  private final ScheduledExecutorService executorService;
  private Pet pet;

  /**
   * Constructor for the TimeSimulator class.
   *
   * @param pet the pet to be updated.
   */
  public TimeSimulator(Pet pet) {
    this.pet = pet;
    executorService = Executors.newScheduledThreadPool(1);
  }

  /**
   * Start the attribute updates.
   */
  public void startAttributeUpdates() {
    executorService.scheduleAtFixedRate(this::updateAttributes, 0, 1, TimeUnit.MINUTES);
  }

  /**
   * Update the pet's attributes.
   */
  private void updateAttributes() {
    pet.increaseHunger();
    pet.increaseSleep();
    pet.decreaseHygiene();
    pet.decreaseSocial();
    pet.checkBounds();
    if (pet.getPersonality() != null) {
      pet.getPersonality().adjustBehavior(pet);
    }
  }

  /**
   * Stop the attribute updates.
   */
  public void stopAttributeUpdates() {
    executorService.shutdown();
  }
}