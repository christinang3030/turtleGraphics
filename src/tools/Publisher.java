package tools;

import java.util.HashSet;
import java.util.Set;

public class Publisher {
    private Set<Subscriber> s = new HashSet<>();
    public void subscribe(Subscriber sub) {
        s.add(sub);
    }
    public void unsubscribe(Subscriber sub) {
        s.remove(sub);
    }
    public void notifySubscribers() {
        for (Subscriber sub : s) sub.update();
    }
}
