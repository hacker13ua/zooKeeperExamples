import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * TODO: write JavaDoc
 * @author Evgeniy Surovskiy
 */
public class ConnectionWatcher implements Watcher
{
    private static final int SESSION_TIMEOUT = 5000;

    protected ZooKeeper zooKeeper;
    private CountDownLatch connectedSignal = new CountDownLatch(1);

    public void connect(final String hosts) throws IOException, InterruptedException
    {
        zooKeeper = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        connectedSignal.await();
    }

    @Override
    public void process(final WatchedEvent event)
    {
        if(event.getState().equals(Event.KeeperState.SyncConnected))
        {
            connectedSignal.countDown();
        }
        else
        {
            System.out.println("Event = " + event);
        }
    }

    public void close() throws InterruptedException
    {
        zooKeeper.close();
    }
}
