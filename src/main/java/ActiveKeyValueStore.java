import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * TODO: write JavaDoc
 * @author Evgeniy Surovskiy
 */
public class ActiveKeyValueStore extends ConnectionWatcher
{
    private static final Charset CHARSET = Charset.forName("UTF-8");

    public void write(String path, String value) throws KeeperException, InterruptedException
    {
        Stat stat = zooKeeper.exists(path, true);
        if(stat == null)
        {
            zooKeeper.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }
        else
        {
            zooKeeper.setData(path, value.getBytes(CHARSET), -1);
        }
    }

    public String read(String path, Watcher watcher) throws KeeperException, InterruptedException
    {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData(path, watcher, stat);
        return new String(data, CHARSET);
    }
}
