package FileSystem.FileNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectoryNode extends SystemNode {
    public HashMap<String,SystemNode> childrenNodes;

    public DirectoryNode(String name,SystemNode parentSystemNode) {
        super(name, true,parentSystemNode);
        childrenNodes = new HashMap<>();
    }

    public int getNumberOfChildren(){
        return childrenNodes.size();
    }

}
