package FileSystem.FileNode;

public class FileNode extends SystemNode {
    public String content;
    public FileNode(String name,SystemNode parentSystemNode) {
        super(name, false,parentSystemNode);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSize(){
        return content.length();
    }

    @Override
    public int getNumberOfChildren() {
        return 0;
    }
}
