import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * PetSimulation class that simulates a pet's life.
 * It creates an instance of the Pet class and interacts with it.
 * It also displays the pet's current state, performs the pet's preferred action,
 * and checks the pet's health status and mood.
 * It also writes the simulation output to a file.
 */
public class PetSimulation {

  /**
   * Main method for the PetSimulation class.
   *
   * @param args command line arguments.
   */
  public static void main(String[] args) {
    String filePath = "res/output.txt";

    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
      // Set the input stream to 1
      System.setIn(new ByteArrayInputStream("1".getBytes()));

      // Create an instance of your Pet class
      Pet myPet = new Pet();

      // Interact with the pet
      myPet.feed();
      myPet.play();
      myPet.clean();
      myPet.sleep();
      myPet.feed();

      // Display the pet's current state
      String petState = myPet.displayStates();
      writer.println("Current Pet State:\n" + petState);

      // Perform preferred action
      myPet.setPreferredAction(Action.SLEEP);
      myPet.performPreferredAction();

      // Check pet's health status and mood
      String healthStatus = myPet.checkPetHealthStatus();
      writer.println("Pet Health Status: " + healthStatus);

      Mood petMood = myPet.getMood();
      writer.println("Pet Mood: " + petMood);

      System.out.println("Simulation output written to: " + filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}