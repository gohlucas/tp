package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a Sport associated with a Person in the address book.
 * Guarantees: sport name is present, not null, and is valid.
 */
public class Sport {
    private static Set<String> validSports = new HashSet<>();

    // Static initializer to load default sports when the class is first loaded
    static {
        loadDefaultSports();
    }

    public final String sportName;

    /**
     * Constructs a {@code Sport}.
     *
     * @param sportName A valid sport name.
     */
    public Sport(String sportName) {
        requireNonNull(sportName);
        this.sportName = sportName;
    }

    public static String getMessageConstraints() {
        return "Sport must be one of the following: " + String.join(", ", getSortedValidSports());
    }

    /**
     * Returns an unsorted set of valid sports. For most display purposes, use getSortedValidSports().
     */
    public static Set<String> getValidSports() {
        return validSports;
    }

    /**
     * Returns a sorted list of valid sports.
     * This is useful for displaying sports in a consistent order.
     */
    public static List<String> getSortedValidSports() {
        return new ArrayList<>(new TreeSet<>(validSports));
    }

    /**
     * Deletes a valid sport by its index in the sorted list.
     *
     * @param index The index of the sport to delete (0-based).
     * @return The deleted sport name, or null if the index is invalid.
     */
    public static String deleteValidSport(int index) {
        List<String> sortedSports = getSortedValidSports();
        if (index < 0 || index >= sortedSports.size()) {
            return null;
        }

        String sportToRemove = sortedSports.get(index);
        validSports.remove(sportToRemove);
        return sportToRemove;
    }

    /**
     * Loads the valid sports from a JSON file.
     */
    public static void loadValidSports(Path filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        if (!Files.exists(filePath)) {
            Set<String> defaultSports = new HashSet<>(Arrays.asList("soccer", "basketball", "tennis", "badminton",
                    "cricket", "baseball", "volleyball", "hockey", "rugby", "golf"));
            Files.write(filePath, mapper.writeValueAsBytes(defaultSports), StandardOpenOption.CREATE);
            validSports = defaultSports;
        }
        try {
            validSports = mapper.readValue(Files.readAllBytes(filePath), new TypeReference<Set<String>>() {});
        } catch (IOException e) {
            System.err.println("Failed to load sports from file: " + e.getMessage());
        }
    }

    /**
     * Loads the default sports.
     */
    public static void loadDefaultSports() {
        validSports = new HashSet<>(Arrays.asList("soccer", "basketball", "tennis", "badminton",
                "cricket", "baseball", "volleyball", "hockey", "rugby", "golf"));
    }

    /**
     * Saves the valid sports to a JSON file.
     */
    public static void saveValidSports(Path filePath) throws IOException {
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(filePath.toFile(), validSports);
    }

    /**
     * Returns true if the given sport name is valid.
     */
    public static boolean isValidSport(String sportName) {
        requireNonNull(sportName);
        return validSports.contains(sportName.toLowerCase().trim());
    }

    /**
     * Adds a new sport to the list of valid sports.
     * The sport must be unique and not already exist.
     *
     * @param sportName The sport to add.
     * @return true if the sport was added, false if the sport already exists.
     */
    public static boolean createValidSport(String sportName) {
        requireNonNull(sportName);
        if (isValidSport(sportName)) { //sport already exists
            return false;
        }
        return validSports.add(sportName.toLowerCase().trim());
    }

    /**
     * Returns the normalized form of the sport name (lowercase).
     */
    public String getNormalizedName() {
        return sportName.toLowerCase();
    }

    @Override
    public String toString() {
        return sportName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Sport otherSport)) {
            return false;
        }
        return sportName.equalsIgnoreCase(otherSport.sportName);
    }

    @Override
    public int hashCode() {
        return sportName.toLowerCase().hashCode();
    }
}
