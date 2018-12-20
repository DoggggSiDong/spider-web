package spider.web.base;

public enum WEB_ZNODE_PATH {
    TASK("/spider-nest/task"),NODE("/spider-nest/node");
    private  String url;
    WEB_ZNODE_PATH(String url) {
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
