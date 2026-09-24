import java.util.Locale;

/**
 * Watches the search queries
 */
public class Snooper {
    private final WebSearchModel model;

    public Snooper(WebSearchModel model) {
        this.model = model;

        model.addQueryObserver(
                new WebSearchModel.QueryObserver() {
                    @Override
                    public void onQuery(String query) {
                        System.out.println("Oh Yes! " + query);
                    }
                },
                new QueryFilter() {
                    @Override
                    public boolean isInterested(String query) {
                        return query.toLowerCase(Locale.ROOT).contains("friend");
                    }
                });

        model.addQueryObserver(
                new WebSearchModel.QueryObserver() {
                    @Override
                    public void onQuery(String query) {
                        System.out.println("So long " + query);
                    }
                },
                new QueryFilter() {
                    @Override
                    public boolean isInterested(String query) {
                        return query.length() > 60;
                    }
                });
            }
}
