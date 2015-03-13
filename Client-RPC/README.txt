THIS IS THE JAVA VERSION FILE FOR getLising unit test :

/**
 *
 */
package org.apache.hadoop.ipc;

import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.DirectoryListing;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.net.NetUtils;
import org.junit.Test;

/**
 * @author gabriel
 *
 */
public class MyTest {

        private static final String ADDRESS = "10.105.2.143";
        private static final int PORT = 9000;

        public static final Log LOG = LogFactory.getLog(TestRPC.class);

        private static Configuration conf = new Configuration();

        int datasize = 1024 * 100;
        int numThreads = 50;

        @Test
        public void testList() throws Exception {
                System.out.println("Testing Slow RPC");
                ClientProtocol proxy = null;
                try {
                        InetSocketAddress addr = NetUtils.createSocketAddr(ADDRESS, PORT);
                        proxy = (ClientProtocol) RPC.getProxy(ClientProtocol.class,
                                        ClientProtocol.versionID, addr, conf);
                        DirectoryListing directoryListing = proxy
                                        .getListing(
                                                        "/anritsu/mclaw/eoDR/buffer/eoAINT/2013/05/27/16/QXDRS_CAME-AINT1",
                                                        HdfsFileStatus.EMPTY_NAME);
                        for (HdfsFileStatus entry : directoryListing.getPartialListing()) {
                                System.err.println(entry.getOwner());
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(e.toString());
                }

        }
}
