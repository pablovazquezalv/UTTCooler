package app.Clases_adafruit;

import java.util.List;

public class Feed {

    public  int id;
    public String label;

    public List<BlockFeed> block_feeds;

    public Feed(int id, String label, List<BlockFeed> block_feeds) {
        this.id = id;
        this.label = label;
        this.block_feeds = block_feeds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<BlockFeed> getBlock_feeds() {
        return block_feeds;
    }

    public void setBlock_feeds(List<BlockFeed> block_feeds) {
        this.block_feeds = block_feeds;
    }
}
