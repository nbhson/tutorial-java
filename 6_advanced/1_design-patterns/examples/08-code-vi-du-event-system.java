import java.util.ArrayList;
import java.util.List;

// Observer Interface
public interface EventListener {
    void onEvent(String eventType, String data);
}

// Subject (Publisher)
public class EventManager {

    private final List<EventListener> listeners = new ArrayList<>();

    public void subscribe(EventListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    public void publish(String eventType, String data) {
        for (EventListener listener : listeners) {
            listener.onEvent(eventType, data);
        }
    }
}
