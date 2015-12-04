import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

/**
 * TODO: write JavaDoc
 * @author Evgeniy Surovskiy
 */
public class CreateGroup extends ConnectionWatcher
{
    public void create(final String groupName) throws KeeperException, InterruptedException
    {
        String path = "/" + groupName;
        String createdPath = zooKeeper.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Created " + createdPath);
    }

    public static void main(final String[] args) throws IOException, InterruptedException, KeeperException
    {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect(args[0]);
        createGroup.create(args[1]);
        Thread.sleep(Long.MAX_VALUE);
        createGroup.close();
        System.out.println("Closed");
        Thread.sleep(5000l);
    }
}
