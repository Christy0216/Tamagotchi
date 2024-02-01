/**
 * Enumerated type for the actions that can be performed on a pet.
 */
public interface PetModel {

  /**
   * Feed th pet, the hunger level will decrease by 10 and the health level will increase by 5.
   */
  public void feed();

  /**
   * Clean the pet, the hygiene level increase by 10 and the health level will increase by 5.
   */
  public void clean();

  /**
   * Play with the pet, the social level will increase by 10 and the health level will increase by
   * 5.
   */
  public void play();

  /**
   * Put the pet to sleep, the sleep level will decrease by 10 and the health level will increase by
   * 5.
   */
  public void sleep();

  /**
   * Get the current hunger level of the pet.
   * @return the current hunger level of the pet.
   */
  public int getHunger();

  /**
   * Get the current hygiene level of the pet.
   * @return the current hygiene level of the pet.
   */
  public int getHygiene();

  /**
   * Get the current social level of the pet.
   * @return the current social level of the pet.
   */
  public int getSocial();

  /**
   * Get the current sleep level of the pet.
   * @return the current sleep level of the pet.
   */
  public int getSleep();

  /**
   * Get the current health level of the pet.
   * @return the current health level of the pet.
   */
  public int getHealth();

  /**
   * Get the personality of the pet.
   * @return the personality of the pet.
   */
  public PersonalityStrategy getPersonality();

  /**
   * Display the attributes: hunger, clean, sleep, social, of the pet.
   * @return the attributes of the pet.
   */
  public String displayStates();

  /**
   * Get the mood of the pet.
   * @return the mood of the pet.
   */
  public Mood getMood();

  /**
   * Get the preferred action of the pet.
   * @return the preferred action of the pet.
   */
  public Action getPreferredAction();

  /**
   * Perform the preferred action of the pet.
   */
  public void performPreferredAction();

  /**
   * Check if the pet is dead.
   * @return the death status of the pet.
   */
  public boolean checkDeath();

  /**
   * Check if the pet's health status.
   * @return the health status of the pet.
   */
  public String checkPetHealthStatus();

  /**
   * Get the last interacted time of the pet.
   * @return the last interacted time of the pet.
   */
  long getLastInteractedTime();
}