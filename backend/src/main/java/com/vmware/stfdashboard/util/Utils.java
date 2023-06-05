package com.vmware.stfdashboard.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;

public class Utils {

    private final static Logger logger = LoggerFactory.getLogger(Utils.class);

    private final static Pattern sddcPattern = Pattern.compile("CToT-CDS-(\\w+)-.*");
    private final static Pattern suitePattern = Pattern.compile("CToT-CDS-\\w+-([^']*) #.*");
    private final static Pattern suiteBackup = Pattern.compile("CToT-CDS-\\w+-([^']*)");
    private final static Pattern buildNumberPattern = Pattern.compile("#.* ob-([^']*)");

    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> out = new ArrayList<>();
        iterable.forEach(out::add);
        return out;
    }

    public static SddcType extractSddcType(String name) {
        Matcher matcher = sddcPattern.matcher(name);
        if (!matcher.find()) throw new IllegalArgumentException("Failed to find SDDC Type in name " + name);

        return SddcType.findByValue(matcher.group(1));
    }

    public static SuiteType extractSuiteType(String name) {
        Matcher matcher = suitePattern.matcher(name);
        if (!matcher.find()) {
            matcher = suiteBackup.matcher(name);

            if (!matcher.find()) {
                throw new IllegalArgumentException("Failed to find Suite Type in name " + name);
            }
        }

        return SuiteType.findByValue(matcher.group(1).trim());
    }

    public static long extractBuildNumber(String name) {
        Matcher matcher = buildNumberPattern.matcher(name);
        if (!matcher.find()) throw new IllegalArgumentException("Failed to find Build Number in name " + name);

        return Long.parseLong(matcher.group(1));
    }

    /**
     * Applies a mapping function to a list of items, filtering any that will have an exception
     * thrown on them for one reason or another. Filters out any items that throw a
     * {@link IllegalArgumentException}
     * @param items the list of items to map.
     * @param map the mapping function to apply to the list.
     * @return the list of mapped (and filtered) items.
     * @param <T> The class type of the given list.
     * @param <E> The class type of the list returned.
     */
    public static <T, E> Pair<List<E>, List<T>> filterMap(List<T> items, Function<T, E> map) {
        List<E> out = new ArrayList<>();
        List<T> skippedItems = new ArrayList<>();

        for (T t : items) {
            try {
                out.add(map.apply(t));
            } catch (IllegalArgumentException e) {
                logger.info(e.getMessage() + " for item {}", t);
                skippedItems.add(t);
            }
        }

        if (!skippedItems.isEmpty()) logger.info("Skipped {} items.", skippedItems.size());

        return Pair.of(out, skippedItems);
    }

    public static <T, E> List<E> filterMapSuppressed(List<T> items, Function<T, E> map) {
        List<E> out = new ArrayList<>();

        for (T t : items) {
            try {
                out.add(map.apply(t));
            } catch (IllegalArgumentException e) {
                logger.info(e.getMessage() + " for item {}", t);
            }
        }

        return out;
    }

    public static String extractFilter(String filter, String column, String defaultValue) {
        Pattern filterExtractor = Pattern.compile(column + "==(.*?)\\*");

        Matcher m = filterExtractor.matcher(filter);

        return m.find() ? m.group(1) : defaultValue;
    }

}
