/**
 * Strategy used to decide whether a search query is interesting.
 */
public interface QueryFilter {
    boolean isInterested(String query);
}
