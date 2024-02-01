/**
 * The Class PetGame.
 */
public class PetGame {

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    Pet model = new Pet();
    PetView view = new PetView();

    PetController controller = new PetController(model, view);
    view.setController(controller);

    view.setVisible(true);
  }
}