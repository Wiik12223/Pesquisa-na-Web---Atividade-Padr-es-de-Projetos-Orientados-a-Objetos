import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Perform "web search" (from a  file), notify the interested observers of each query.
 */
public class WebSearchModel {
    private final File sourceFile;
    private final List<ObserverRegistration> observers = new ArrayList<>();

    public interface QueryObserver {
        void onQuery(String query);
    }

    private static class ObserverRegistration {
        private final QueryObserver observer;
        private final QueryFilter filter;

        private ObserverRegistration(QueryObserver observer, QueryFilter filter) {
            this.observer = observer;
            this.filter = filter;
        }
    }

    public WebSearchModel(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void pretendToSearch() {
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            while ( true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                notifyInterestedObservers(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQueryObserver(QueryObserver queryObserver, QueryFilter filter) {
        observers.add(new ObserverRegistration(queryObserver, filter));
    }

    private void notifyInterestedObservers(String query) {
        for (ObserverRegistration registration : observers) {
            if (registration.filter.isInterested(query)) {
                registration.observer.onQuery(query);
            }
        }
    }
}
