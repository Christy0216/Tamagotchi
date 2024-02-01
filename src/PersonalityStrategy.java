/**
 * Interface for the personality strategy.
 */
public interface PersonalityStrategy {

  /**
   * Adjust the behavior of the pet based on its personality.
   * @param pet the pet to adjust the behavior of
   */
  void adjustBehavior(Pet pet);
}
