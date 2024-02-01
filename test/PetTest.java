import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Pet.
 */
public class PetTest {

  private Pet pet1;
  private Pet pet2;
  private Pet pet3;
  private TimeSimulator timeSimulator1;
  private TimeSimulator timeSimulator2;
  private TimeSimulator timeSimulator3;

  /**
   * Set up the first pet and time simulator for testing.
   */
  @Before
  public void setUp1() {
    System.setIn(new ByteArrayInputStream("1".getBytes()));
    pet1 = new Pet();
    timeSimulator1 = new TimeSimulator(pet1);
  }

  /**
   * Set up the second pet and time simulator for testing.
   */
  @Before
  public void setUp2() {
    System.setIn(new ByteArrayInputStream("2".getBytes()));
    pet2 = new Pet();
    timeSimulator2 = new TimeSimulator(pet2);
  }

  /**
   * Set up the third pet and time simulator for testing.
   */
  @Before
  public void setUp3() {
    System.setIn(new ByteArrayInputStream("3".getBytes()));
    pet3 = new Pet();
    timeSimulator3 = new TimeSimulator(pet3);
  }

  /**
   * Test the constructor for pet1.
   */
  @Test
  public void testPet1() {
    assertEquals(20, pet1.getHunger());
    assertEquals(80, pet1.getHygiene());
    assertEquals(80, pet1.getSocial());
    assertEquals(20, pet1.getSleep());
    assertEquals(100, pet1.getHealth());
  }

