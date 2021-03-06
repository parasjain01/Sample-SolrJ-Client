package net.parasjain.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * User: Paras Jain
 * Date: 12/9/14
 * Time: 5:49 PM
 */
public class SampleSolrClient {

    public static void main(String[] args) throws IOException, SolrServerException {
        String url = "http://localhost:9080/worklifecore1" ;
        HttpSolrServer server = new HttpSolrServer( url );
        server.setMaxRetries(1); // defaults to 0.  > 1 not recommended.
        server.setConnectionTimeout(5000); // 5 seconds to establish TCP
        // Setting the XML response parser is only required for cross
        // version compatibility and only when one side is 1.4.1 or
        // earlier and the other side is 3.1 or later.
        server.setParser(new XMLResponseParser()); // binary parser is used by default
        // The following settings are provided here for completeness.
        // They will not normally be required, and should only be used
        // after consulting javadocs to know whether they are truly required.
        server.setSoTimeout(1000);  // socket read timeout
        server.setDefaultMaxConnectionsPerHost(100);
        server.setMaxTotalConnections(100);
        server.setFollowRedirects(false);  // defaults to false
        // allowCompression defaults to false.
        // Server side must support gzip or deflate for this to have any effect.
        server.setAllowCompression(true);

        SolrQuery query = new SolrQuery();
        query.setQuery("title:" + "Eat Healthy");
        query.set("fl", "id,score");
        int maxResults = 20;
        query.setRows(maxResults);

        QueryResponse response = server.query(query);
        System.out.println(response.getResults().size());

    }
}
