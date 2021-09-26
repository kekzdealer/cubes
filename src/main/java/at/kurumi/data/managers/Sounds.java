package at.kurumi.data.managers;

import at.kurumi.data.resources.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for {@link at.kurumi.data.resources.Sound} objects.
 *
 * @see at.kurumi.data.resources.Sound
 */
public class Sounds {

    public static final String DEFAULT_SOUND_NAME = "default";

    private final Map<String, Sound> sounds = new HashMap<>();
}