  /**
   * Test the startTimer method.
   */
  @Test
  public void testStartTimer() {
    try {
      Thread.sleep(TimeUnit.MINUTES.toMillis(5) + 100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    assertEquals(25, pet1.getHunger());
    assertEquals(78, pet1.getHygiene());
    assertEquals(78, pet1.getSocial());
    assertEquals(25, pet1.getSleep());
  }

  @Test
  public void testStartTimer1() {
    pet1.setHunger(80);
    pet1.displayStates();
    try {
      Thread.sleep(TimeUnit.MINUTES.toMillis(6) + 100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    assertEquals(95, pet1.getHealth());
  }

  /**
   * Test setHungry method.
   */
  @Test
  public void setHungry() {
    pet1.setHunger(50);
    assertEquals(50, pet1.getHunger());
  }

  /**
   * Test setHungry method when hunger is below 0.
   */
  @Test
  public void setHungry1() {
    pet1.setHunger(-1);
    assertEquals(0, pet1.getHunger());
  }

  /**
   * Test setHungry method when hunger is above 100.
   */
  @Test
  public void setHungry2() {
    pet1.setHunger(101);
    assertEquals(100, pet1.getHunger());
  }

  /**
   * Test setHygiene method.
   */
  @Test
  public void setHygiene() {
    pet1.setHygiene(50);
    assertEquals(50, pet1.getHygiene());
  }

  /**
   * Test setHygiene method when hygiene is below 0.
   */
  @Test
  public void setHygiene1() {
    pet1.setHygiene(-1);
    assertEquals(0, pet1.getHygiene());
  }

  /**
   * Test setHygiene method when hygiene is above 100.
   */
  @Test
  public void setHygiene2() {
    pet1.setHygiene(101);
    assertEquals(100, pet1.getHygiene());
  }

  /**
   * Test setSocial method.
   */
  @Test
  public void setSocial() {
    pet1.setSocial(50);
    assertEquals(50, pet1.getSocial());
  }

  /**
   * Test setSocial method when social is below 0.
   */
  @Test
  public void setSocial1() {
    pet1.setSocial(-1);
    assertEquals(0, pet1.getSocial());
  }

  /**
   * Test setSocial method when social is above 100.
   */
  @Test
  public void setSocial2() {
    pet1.setSocial(101);
    assertEquals(100, pet1.getSocial());
  }

  /**
   * Test setSleep method.
   */
  @Test
  public void setSleep() {
    pet1.setSleep(50);
    assertEquals(50, pet1.getSleep());
  }

  /**
   * Test setSleep method when sleep is below 0.
   */
  @Test
  public void setSleep1() {
    pet1.setSleep(-1);
    assertEquals(0, pet1.getSleep());
  }

  /**
   * Test setSleep method when sleep is above 100.
   */
  @Test
  public void setSleep2() {
    pet1.setSleep(101);
    assertEquals(100, pet1.getSleep());
  }

  /**
   * Test setHealth method.
   */
  @Test
  public void setHealth() {
    pet1.setHealth(50);
    assertEquals(50, pet1.getHealth());
  }

  /**
   * Test setHealth method when health is below 0.
   */
  @Test
  public void setHealth1() {
    pet1.setHealth(-1);
    assertEquals(0, pet1.getHealth());
  }

  /**
   * Test setHealth method when health is above 100.
   */
  @Test
  public void setHealth2() {
    pet1.setHealth(101);
    assertEquals(100, pet1.getHealth());
  }

  /**
   * Test setPreferredAction method.
   */
  @Test
  public void setPreferredAction() {
    pet1.setPreferredAction(Action.FEED);
    assertEquals(Action.FEED, pet1.getPreferredAction());
  }

  /**
   * Test setPersonality method.
   */
  @Test
  public void performPreferredAction() {
    pet1.setPreferredAction(Action.FEED);
    System.out.println(pet1.displayStates());
    pet1.performPreferredAction();
    assertEquals(5, pet1.getHunger());
  }

  /**
   * Test getHunger method.
   */
  @Test
  public void getHunger() {
    assertEquals(20, pet2.getHunger());
  }

  /**
   * Test getHygiene method.
   */
  @Test
  public void getHygiene() {
    assertEquals(80, pet2.getHygiene());
  }

  /**
   * Test getSocial method.
   */
  @Test
  public void getSocial() {
    assertEquals(80, pet2.getSocial());
  }

  /**
   * Test getSleep method.
   */
  @Test
  public void getSleep() {
    assertEquals(20, pet2.getSleep());
  }

  /**
   * Test getHealth method.
   */
  @Test
  public void getHealth() {
    assertEquals(100, pet2.getHealth());
  }

  /**
   * Test getMood method.
   */
  @Test
  public void getMood() {
    assertEquals(Mood.HAPPY, pet2.getMood());
  }

  /**
   * Test feed while not sleeping.
   */
  @Test
  public void testFeedWhenNotSleeping() {
    int initialHunger = pet2.getHunger();
    int initialHealth = pet2.getHealth();
    final long initialLastInteractedTime = pet2.getLastInteractedTime();

    pet2.feed();

    assertEquals(initialHunger - 10, pet2.getHunger());
    assertEquals(initialHealth, pet2.getHealth());
    assertNotEquals(initialLastInteractedTime, pet2.getLastInteractedTime());
  }

  /**
   * Test feed while sleeping.
   */
  @Test
  public void testFeedWhileSleeping() {
    pet2.sleep();
    int initialHunger = pet2.getHunger();
    pet2.feed();

    assertEquals(initialHunger, pet2.getHunger());
  }

  /**
   * Test sleep again after put the pet to sleep.
   */
  @Test(expected = IllegalStateException.class)
  public void testSleepAgainWhileSleeping() {
    pet2.sleep();
    pet2.sleep();
  }

  /**
   * Test clean while not sleeping.
   */
  @Test
  public void testCleanWhileNotSleeping() {
    int initialHygiene = pet2.getHygiene();
    int initialHealth = pet2.getHealth();
    final long initialLastInteractedTime = pet2.getLastInteractedTime();

    pet2.clean();

    assertEquals(initialHygiene + 10, pet2.getHygiene());
    assertEquals(initialHealth, pet2.getHealth());
    assertNotEquals(initialLastInteractedTime, pet2.getLastInteractedTime());
  }

  /**
   * Test clean while sleeping.
   */
  @Test
  public void testCleanWhileSleeping() {
    pet2.sleep();

    int initialHygiene = pet2.getHygiene();
    pet2.clean();

    assertEquals(initialHygiene, pet2.getHygiene());
  }

  /**
   * Test play while not sleeping.
   */
  @Test
  public void testPlayWhileNotSleeping() {
    int initialSocial = pet2.getSocial();
    int initialHealth = pet2.getHealth();
    final long initialLastInteractedTime = pet2.getLastInteractedTime();

    pet2.play();

    assertEquals(initialSocial + 10, pet2.getSocial());
    assertEquals(initialHealth, pet2.getHealth());
    assertNotEquals(initialLastInteractedTime, pet2.getLastInteractedTime());
  }

  /**
   * Test play while sleeping.
   */
  @Test
  public void testPlayWhileSleeping() {
    pet2.sleep();

    int initialSocial = pet2.getSocial();
    pet2.play();

    assertEquals(initialSocial, pet2.getSocial());
  }

  /**
   * Test displayStates method.
   */
  @Test
  public void testDisplayStates() {
    assertEquals(
        "Hunger: 20\n"
            + "Hygiene: 80\n"
            + "Social: 80\n"
            + "Sleep: 20\n"
            + "Health: 100\n"
            + "Mood: HAPPY\n"
            + "Personality: NormalPersonality",
        pet3.displayStates());
  }

  /**
   * Test last interacted time.
   */
  @Test
  public void testLastInteractedTime() {
    pet3.feed();
    assertEquals(System.currentTimeMillis(), pet3.getLastInteractedTime(), 1000);
  }

  /**
   * Test sleep method.
   */
  @Test
  public void testSleep() {
    pet3.sleep();
    try {
      Thread.sleep(TimeUnit.MINUTES.toMillis(1) + 100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    assertEquals(10, pet3.getSleep());
  }

  /**
   * Test assigned behavior should be PLAY when personality is Happy and heath is greater than 70.
   */
  @Test
  public void testAdjustBehavior_HealthGreaterThan70() {
    pet1.getPersonality().adjustBehavior(pet1);
    assertEquals(Action.PLAY, pet1.getPreferredAction());

    pet1.performPreferredAction();
    assertEquals(95, pet1.getSocial());
  }

  /**
   * test getMood method.
   */
  @Test
  public void testGetMood() {
    assertEquals(Mood.HAPPY, pet1.getMood());
  }

  /**
   * Test startAttributeUpdates method.
   */
  @Test
  public void testStartAttributeUpdates() throws InterruptedException {
    timeSimulator3.startAttributeUpdates();

    TimeUnit.MINUTES.sleep(3);

    timeSimulator3.stopAttributeUpdates();

    assertEquals(40, pet3.getHunger());
    assertEquals(72, pet3.getHygiene());
  }

  /**
   * Test increaseHunger method.
   */
  @Test
  public void testIncreaseHunger() {
    pet2.increaseHunger();
    assertEquals(25, pet2.getHunger());
  }

  /**
   * Test increaseSleep method.
   */
  @Test
  public void testIncreaseSleep() {
    pet2.increaseSleep();
    assertEquals(25, pet2.getSleep());
  }

  /**
   * Test decreaseHygiene method.
   */
  @Test
  public void testDecreaseHygiene() {
    pet2.decreaseHygiene();
    assertEquals(78, pet2.getHygiene());
  }

  /**
   * Test decreaseSocial method.
   */
  @Test
  public void testDecreaseSocial() {
    pet2.decreaseSocial();
    assertEquals(78, pet2.getSocial());
  }

  /**
   * Test checkDeath method.
   */
  @Test
  public void testCheckDeath() {
    assertEquals("Your pet is alive!", pet1.checkDeath());
    pet1.setHealth(0);
    assertEquals("Your pet has died!", pet1.checkDeath());
  }

  /**
   * Test checkPetHealthStatus method.
   */
  @Test
  public void testCheckPetHealthStatus() {

    // Test for health > 70
    pet1.setHealth(80);
    assertEquals("Your pet is healthy!", pet1.checkPetHealthStatus());

    // Test for health > 30 and < 70
    pet1.setHealth(50);
    assertEquals("Your pet is fine!", pet1.checkPetHealthStatus());

    // Test for health < 30
    pet1.setHealth(20);
    assertEquals("Your pet is sick!", pet1.checkPetHealthStatus());
  }
}