package at.kurumi.graphics;

/**
 * Interface for a UI Element providing a health bar.
 */
public interface IHealthbar extends UIElement {

    /**
     * Set the health bar fill level as percentage.
     *
     * @param value value between {@code 0.0} and {@code 1.0}
     */
    void setHealth(float value);

}
