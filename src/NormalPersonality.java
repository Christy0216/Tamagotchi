/**
 * This class represents a normal personality for a pet.
 */
public class NormalPersonality implements PersonalityStrategy {
  /**
   * This method adjusts the behavior of the pet based on its personality.
   *
   * @param pet the pet whose behavior is being adjusted
   */
  @Override
  public void adjustBehavior(Pet pet) {
    if (pet.getSocial() < 40) {
      pet.setPreferredAction(Action.PLAY);
    } else {
      pet.setPreferredAction(Action.SLEEP);
    }
  }
}
