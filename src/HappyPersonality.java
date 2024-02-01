/**
 * The class that represents a happy personality.
 */
public class HappyPersonality implements PersonalityStrategy {
  /**
   * The method that adjusts the behavior of the pet.
   *
   * @param pet the pet whose behavior is to be adjusted
   */
  @Override
  public void adjustBehavior(Pet pet) {
    if (pet.getHealth() < 70) {
      pet.setPreferredAction(Action.FEED);
    } else {
      pet.setPreferredAction(Action.PLAY);
    }
  }
}