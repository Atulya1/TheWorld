package targetcharcterpet;

/**
 * Pet interface implementation.
 */
public class PetClass implements Pet {
  private final String name;

  /**
   * Constructor to create Pet class.
   * @param name name.
   */
  public PetClass(String name) {
    this.name = name;
  }

  /**
   * get pet name.
   * @return name.
   */
  @Override
  public String getPetName() {
    return name;
  }

  /**
   * get pet description.
   * @return description.
   */
  @Override
  public String getPetDescription() {
    return "{Pet name is :" + name + "}";
  }

}
